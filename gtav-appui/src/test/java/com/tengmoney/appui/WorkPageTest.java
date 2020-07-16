package com.tengmoney.appui;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkPageTest {
    private static HomePage homePage ;

    @BeforeAll
    static void beforAll() throws MalformedURLException {
        homePage = new HomePage();
    }
    @Test
    public void testLazy(){
        assertTrue(homePage.toWork().click().getResult().equals("今日打卡已完成，好好休息"));
    }
    @AfterAll
    public static void shutDown(){
        homePage.quit();
    }
}
