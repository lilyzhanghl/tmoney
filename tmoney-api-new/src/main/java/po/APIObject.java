package po;

import java.util.HashMap;

/**
 * tmoney
 * 2020/5/8 17:00
 *
 * @author zhzh.yin
 **/
public class APIObject {
    String url;
    String method;
    HashMap<String,Object> headers;
    String connection;
    String host;
    List<String ,Object> params;
    List<String,Object>cookies;
    String json ;
    String jsonFile;

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

    public List<String, Object> getCookies() {
        return cookie;
    }

    public void setCookies(List<String, Object> cookies) {
        this.cookies = cookies;
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

    public List<String, Object> getParams() {
        return params;
    }

    public void setParams(List<String, Object> params) {
        this.params = params;
    }
}
