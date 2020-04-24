package com.tengmoney.op.testcase;

import com.tengmoney.op.page.BasePage;
import com.tengmoney.op.page.MorPaperPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;


/**
* @author: zhzh.yin
* @create: 2020-04-07
**/
public class TestMorPaperPage {
    WebDriver driver = BasePage.getDriver();
    MorPaperPage page = new MorPaperPage(driver);

    @Test
    public void jumpToEditPaper(){
    }
    @Test
    public void editPaper(){

    }
}
