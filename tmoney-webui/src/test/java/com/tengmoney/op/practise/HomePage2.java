package com.tengmoney.op.practise;

import com.tengmoney.op.page.BasePage;
import com.tengmoney.op.page.MorPaperPage;
import com.tengmoney.op.util.ReadYAML;
import com.tengmoney.op.util.WechatLoginConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @ClassName: HomePage2
 * @Description: yaml剥离数据
 * @Author: zhzh.yin
 * @Date: 2020-04-27 18:59
 * @Verion: 1.0
 */
public class HomePage2 extends BasePage {
/*
//todo move the basepage to here
    private static WebDriver driver;
    public static final String src = "src/test/resources/wechatlogin.yaml";
    public static final WechatLoginConfig config = ReadYAML.getYamlConfig(src,WechatLoginConfig.class);*/
    private WechatLoginConfig config = ReadYAML.getYamlConfig(src, WechatLoginConfig.class);
    public static final String hostEnv = "test";

    private String loginURL = ((String) config.host.get(hostEnv))
            + ((String) config.auth.get("op").toString());
    private String homepageURL = ((String) config.host.get(hostEnv))
            + ((String) config.homepage.get("op").toString());
    private WebDriver driver = BasePage.getDriver();

    public HomePage2(WebDriver driver) {
        super(driver);
    }


    @FindBy(css = ".show-menu-hearder.ant-layout-header")
    WebElement titleBanner;
    @FindBy(css = "button.authBtn")
    WebElement login;
    @FindBy(xpath = "//span[.='文章管理']")
    WebElement newsManagerButton;
    @FindBy(linkText = "机构管理")
    WebElement corpManagerButton;
    @FindBy(linkText = "快捷回复")
    WebElement fastReplyButton;
    @FindBy(linkText = "使用分析")
    WebElement usageAnalysisButton;

    @FindBy(linkText = "每日早报")
    WebElement morPaperButton;
    @FindBy(linkText = "午间趣谈")
    WebElement noonPageButton;

    public Boolean isLoginSuccess() {
        waitForLoad(newsManagerButton);
        return hasElement(newsManagerButton);
    }

    public HomePage2 loginWithCookie(String userId, String corpId) throws InterruptedException {
        Thread.sleep(500);
        driver.get(loginURL + "?userId=" + userId + "&corpId=" + corpId);
        Thread.sleep(500);
        driver.get(homepageURL);
        driver.manage().window().maximize();
        if (hasElement(login)) {
            click(login);
        }
        waitForLoad(titleBanner);
        hideElement(titleBanner);
        return this;
    }


    public MorPaperPage toMorPaper() {
        click(newsManagerButton);
//        newsManagerButton.click();
        click(morPaperButton);
//        morPaperButton.click();
        return new MorPaperPage(this.driver);
    }
}
