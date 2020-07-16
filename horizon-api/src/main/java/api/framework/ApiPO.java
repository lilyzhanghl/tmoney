package api.framework;

import auto.framework.BasePO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import poexception.APINotFoundException;
import util.JSONTemplate;
import util.LoadDefaultConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ApiPO extends BasePO {

    private static String host = LoadDefaultConfig.getHost();
    private static RequestSpecBuilder builder;

    /**
     * json 文件名称需要和apiContent的name一致
     * json文件的存储位置要和yaml一致
     * @param path yaml存储路径
     * @return json存储路径
     */
    public static String getJsonPath(String path) {
        return path.replace(".yaml",".json");

    }

    /**
     * 读取apiContent中的param参数
     */
    public static HashMap<String, String> getParam(ApiContent apiContent) {
        return apiContent.getParam();
    }

    /**
     * 将List中的参数，转换为Map
     * @param list
     * @param map
     * @return
     * @throws APINotFoundException
     */
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

    /**
     * 解析单个api
     * @param api
     * @param map
     * @param frontAPIClazz
     * @return
     * @throws APINotFoundException
     */


    private static Response parseApiFromYaml(ApiContent api, Map<String, String> map,String path) throws APINotFoundException {
        String url = api.getUrl();
        String method = api.getMethod();
        HashMap<String, Object> headers = api.getHeaders();
        String connection = api.getConnection();
        String jsonFile = api.getJsonFile();
        String params = api.getParams();
        String json = api.getJson();
        String localHost = "";
        if (api.getHost() != null) {
            localHost = api.getHost();
        } else {
            localHost = host;
        }


        if (method == "" || method.equals(null)) {
            throw new APINotFoundException("没有写入method");
        }

        //todo 参数枚举化
        RequestSpecification request = given();
        log.info("查看builder有没有值");
        if (builder != null) {
            log.info("the builder is " + builder.toString());
            RequestSpecification requestSpec = builder.build();
            request = request.spec(requestSpec);
        }
        if (params != null) {
            List<String> params1 = Arrays.asList(
                    api.getParams().split(","));
            request = request.params(transParams(params1, map));
            log.info("配置params:" + api.getParams());

        }
        if (headers != null) {
            request = request.headers(api.getHeaders());
            log.info("配置header:" + headers);
        }
        if (json != null){
            List<String >json1 = Arrays.asList(json.split(","));
            if(jsonFile!=null){
                String jsonPath = getJsonPath(path);
                HashMap<String, String> jsonMap = transParams(json1, map);
                String jsonValue = JSONTemplate.template(jsonPath, jsonMap);
                request = request
                        .contentType(ContentType.JSON)
                        .body(jsonValue);
            }else{
                request = request
                        .contentType(ContentType.JSON)
                        .body(transParams(json1, map));
            }
        }
        if (jsonFile != null) {
            jsonFile = getJsonPath(path);
            log.info("jsonFile is " + jsonFile);
            request = request
                    .contentType(ContentType.JSON)
                    .body(JSONTemplate.template(jsonFile));
        }

        if (method.equals("get")) {
            Response response = request.when()
                    .log().all()
                    .get(localHost + url)
                    .then()
                    .log().all()
                    .extract()
                    .response();
            if (null==builder) {
                builder = new RequestSpecBuilder();
                log.info(response.getCookies().keySet().toString());
                builder.addCookies(response.getCookies());
            }
            return response;
        } else if (method.equals("post")) {
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
