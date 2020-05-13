package po;

import io.restassured.specification.RequestSpecification;
import util.LoadDefaultConfig;
import util.ReadYAML;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static io.restassured.RestAssured.given;

/**
 * tmoney
 * 2020/5/7 20:41
 * 解析yaml用例
 *
 * @author zhzh.yin
 **/
public class PageObjectModel {
    private static final Logger log = LoggerFactory.getLogger(PageObjectModel.class);
    public HashMap<String, PageObjectAPI> apilist;
    public HashMap<String, PageObjectParam> paramlist;
    private static String host = LoadDefaultConfig.getHost();

    public static Response parseAPI(Class frontAPIClazz, Map<String, String> map) {
        String path = ReadYAML.transClasspathToYamlpath(frontAPIClazz);
        log.info(" parse the path : " + path);
        PageObjectModel model = ReadYAML.getYamlConfig(path, PageObjectModel.class);
        log.info("载入yaml中写的apilist");
        String methodname = Thread.currentThread().getStackTrace()[2].getMethodName();
        log.info("载入method :" + methodname);
        log.info(model.apilist.get(methodname).getApi().size()+"");
        return parseApiFromYaml(model.apilist.get(methodname), map);
    }

    public static HashMap<String, String> parseParam(Class frontAPIClazz) {
        String path = ReadYAML.transClasspathToYamlpath(frontAPIClazz);
        log.info(" parse the path : " + path);
        PageObjectModel model = ReadYAML.getYamlConfig(path, PageObjectModel.class);
        log.info("载入yaml中写的apilist");
        String methodname = Thread.currentThread().getStackTrace()[2].getMethodName();
        log.info("载入method :" + methodname);
        return model.paramlist.get(methodname).getParam();
    }

    public static HashMap<String, String> getParam(Class frontTestClazz) {
        String path = ReadYAML.transClasspathToYamlpath(frontTestClazz);
        log.info(" parse the path : " + path);
        PageObjectModel model = ReadYAML.getYamlConfig(path, PageObjectModel.class);
        log.info("载入yaml中写的paramlist");
        String methodname = Thread.currentThread().getStackTrace()[2].getMethodName();
        log.info("载入method :" + methodname);
        return model.paramlist.get(methodname).getParam();
    }


    private static HashMap<String, String> transParams(String str, Map<String, String> map) {
        if (map.size() <= 0) {
            log.info("没有需要进行字符串转换的参数");
            return null;
        }
        log.info("before replace ,the str is :" + str);
        List<String> paramlist = Arrays.asList(str.split(","));
        log.info("用来转换的参数列表："+paramlist.toString());
        HashMap<String, String> resultMap = new HashMap<>();
        for (String param : paramlist) {
            if (map.containsKey(param)) {
                resultMap.put(param, map.get(param));
                log.info("put " + param + "into paramlist,value is   :" + map.get(param));
            }
        }
        return resultMap;
    }

    private static Response parseApiFromYaml(PageObjectAPI apilist, Map<String, String> map) {
        HashMap<String, String> apiItems = apilist.getApi();
        if (apiItems.size() <= 0) {
            log.info("没找到配置的api信息");
//            throw new APINotFoundException("yaml 配置错误，未找到配置的api");
            return null;
        }
        //todo 参数枚举化
        RequestSpecification request = given();
        if (map.size() > 0) {
            if (apiItems.containsKey("params")) {
                request = request.params(transParams(apiItems.get("params"), map));
                apiItems.remove("params");
            }
            if (apiItems.containsKey("cookie")) {
                request = request.cookies(transParams(apiItems.get("cookie"), map));
                apiItems.remove("cookie");
            }
            if (apiItems.containsKey("header")) {
                request = request.headers(transParams(apiItems.get("header"), map));
                apiItems.remove("header");
            }
            if (apiItems.containsKey("contentType")) {
                request = request.contentType(apiItems.get("contentType"));
                apiItems.remove("contentType");
            }
            if (apiItems.containsKey("json")) {
                String jsonName = apiItems.get("json");
                HashMap<String, String> apiParam = transParams(apiItems.get("json"), map);
                String jsonPath = apiParam.get(jsonName);
                if (!jsonPath.contains("/")) {
                    request = request.body(apiParam);
//request = request .body("{\"id\": \"f09a04b775974f98bee9aaed8c492d24\"}");
                } else {
                    //todo jsonPath转成json
                }

                apiItems.remove("json");
            }
        }
        if (apiItems.containsKey("get")) {
            Response response = (Response) request.when()
                    .log().all()
                    .get(host + apiItems.get("get"))
                    .then()
                    .log().all()
                    .extract();
            apiItems.remove("get");
            return response;
        } else if (apiItems.containsKey("post")) {
            Response response = (Response) request.when()
                    .log().all()
                    .post(host + apiItems.get("post"))
                    .then()
                    .log().all()
                    .extract();
            apiItems.remove("post");
            return response;
        } else {
            log.info("还没写" + apiItems.keySet());
        }
//     throw new APINotFoundException("解析失败");
        return null;
    }

}
