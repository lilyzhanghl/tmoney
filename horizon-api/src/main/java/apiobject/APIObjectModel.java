package apiobject;

import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import poexception.APINotFoundException;
import util.JSONTemplate;
import util.LoadDefaultConfig;
import util.HandelYaml;
import io.restassured.response.Response;
import java.util.*;

import static io.restassured.RestAssured.given;

/**
 * tmoney
 * 2020/5/7 20:41
 * 解析yaml用例
 *
 * @author zhzh.yin
 **/
@Slf4j
@Data
public class APIObjectModel {
    public HashMap<String, APIObject> apilist;
    public HashMap<String, APIObjectParam> paramlist;
    private static String host = LoadDefaultConfig.getHost();


    public static String transClasspathToYamlpath(Class clazz) {
        return "src/main/java/" + clazz.getCanonicalName()
                .replace(".", "/")
                .toLowerCase()
                + ".yaml";
    }

    public static String transClasspathToJsonpath(Class clazz, String jsonFileName) {
        return "src/main/java/" + clazz.getPackage().getName().replace(".", "/") + "/" + jsonFileName
                .toLowerCase()
                + ".json";

    }

    public static Response parseAPI(Class frontAPIClazz, Map<String, String> map) {
        String path = transClasspathToYamlpath(frontAPIClazz);
        log.info(" parse the path : " + path);

        APIObjectModel model = HandelYaml.getYamlConfig(path, APIObjectModel.class);
        log.info("载入yaml中写的apilist");
        String methodname = Thread.currentThread().getStackTrace()[2].getMethodName();
        log.info("载入method :" + methodname);
        log.info(model.apilist.get(methodname).toString() + "");
        try {
            return parseApiFromYaml(model.apilist.get(methodname), map, frontAPIClazz);
        } catch (APINotFoundException e) {
            log.info(e.getMessage());
            return null;
        }

    }

    public static HashMap<String, String> parseParam(Class frontAPIClazz) {
        String path = transClasspathToYamlpath(frontAPIClazz);
        log.info(" parse the path : " + path);
        log.info(" ====================== " );

        APIObjectModel model = HandelYaml.getYamlConfig(path, APIObjectModel.class);
        log.info("载入yaml中写的paramlist");
        String methodname = Thread.currentThread().getStackTrace()[2].getMethodName();
        log.info("载入method :" + methodname);
        if(null!=model.paramlist.get(methodname)){
            return model.paramlist.get(methodname).getParam();
        }
        return null;
    }

    public static HashMap<String, String> getParam(Class frontTestClazz) {
        String path = transClasspathToYamlpath(frontTestClazz);
        log.info(" parse the path : " + path);
        APIObjectModel model = HandelYaml.getYamlConfig(path, APIObjectModel.class);
        log.info("载入yaml中写的paramlist");
        String methodname = Thread.currentThread().getStackTrace()[2].getMethodName();
        log.info("载入method :" + methodname);
        return model.paramlist.get(methodname).getParam();
    }


    private static HashMap<String, String> transParams(List<String> list, Map<String, String> map) throws APINotFoundException {
        if (map.size() <= 0) {
            log.info("没有需要进行字符串转换的参数");
            throw new APINotFoundException("api ");
        }
        log.info("before replace ,the str is :" + list);
        HashMap<String, String> resultMap = new HashMap<>();
        for (String param : list) {
            if (map.containsKey(param)) {
                resultMap.put(param, map.get(param));
                log.info("put " + param + " into paramlist,value is   :" + map.get(param));
            }
        }
        return resultMap;
    }

    private static Response parseApiFromYaml(APIObject api, Map<String, String> map, Class frontAPIClazz) throws APINotFoundException {
        String url = api.getUrl();
        String method = api.getMethod();
        HashMap<String,Object> headers  = api.getHeaders();
        String connection = api.getConnection();
        String jsonFile = api.getJsonFile();
        String params = api.getParams();
        String json = api.getJson();
        String cookies = api.getCookies();
        String localHost = "";
        if (api.getHost() != null) {
            localHost = api.getHost();
        }else {
            localHost = host;
        }



        if(method==""||method.equals(null)){
            throw  new APINotFoundException("没有写入method");
        }

        //todo 参数枚举化
        RequestSpecification request = given();
            if(params!=null){
                List<String > params1 = Arrays.asList(
                        api.getParams().split(","));
                request = request.params(transParams(params1, map));
                log.info("配置params:" + api.getParams());

            }

            if (cookies!=null) {
                List<String > cookies1 = Arrays.asList(
                        api.getCookies().split(","));
                request = request.cookies(transParams(cookies1, map));
                log.info("配置cookies:" + cookies1);

            }
            if (headers!=null) {
                request = request.headers(api.getHeaders());
                log.info("配置header:" + headers);
            }
            if(json!=null){
                List<String >json1 = Arrays.asList(json.split(","));
                if(jsonFile!=null){
                    String jsonPath = transClasspathToJsonpath(frontAPIClazz, jsonFile);
                    HashMap<String, String> jsonMap = transParams(json1, map);
                    String jsonValue = JSONTemplate.template(jsonPath, jsonMap);
                    request = request.body(jsonValue);
                }else{
                    request = request.body(transParams(json1, map));
                }

            }
        if (jsonFile!=null) {
            jsonFile = transClasspathToJsonpath(frontAPIClazz, jsonFile);
            log.info("jsonFile is " + jsonFile);
            request = request.body(JSONTemplate.template(jsonFile));
        }
        if (method .equals("get")) {
            Response response = request.when()
                    .log().all()
                    .get(localHost + url)
                    .then()
                    .log().all()
                    .extract()
                    .response();
            return response;
        } else if (method .equals("post")) {
            Response response = request.when()
                    .log().all()
                    .post(localHost + url)
                    .then()
                    .log().all()
                    .extract()
                    .response();
            return response;
        } else {
            throw new APINotFoundException("解析失败");
        }
    }

}
