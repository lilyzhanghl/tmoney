package apiobject;

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
    public HashMap<String, String> param;

    public HashMap<String, String> getParam() {
        return param;
    }

    public void setParam(HashMap<String, String> param) {
        this.param = param;
    }
}
