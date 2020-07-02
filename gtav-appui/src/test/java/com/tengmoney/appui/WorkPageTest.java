package com.tengmoney.appui;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkPageTest {
    private static HomePage basePage = HomePage.getInstance();
    private static WorkPage workPage;
    @BeforeAll
    public static void toWorkPage() throws MalformedURLException {
            basePage.start();
        workPage= basePage.toWork();
    }
    @Test
    public void testLazy(){
        workPage.click();
        assertTrue(workPage.getResult().equals("今日打卡已完成，好好休息"));
    }
    @AfterAll
    public static void shutDown(){
        workPage.quit();
    }
}
