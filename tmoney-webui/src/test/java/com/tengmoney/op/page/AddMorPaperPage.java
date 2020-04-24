package com.tengmoney.op.page;

import commons.ReadProperties;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * tmoneyapi
 * 2020/4/12 17:08
 *
 * @author zhzh.yin
 **/
public class AddMorPaperPage extends BasePage {
    private WebDriver driver = BasePage.getDriver();
    String url = ReadProperties.getInstance().getConfig("newsUrl");

    public AddMorPaperPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    //todo 新增早报

    @FindBy(xpath = "//input[@placeholder='请输入早报标题']")
    WebElement titleSentence;
    @FindBy(xpath = "//textarea[@placeholder='请输入今日综述']")
    WebElement summarySentence;
    @FindBy(xpath = "//textarea[@placeholder='请输入一日谈']")
    WebElement topicSentence;
    @FindBy(css = ".show-menu-hearder.ant-layout-header")
    WebElement titleBanner;

    //宏观时事
    @FindBy(xpath = "//span[text()=\"宏观时事\"]/..")
    WebElement macroTab;
    @FindBy(css = ".noMarginBottom .ant-form-item-control button:nth-child(2)")
    WebElement marketTab;
    @FindBy(css = ".noMarginBottom .ant-form-item-control button:nth-child(3)")
    WebElement industryTab;
    @FindBy(css = ".noMarginBottom .ant-form-item-control button:nth-child(4)")
    WebElement financeTab;
    //    @FindBy(xpath = "//span[text()=\"地产楼市\"]/..")
    @FindBy(css = ".noMarginBottom .ant-form-item-control button:nth-child(5)")
    WebElement estateTab;
    //    @FindBy(xpath = "//span[text()=\"全球视角\"]/..")
    @FindBy(css = ".noMarginBottom .ant-form-item-control button:nth-child(6)")
    WebElement globalTab;

    @FindBy(xpath = "//span[text()=\" 增加文章\"]/..")
    WebElement addButton;

    @FindBy(css = "#rcDialogTitle0")
    WebElement popWindowTitle;

    @FindBy(xpath = "//input[@placeholder='文章地址']")
    WebElement urlSentence;
    @FindBy(xpath = "//span[text()=\"确 定\"]/..")
    WebElement confirmButton;

    @FindBy(xpath = "//textarea[@placeholder='请输入概述']")
    WebElement overviewSentence;
    @FindBy(xpath = "//textarea[@placeholder='请输入点评']")
    WebElement reviewSentence;
    @FindBy(xpath = "//span[text()=\"添加成功\"]/..")
    WebElement floatWindow;


    @FindBy(xpath = "//span[text()=\"取 消\"]/..")
    WebElement cancelButton;

    @FindBy(xpath = "//span[text()=\"预 览\"]/..")
    WebElement previewButton;
    @FindBy(xpath = "//span[text()=\"保 存\"]/..")
    WebElement saveButton;
    @FindBy(xpath = "//span[text()=\"发 布\"]/..")
    WebElement releaseButton;

    private void fillSentence() {
        sendKeys(titleSentence, (new SimpleDateFormat("YYYY-MM-dd").format(new Date())) + "早报");
        sendKeys(summarySentence, "GitHub is built for collaboration.");
        sendKeys(topicSentence, "Set up an organization to improve the way your team works together");
    }

    private void addNews() {
        ArrayList<WebElement> newsList = new ArrayList<WebElement>() {{
            add(macroTab);
            add(marketTab);
            add(industryTab);
            add(financeTab);
            add(estateTab);
            add(globalTab);
        }};
        for (WebElement newsTab : newsList
        ) {
            try {
                click(newsTab);
            } catch (Exception e) {
                while (hasElement(floatWindow)) ;
                click(newsTab);
            }
            click(addButton);
            sendKeys(urlSentence,url);
            click(confirmButton);
            while(hasElement(confirmButton));
            sendKeys(overviewSentence,"overview");
            sendKeys(reviewSentence,"review");
        }

    }

    private void releasePaper() {
//        click(previewButton);
        click(saveButton);
        click(confirmButton);
//        click(releaseButton);
    }

    public MorPaperPage addMorPaper() {
        fillSentence();
        addNews();
        releasePaper();
        return new MorPaperPage(driver);
    }
}
