package api.item;/**
 * @author zhzh.yin
 * @create 2020-08-05 13:37
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 〈ApiType〉
 *
 * @author zhzh.yin
 * @create 2020/8/5
 */
public enum AppType {
    MINIPRO("minipro"),
    H5STATION("h5station"),
    H5PRODUCT("h5product"),
    MANAGE("manage"),
    MARKET("market");
    private final String apiType;
    AppType(String apiType){
        this.apiType=apiType;
    }
    @JsonCreator
    public static AppType fromString(String apiType){
        return apiType ==null
                ? null
                : AppType.valueOf(apiType.toUpperCase());
    }
    @JsonValue
    public String getEnv() {
        return apiType;
    }
}
