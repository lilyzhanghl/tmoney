package com.tengmoney.op.testcase;

import com.tengmoney.op.page.BasePage;
import com.tengmoney.op.page.HomePage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import java.net.MalformedURLException;
/**
 * tmoneyapi
 * 2020/4/12 16:39
 * @author zhzh.yin
 **/
public class BaseCase {
    static WebDriver driver = BasePage.getDriver();
    public static HomePage page ;
    @BeforeAll
    public static void loginWithCookie() throws MalformedURLException {
        page= new HomePage(driver);
        try {
            page.loginWithCookie();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLogin() throws InterruptedException {
        Assertions.assertTrue(page.isLoginSuccess(),"登录失败");
    }

    @AfterAll
    public static void quit() {
        driver.quit();
    }
}
