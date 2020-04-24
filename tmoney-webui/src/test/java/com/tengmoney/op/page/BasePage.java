package com.tengmoney.op.page;

import commons.ReadProperties;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * tmoneyapi
 * 2020/4/15 23:24
 * loginPage
 *
 * @author zhzh.yin
 **/
public class BasePage {
    private static WebDriver driver;
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public static WebDriver getDriver (){
        if(driver==null){
            System.setProperty("webdriver.chrome.driver", ReadProperties.getInstance().getConfig("chromedrvierpath"));
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
            driver = new ChromeDriver(chromeOptions);
        }
        return driver;
    }
    public static void waitForLoad(WebDriver driver, final By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);// timeOut为等待时间，单位秒
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean loadcomplete = d.findElement(locator).isDisplayed();
                return loadcomplete;
            }
        });
    }
    public static void waitForLoad(final WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);// timeOut为等待时间，单位秒
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean loadcomplete = element.isDisplayed();
                return loadcomplete;
            }
        });
    }
    public void click(WebElement element){
        waitForLoad(element);
        moveTo(element);
        element.click();
    }
    public void moveTo(WebElement element) {
        if (hasElement(element)) {
            String js = "arguments[0].scrollIntoView(true);";
            ((JavascriptExecutor) driver).executeScript(js, element);
        } else {
            throw new NoSuchElementException("找不到该元素");
        }
    }
    public void hideElement(WebElement element){
        String js = "arguments[0].style.display=\"none\";";
        ((JavascriptExecutor) driver).executeScript(js, element);
    }
    public Boolean hasElement(WebElement element) {
        try {
            if (element.isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("找不到目标元素");
            e.printStackTrace();
            return false;
        }
    }
    public void sendKeys(WebElement element,String str){
        waitForLoad(element);
        moveTo(element);
        element.sendKeys(str);
    }
    public Boolean isDisplayed(By by) {
        try {
            driver.findElement(by).isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
