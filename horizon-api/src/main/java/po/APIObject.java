package po;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * tmoney
 * 2020/5/8 17:00
 *
 * @author zhzh.yin
 **/
@Data
public class APIObject {
    String url;
    String method;
    HashMap<String,Object> headers;
    String connection;
    String host;
    List<String> params;
    List<String> cookies;
    List<String> json ;
    String jsonFile;

    @Override
    public String toString() {
        return "APIObject{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", headers=" + headers +
                ", connection='" + connection + '\'' +
                ", host='" + host + '\'' +
                ", params=" + params +
                ", cookies=" + cookies +
                ", json='" + json + '\'' +
                ", jsonFile='" + jsonFile + '\'' +
                '}';
    }



    public String getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
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


}
