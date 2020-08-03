package api.item;/**
 * @author zhzh.yin
 * @create 2020-08-05 16:10
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 〈环境〉
 *
 * @author zhzh.yin
 * @create 2020/8/5
 */

public enum Env {

    TEST("test"),
    DEV("dev"),
    PROD("prod"),
    UAT("uat");
    private String env;
    Env(String env){
        this.env=env;
    }
    @JsonCreator
    public static Env fromString(String env){
        return env ==null
                ? null
                : Env.valueOf(env.toUpperCase());
    }
    @JsonValue
    public String getEnv() {
        return env;
    }
}
