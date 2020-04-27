package com.tengmoney.op.testcase;

import com.tengmoney.op.page.BasePage;
import com.tengmoney.op.page.HomePage;
import com.tengmoney.op.util.Data4TestConfig;
import com.tengmoney.op.util.ReadYAML;
import com.tengmoney.op.util.WechatLoginConfig;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import java.net.MalformedURLException;
/**
 * tmoneyapi
 * 2020/4/12 16:39
 * @author zhzh.yin
 **/
public class BaseCase {
    static WebDriver driver = BasePage.getDriver();
    public static final String testsrc ="src/test/resources/data4test.yaml";
    public static final Data4TestConfig config = ReadYAML.getYamlConfig(testsrc, Data4TestConfig.class);
    private static String userId = config.userId;
    private static String corpId = config.corpId;

    public static HomePage page ;
    @BeforeAll
    public static void loginWithCookie() throws MalformedURLException, InterruptedException {
        page= new HomePage(driver);
            page.loginWithCookie(userId,corpId);
    }

    @Test
    @Description("验证登录op是否成功")
    public void testLogin() throws InterruptedException {
        Assertions.assertTrue(page.isLoginSuccess());
    }

//    @AfterAll
    public static void quit() {
        driver.quit();
    }
}
