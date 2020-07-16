package api.framework;

import auto.framework.Content;
import lombok.Data;

import java.util.HashMap;

/**
 * tmoney
 * 2020/5/8 17:00
 *
 * @author zhzh.yin
 **/
@Data
public class ApiContent extends Content {
    String name;
    String url;
    String method;
    HashMap<String, Object> headers;
    String connection;
    String host;
    String params;
    String json;
    String jsonFile;
    public HashMap<String, String> param;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public HashMap<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, Object> headers) {
        this.headers = headers;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public HashMap<String, String> getParam() {
        return param;
    }

    public void setParam(HashMap<String, String> param) {
        this.param = param;
    }
}
