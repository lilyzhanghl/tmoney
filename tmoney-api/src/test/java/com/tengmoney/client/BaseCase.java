package com.tengmoney.client;

import com.tengmoney.client.util.ApiConfig;
import com.tengmoney.client.util.ReadYAML;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * @ClassName: TestLogin
 * @Description: testcase of login
 * @Author: zhzh.yin
 * @Date: 2020-04-22 20:51
 * @Verion: 1.0
 */
public class BaseCase extends BaseAPI{
    public static  BaseAPI baseapi;
    public static final String testsrc = "src/test/resources/apilist.yaml";
    public static final ApiConfig apiconfig = ReadYAML.getYamlConfig(testsrc,ApiConfig.class);
    @BeforeAll
    public static void setUp(){
        baseapi.loginWithCookie(apiconfig.userId,apiconfig.corpId);
    }
    @Test
    @Description("登录成功")
    public void loginSuccess(){
        baseapi.loginWithCookie(apiconfig.userId,apiconfig.corpId)
                .then()
                .body("ret",equalTo(0));
    }
    @Test
    @Description("登录失败")
    public void loginFailure(){
        baseapi.loginWithCookie(apiconfig.userId,"")
                .then()
                .body("ret",not(0));
    }

}
