package com.tengmoney.op.page;

import com.tengmoney.op.util.ReadYAML;
import com.tengmoney.op.util.WechatLoginConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

/**
 * @ClassName: Homepage
 * @Description:
 * @Author: zhzh.yin
 * @Date: 2020-03-31 19:32
 * @Verion: 1.0
 */
public class HomePage extends BasePage {
    private WechatLoginConfig config = ReadYAML.getYamlConfig(src, WechatLoginConfig.class);
    public static final String hostEnv = "test";

    private String loginURL = ((String) config.host.get(hostEnv))
            + ((String) config.auth.get("op").toString());
    private String homepageURL = ((String) config.host.get(hostEnv))
            + ((String) config.homepage.get("op").toString());
    private WebDriver driver = BasePage.getDriver();

    public HomePage(WebDriver driver) {
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

    public HomePage loginWithCookie(String userId, String corpId) throws InterruptedException {
        Thread.sleep(500);
        driver.get(loginURL + "?userId=" + userId + "&corpId=" + corpId);
        Thread.sleep(500);
        driver.get(homepageURL);
        driver.manage().window().maximize();
        if (hasElement(login)) {
            click(login);
        }
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
    //todo 其他page
/*    public NoonPaperPage toNoonPager(){
        newsManagerButton.click();
        noonPageButton.click();
        return new NoonPaperPage(this.driver);
    }

    public NewsListPage toNewsList(){

        return new NewsListPage(this.driver);
    }*/

}
