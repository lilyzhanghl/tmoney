package api.framework;

import api.item.ManuData;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import poexception.APINotFoundException;
import poexception.InsertValueNotInitException;
import util.JSONTemplate;
import util.LoadDefaultConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * tmoney
 * 2020/5/8 17:00
 *
 * @author zhzh.yin
 **/
@Data
@Slf4j
public class ApiContent {
    public String url;
    public String method;
    public HashMap<String, Object> headers;
    public String connection;
    public String jsonFileName;
    public HashMap<String, String> requestParam;
    public HashMap<String,String> jsonParam;
    private int flag = 0;

    /**
     * 单例模式
     */



    /**
     * 向api中传入数据
     *
     * @param api
     * @param map
     * @return
     */
    private synchronized ApiContent handleParam(ApiContent api, HashMap<ManuData, HashMap<String, String>> map) throws InsertValueNotInitException {
        log.info("handleMap:" + map);
        if (map != null) {
            if (map.containsKey(ManuData.REQUEST_PARAM)) {
                log.info("传参中有request_param");
                HashMap<String, String> newMap = map.get(ManuData.REQUEST_PARAM);
                if (api.getRequestParam() != null) {
                    HashMap<String, String> oldMap = api.getRequestParam();
                    oldMap.putAll(newMap);
                    //新的数据会覆盖旧的
                    api.setRequestParam(oldMap);
                } else {
                    api.setRequestParam(newMap);
                }

            }
            if (map.containsKey(ManuData.JSON_PARAM)) {
                log.info("传参中有request_param");
                HashMap<String, String> newMap = map.get(ManuData.JSON_PARAM);
                if (api.getJsonParam() != null) {
                    HashMap<String, String> oldMap = api.getJsonParam();
                    oldMap.putAll(newMap);
                    //新的数据会覆盖旧的
                    api.setJsonParam(oldMap);
                } else {
                    api.setJsonParam(newMap);
                }
            }
            if (map.containsKey(ManuData.JSON_FILE_NAME)) {
                log.info("传参中有jsonFileName");
                HashMap<String, String> jsonFileNameMap = map.get(ManuData.JSON_FILE_NAME);
                log.info("jsonFileMap is " + jsonFileNameMap);
                //map中设置key=jsonFileName
                String jsonFileName = jsonFileNameMap.get(ManuData.JSON_FILE_NAME.getType());
                //直接替换旧的
                String jsonPath = "src/test/resources/apiyaml/" + jsonFileName + ".json";
                api.setJsonFileName(jsonPath);
            } else {
                throw new InsertValueNotInitException("测试map的数据不对");
            }
        } else {
            log.warn("没有传入map值");
            String jsonPath = api.getJsonFileName();
            if (jsonPath != null && jsonPath != ""&&(!jsonPath.startsWith("src"))) {
                jsonPath = "src/test/resources/apiyaml/" + jsonPath + ".json";
                log.info("after handle ,the jsonPath is :" + jsonPath);
                api.setJsonFileName(jsonPath);
            }
        }
        return api;
    }


    public synchronized Response run(HashMap map) {

        handleUrl();
        try {
            handleParam(this, map);
        } catch (InsertValueNotInitException e) {
            e.printStackTrace();
        }
        try {
            return handleApi(this);
        } catch (APINotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized Response run( ) {
        handleUrl();
        try {
            handleParam(this, null);
            return handleApi(this);
        } catch (APINotFoundException | InsertValueNotInitException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static RequestSpecBuilder builder;

    /**
     * 处理host
     */
    private synchronized ApiContent handleUrl() {
        String host = LoadDefaultConfig.getHost();
        log.info("当前环境配置的host是：" + host);
        if (getUrl() != null&&flag==0) {
            String apiHost = getUrl();
            log.info("before handle ,the url is :" + apiHost);
            String url = host + apiHost;
            setUrl(url);
            log.info("after handle,当前api的url是：" + url);
            flag=1;
        }
        return this;
    }

    /**
     * 处理jsonPath
     */
    /*private synchronized ApiContent handleJsonPath(ApiContent api) {
        String jsonPath = api.getJsonFileName();
        log.info("before handle ,the jsonPath is :" + jsonPath);
        if (jsonPath != null && jsonPath != "") {
            jsonPath = "src/test/resources/apiyaml/" + jsonPath + ".json";
            log.info("after handle ,the jsonPath is :" + jsonPath);
            api.setJsonFileName(jsonPath);
        }
        return api;
    }*/


    /**
     * 解析单个api
     *
     * @param api
     * @return
     * @throws APINotFoundException
     */
    private synchronized Response handleApi(ApiContent api) throws APINotFoundException {
        String url = api.getUrl();
        String method = api.getMethod();
        HashMap<String, Object> headers = api.getHeaders();
        String jsonPath = api.getJsonFileName();
        HashMap requestParam = api.getRequestParam();
        HashMap jsonParam = api.getJsonParam();
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
        if (requestParam != null) {
            request = request.params(requestParam);
            log.info("配置param:" + requestParam);
        }
        if (headers != null) {
            request = request.headers(api.getHeaders());
            log.info("配置header:" + headers);
        }
        if (jsonPath != null) {
            if (!jsonPath.equals("")) {
                log.info("jsonPath is " + jsonPath);
                request = request
                        .contentType(ContentType.JSON)
                        .body(JSONTemplate.template(jsonPath));
            }
        }
        if (method.equals("get")) {
            Response response = request.when()
                    .log().all()
                    .get(url)
                    .then()
                    .log().all()
                    .extract()
                    .response();
            if (null == builder) {
                builder = new RequestSpecBuilder();
                log.info(response.getCookies().keySet().toString());
                builder.addCookies(response.getCookies());
            }

            return response;
        } else if (method.equals("post")) {
            Response response = request.when()
                    .log().all()
                    .post(url)
                    .then()
                    .log().all()
                    .extract()
                    .response();

            return response;
        } else {
            throw new APINotFoundException("解析失败");
        }
    }

    @Override
    public String toString() {
        return "ApiContent{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", headers=" + headers +
                ", connection='" + connection + '\'' +
                ", jsonPath='" + jsonFileName + '\'' +
                ", requestParam=" + requestParam +
                '}';
    }
}
