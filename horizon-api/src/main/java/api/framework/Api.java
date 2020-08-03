package api.framework;

import api.item.ManuData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import poexception.ApiNotFoundException;
import poexception.InsertValueNotInitException;
import util.JsonTemplate;
import util.LoadDefaultConfig;

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
public class Api {
    public String url;
    public String method;
    public HashMap<String, Object> headers;
    public String connection;
    public String jsonFileName;

    public String getJsonFilePath() {
        return jsonFilePath;
    }

    public void setJsonFilePath(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }

    private String jsonFilePath;
    public HashMap<String, String> requestParam;
    public HashMap<String, String> jsonParam;
    private int flag = 0;

    /**
     * 向api中传入数据
     *
     * @param map
     * @return
     */
    private synchronized Api handleParam(HashMap<ManuData, HashMap<String, String>> map){
        log.info("handleMap:" + map);
        if (map != null) {
            if (map.containsKey(ManuData.REQUEST_PARAM)) {
                log.info("传参中有request_param");
                HashMap<String, String> newMap = map.get(ManuData.REQUEST_PARAM);
                if (this.getRequestParam() != null) {
                    HashMap<String, String> oldMap = this.getRequestParam();
                    oldMap.putAll(newMap);
                    //新的数据会覆盖旧的
                    this.setRequestParam(oldMap);
                } else {
                    this.setRequestParam(newMap);
                }
            }
            if (map.containsKey(ManuData.JSON_PARAM)) {
                log.info("传参中有request_param");
                HashMap<String, String> newMap = map.get(ManuData.JSON_PARAM);
                if (this.getJsonParam() != null) {
                    HashMap<String, String> oldMap = this.getJsonParam();
                    oldMap.putAll(newMap);
                    //新的数据会覆盖旧的
                    this.setJsonParam(oldMap);
                } else {
                    this.setJsonParam(newMap);
                }
            }
            if (map.containsKey(ManuData.JSON_FILE_NAME)) {
                log.info("传参中有jsonFileName");
                HashMap<String, String> jsonFileNameMap = map.get(ManuData.JSON_FILE_NAME);
                log.info("jsonFileMap is " + jsonFileNameMap);
                //map中设置key=jsonFileName
                String jsonFileName = jsonFileNameMap.get(ManuData.JSON_FILE_NAME.getType());
                //直接替换旧的
                //d = a.replace(a.split("/")[a.split("/").length-1], d)+".json"
                //
                String jsonPath = this.getJsonFilePath() + jsonFileName + ".json";
                this.setJsonFileName(jsonPath);
            } else {
                log.error("传入的map数据不正确，不加工");
            }
        } else {
            log.warn("没有传入map值");
            String jsonPath = this.getJsonFileName();
            if (jsonPath != null && jsonPath != "" && (!jsonPath.contains("/"))) {
                jsonPath = this.getJsonFilePath() + jsonPath + ".json";
                log.info("after handle ,the jsonPath is :" + jsonPath);
                this.setJsonFileName(jsonPath);
            }
        }
        return this;
    }

    public synchronized Response run(HashMap map) {
        handleUrl();
            handleParam(map);
        try {
            return handleApi();
        } catch (ApiNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized Response run() {
        handleUrl();
        try {
            handleParam(null);
            return handleApi();
        } catch (ApiNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static RequestSpecBuilder builder;

    /**
     * 处理host
     */
    private synchronized Api handleUrl() {
        String host = LoadDefaultConfig.getHost();
        log.info("当前环境配置的host是：" + host);
        if (getUrl() != null && flag == 0) {
            String apiHost = getUrl();
            log.info("before handle ,the url is :" + apiHost);
            String url = host + apiHost;
            setUrl(url);
            log.info("after handle,当前api的url是：" + url);
            flag = 1;
        }
        return this;
    }

    /**
     * 解析单个api
     *
     * @return
     * @throws ApiNotFoundException
     */
    private synchronized Response handleApi() throws ApiNotFoundException {
        String url = this.getUrl();
        String method = this.getMethod();
        HashMap<String, Object> headers = this.getHeaders();
        String jsonPath = this.getJsonFileName();
        HashMap requestParam = this.getRequestParam();
        HashMap jsonParam = this.getJsonParam();
        if (method == "" || method.equals(null)) {
            throw new ApiNotFoundException("没有写入method");
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
            request = request.headers(this.getHeaders());
            log.info("配置header:" + headers);
        }
        if (jsonPath != null) {
                log.info("jsonPath is " + jsonPath);
                request = request
                        .contentType(ContentType.JSON)
                        .body(JsonTemplate.template(jsonPath));
        }
        request = request.when()
                .log().all();

        if (method.toUpperCase().equals(Method.GET.toString())) {
            Response response = request.get(url)
                    .then()
                    .log().all()
                    .extract()
                    .response();
            if (null==builder){
                builder = new RequestSpecBuilder();
                log.info(response.getCookies().keySet().toString());
                builder.addCookies(response.getCookies());
            }
            return response;
        } else if (method.toUpperCase().equals(Method.POST.toString())) {
            Response response = request.post(url)
                    .then()
                    .log().all()
                    .extract()
                    .response();
            return response;
        } else {
            throw new ApiNotFoundException("解析失败");
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
