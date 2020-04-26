package com.tengmoney.client;

import com.tengmoney.client.util.ReadYAML;
import com.tengmoney.client.util.WechatLoginConfig;
import com.tengmoney.client.util.ApiConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * @ClassName: BaseAPI
 * @Description: base api of all
 * @Author: zhzh.yin
 * @Date: 2020-04-23 21:31
 * @Verion: 1.0
 */
public class BaseAPI {
    public static final String src = "src/test/resources/wechatlogin.yaml";
    public static final String apisrc = "src/test/resources/apilist.yaml";
    public static final WechatLoginConfig config = ReadYAML.getYamlConfig(src, WechatLoginConfig.class);
    public static final ApiConfig apiconfig = ReadYAML.getYamlConfig(apisrc, ApiConfig.class);
    public static final String hostEnv = "test";

    public static Response loginWithCookie(String userId, String corpId) {
        String url = ((String) config.host.get(hostEnv))
                + ((String) config.auth.get("minipro"))
                + "?userId=" + userId + "&corpId=" + corpId;
        return (Response) given()
                .when()
                .get(url)
                .then()
                .log().all()
                .extract();
    }

}
