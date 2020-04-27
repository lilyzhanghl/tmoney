package com.tengmoney.op.testcase;

import com.tengmoney.op.page.BasePage;
import com.tengmoney.op.page.HomePage;
import com.tengmoney.op.page.MorPaperPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestAddMorPaperPage extends BaseCase {
    private WebDriver driver = BasePage.getDriver();
    HomePage homePage=new HomePage(driver);
    MorPaperPage morPaperPage =homePage.toMorPaper();
    @Test
    @Description("验证创建早报")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddMorPaper() throws InterruptedException {
        morPaperPage = morPaperPage.editPaper().addMorPaper();
        String paperName = (new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
        Assertions.assertTrue(driver.getPageSource().contains(paperName));
    }
}
