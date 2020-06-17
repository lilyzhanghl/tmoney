package com.tengmoney.appui;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

public class WorkPageTest {
    App basePage = App.getInstance();
    @BeforeAll
    public WorkPage toWorkPage() {
        try {
            basePage.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return basePage.toWork();
    }
    @Test
    public void testStart(){
        toWorkPage().startWork();
    }
    @Test
    public void testStop(){
        toWorkPage().startWork();
    }
}
