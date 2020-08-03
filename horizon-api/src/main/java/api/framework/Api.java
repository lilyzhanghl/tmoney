package api.framework;

import api.dto.CorpDTO;
import api.dto.StaffDTO;
import api.item.Manu;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import poexception.ApiNotFoundException;
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
    public HashMap<String, String> requestParam;
    public HashMap<String, String> jsonParam;
    private int flag = 0;
    public String jsonFilePath;

    public void login() {
        log.info("登录对应类型");
    }

    /**
     * 导入默认配置
     */


    protected synchronized void importDefaultConfig() {
        log.info("做一些yaml配置的参数导入操作");
        HashMap<String, String> configMap = this.getRequestParam();
        HashMap<String, String> defaultMap = new HashMap();
        if (null == configMap) {
            configMap = new HashMap();
        }

        CorpDTO corp = LoadDefaultConfig.getCorp();
        log.info("corp is " + corp);
        StaffDTO staff = LoadDefaultConfig.getStaff();
        log.info("staff is " + staff);

        defaultMap.put("authCorpId", corp.corpId);
        defaultMap.put("currentCorpId", corp.corpId);
        defaultMap.put("userId", staff.userId);
        defaultMap.put("corpId", corp.corpId);

        defaultMap.keySet().removeAll(configMap.keySet());
        configMap.putAll(defaultMap);
        this.setRequestParam(configMap);
    }

    /**
     * 向api中传入数据
     *
     * @param map
     * @return
     */
    private synchronized Api handleParam(HashMap<Manu, HashMap<String, String>> map) {
        log.info("handleMap:" + map);
        if (map != null) {
            if (map.containsKey(Manu.REQUEST_PARAM)) {
                log.info("传参中有request_param");
                HashMap<String, String> newMap = map.get(Manu.REQUEST_PARAM);
                if (this.getRequestParam() != null) {
                    HashMap<String, String> oldMap = this.getRequestParam();
                    oldMap.putAll(newMap);
                    //新的数据会覆盖旧的
                    this.setRequestParam(oldMap);
                    log.info("当前 map参数为：" + oldMap);
                } else {
                    log.info("当前 map参数为：" + newMap);
                    this.setRequestParam(newMap);
                }
            }
            if (map.containsKey(Manu.JSON_PARAM)) {
                log.info("传参中有request_param");
                HashMap<String, String> newMap = map.get(Manu.JSON_PARAM);
                if (this.getJsonParam() != null) {
                    HashMap<String, String> oldMap = this.getJsonParam();
                    oldMap.putAll(newMap);
                    //新的数据会覆盖旧的

                    this.setJsonParam(oldMap);
                } else {
                    this.setJsonParam(newMap);
                }
            }
            if (map.containsKey(Manu.JSON_FILE_NAME)) {
                log.info("传参中有jsonFileName");
                HashMap<String, String> jsonFileNameMap = map.get(Manu.JSON_FILE_NAME);
                log.info("jsonFileMap is " + jsonFileNameMap);
                //map中设置key=jsonFileName
                String jsonFileName = jsonFileNameMap.get(Manu.JSON_FILE_NAME.getType());
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
        importDefaultConfig();
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
        importDefaultConfig();
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
        log.info(this.toString());
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
            if (requestParam != null) {
                log.info("当前请求的参数map为：" + requestParam);
                request = request.params(requestParam);
                log.info("配置param:" + requestParam);
            }
            Response response = request.get(url)
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
