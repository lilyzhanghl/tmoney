package api.framework;

import lombok.Data;

import java.util.HashMap;

/**
 * tmoney
 * 2020/5/8 17:00
 *
 * @author zhzh.yin
 **/
@Data
public class ApiContent {
    public String name;
    public String url;
    public String method;
    public HashMap<String, Object> headers;
    public String connection;
    //    public String host;
//    public String jsonParams;
    public String jsonPath;
    public HashMap<String, String> requestParam;

    public void run(){}




    public HashMap<String, String> getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(HashMap<String, String> requestParam) {
        this.requestParam = requestParam;
    }

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

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    @Override
    public String toString() {
        return "ApiContent{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", headers=" + headers +
                ", connection='" + connection + '\'' +
                ", jsonPath='" + jsonPath + '\'' +
                ", requestParam=" + requestParam +
                '}';
    }
}
