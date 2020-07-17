package api.framework;

import auto.framework.BasePO;
import auto.framework.Model;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import poexception.APINotFoundException;
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

    private static RequestSpecBuilder builder;
    /**
     * 处理host
     */
    private static ApiContent handleHost(ApiContent apiContent){
        String host = LoadDefaultConfig.getHost();
        String apiHost = apiContent.getHost();
        if(apiHost!=null){
            apiContent.setHost(host+apiHost);
        }
        return apiContent;
    }
    /**
     * 读取apiContent中的param参数
     */
    public static HashMap<String, String> getParam(ApiContent apiContent) {
        return apiContent.getParam();
    }

    /**
     * 处理jsonParams
     */
    private static HashMap<String,String> handleJsonMap(String jsonParams,HashMap<String,String>map){
        return null;
    }
    /**
     * 处理jsonPath
     */
    private static ApiContent handleJsonPath(ApiContent api,String yamlpath){
        String jsonPath = api.getJsonPath();
        jsonPath = yamlpath.replace("///.*?/.yaml",jsonPath)
        .replace(".yaml",".json");
        api.setJsonPath(jsonPath);
        return api;
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
     * 对ApiContent中的param，综合外部传参，进行数据加工
     */
    private static ApiContent handleParam(ApiContent api) {
        List<String> params1 = Arrays.asList(
                api.getRequestParams().split(","));
        HashMap map = api.getParam();
        try {
            map = transParams(params1, map);
        } catch (APINotFoundException e) {
            log.info("API NOT FOUND");
        }
        api.setParam(map);
        return api;
    }
    /**
     * 加工api参数
     */
    public static ApiContent handleApi(ApiContent api,String path){
        api= handleHost(api);
        api= handleJsonPath(api,path);
        api= handleParam(api);

    }
    /**
     * 解析单个api
     * @param api
     * @return
     * @throws APINotFoundException
     */
    public static Response parseApi(ApiContent api) throws APINotFoundException {

        String name = api.getName();
        String url = api.getUrl();
        String method = api.getMethod();
        HashMap<String, Object> headers = api.getHeaders();
        String jsonPath = api.getJsonPath();
        HashMap param = api.getParam();
        String localHost = "";


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
/*        if (param != null) {
            request = request.params(param);
            log.info("配置param:" + api.getParam());
        }*/
        if (headers != null) {
            request = request.headers(api.getHeaders());
            log.info("配置header:" + headers);
        }
/*        if (jsonPath != null) {
            log.info("jsonPath is " + jsonPath);
            request = request
                    .contentType(ContentType.JSON)
                    .body(JSONTemplate.template(jsonPath));
        }*/
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

    public List<ApiContent> getContent(Model model){
        return model.contents;
    }

}
