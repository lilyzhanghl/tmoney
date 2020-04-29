package com.tengmoney.op.practise;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: LoginConfig
 * @Description: login with yaml ,use userId and corpId
 * @Author: zhzh.yin
 * @Date: 2020-04-29 15:34
 * @Verion: 1.0
 */
public class AppLogin {
    static final Logger log = LoggerFactory.getLogger(AppLogin.class);
    String fileName = "src/test/resources/application-test.yaml";
    String fileName2 = "testcase";
    String method = "loginWithCookie";
    public AppLogin loginWithCookie(String userId, String corpId)   {
        log.info("执行："+fileName2+"的方法："+method);
        PageObjectModel
                .parseStepsUseHandledPath(method,AppLogin.class);
        return this;
    }
    public AppLogin loginWithCookieUseLocalPath(String userId, String corpId){
        log.info("执行："+fileName+"的方法："+method);
        PageObjectModel
                .parseSteps(method,fileName);
        return this;
    }
    public WebDriver getDriver(){
        return PageObjectModel
                .initDriver(fileName);
    }
}
