package com.tengmoney.op.page;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @ClassName: MorPagerPage
 * @Description:
 * @Author: zhzh.yin
 * @Date: 2020-03-31 20:25
 * @Verion: 1.0
 */
public class MorPaperPage extends BasePage {
    private WebDriver driver = BasePage.getDriver();
    public MorPaperPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath ="//span[.='新增今日早报']/.." )
    WebElement addMorPaperButton;
    @FindBy(xpath="//span[.='编辑今日早报']/..")
    WebElement editMorPaperButton;

    public AddMorPaperPage editPaper(){
        try {
            click(addMorPaperButton);
        }catch(TimeoutException e){
            click(editMorPaperButton);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new AddMorPaperPage(this.driver);
    }

}
