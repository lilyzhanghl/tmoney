package po;

import lombok.Data;

import java.util.HashMap;

/**
 * tmoney
 * 2020/5/8 17:02
 *
 * @author zhzh.yin
 **/
@Data
public class APIObjectParam {
    public HashMap<String,String> param;
    public HashMap<String,String> cookie;
    public HashMap<String,String> body;
    public HashMap<String,String> header;
    public HashMap<String,String> json;

    public HashMap<String, String> getJson() {
        return json;
    }

    public void setJson(HashMap<String, String> json) {
        this.json = json;
    }


    public HashMap<String, String> getCookie() {
        return cookie;
    }

    public void setCookie(HashMap<String, String> cookie) {
        this.cookie = cookie;
    }

    public HashMap<String, String> getBody() {
        return body;
    }

    public void setBody(HashMap<String, String> body) {
        this.body = body;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    public HashMap<String, String> getParam() {
        return param;
    }

    public void setParam(HashMap<String, String> param) {
        this.param = param;
    }
}
