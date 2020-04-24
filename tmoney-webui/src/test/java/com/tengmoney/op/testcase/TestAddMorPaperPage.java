package com.tengmoney.op.testcase;

import com.tengmoney.op.page.BasePage;
import com.tengmoney.op.page.HomePage;
import com.tengmoney.op.page.MorPaperPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestAddMorPaperPage extends BaseCase {
    private WebDriver driver = BasePage.getDriver();
    HomePage homePage=new HomePage(driver);
    MorPaperPage morPaperPage =homePage.toMorPaper();
//    @Test
    @Description("create a morning paper.")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddMorPaper() throws InterruptedException {
        morPaperPage = morPaperPage.editPaper().addMorPaper();
        String paperName = (new SimpleDateFormat("YYYY-MM-dd").format(new Date())) + "早报";
        Assertions.assertTrue(driver.getPageSource().contains(paperName),"判断新增日报:"+paperName+"是否成功");
        //todo jdbc
    }

}
