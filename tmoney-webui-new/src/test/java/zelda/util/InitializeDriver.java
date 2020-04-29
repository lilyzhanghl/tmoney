package zelda.util;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: InitializeDriver
 * @Description: 读取application.yaml的浏览器配置，初始化driver, 封装webdriver和element的一些操作
 * @Author: zhzh.yin
 * @Date: 2020-04-29 15:34
 * @Verion: 1.0
 */
public class InitializeDriver {
    static final Logger log = LoggerFactory.getLogger(InitializeDriver.class);
    private static WebDriver driver;
    private static String browser = LoadDefaultConfig.getBrowserVersion().get(0);
    private static String browserVersion = LoadDefaultConfig.getBrowserVersion().get(1);
    public static final WebDriver getDriver() {
        if (driver == null) {
            log.info("初始化webDriver");
            switch (browser){
                case "chrome":
                    System.setProperty("webdriver.chrome.driver",browserVersion);
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
                    driver = new ChromeDriver(chromeOptions);
                    return driver;
                case "firefox":
                    System.setProperty("webdriver.firefox.driver",browserVersion);
                    //....
            }
        }
        return driver;
    }


    public static void waitForLoad(WebDriver driver, final By locator, int timeOut) {
        log.info("等待By操作搜索element，最长等待（秒）："+timeOut);
        WebDriverWait wait = new WebDriverWait(driver, timeOut);// timeOut为等待时间，单位秒
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean loadcomplete = d.findElement(locator).isDisplayed();
                log.info("元素查找结果为："+loadcomplete);
                return loadcomplete;
            }
        });
    }

    public static void waitForLoad(WebDriver driver,final WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 5);// timeOut为等待时间，单位秒
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean loadcomplete = element.isDisplayed();
                return loadcomplete;
            }
        });
    }

    public static void click(WebDriver driver, WebElement element) {
        log.info("即将点击元素，执行顺序：等待-移动-点击");
        waitForLoad(driver,element);
        moveTo(driver,element);
        element.click();
    }

    public static void moveTo(WebDriver driver, WebElement element) {
        log.info("等待元素加载---");
        if (hasElement(driver,element)) {
            String js = "arguments[0].scrollIntoView(true);";
            ((JavascriptExecutor) driver).executeScript(js, element);
        } else {
            throw new NoSuchElementException("找不到该元素");
        }
    }

    public static void hideElement(WebDriver driver, WebElement element) {
        log.info("隐藏元素");
        String js = "arguments[0].style.display=\"none\";";
        ((JavascriptExecutor) driver).executeScript(js, element);
    }

    public static Boolean hasElement(WebDriver driver, WebElement element) {
        log.info("查看是否有该元素");
        try {
            waitForLoad(driver,element);
            if (element.isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            log.warn("找不到目标元素");
            e.printStackTrace();
            return false;
        }
    }

    public static void sendKeys(WebDriver driver, WebElement element, String str) {
        log.info("在输入框中键入值："+str);
        waitForLoad(driver,element);
        moveTo(driver,element);
        element.sendKeys(str);
    }
    public static WebElement findElement(WebDriver driver,By by){
        log.info("开始查找元素---");
        waitForLoad(driver,by,5);
        return driver.findElement(by);
    }
    public Boolean isDisplayed(WebDriver driver,By by) {
        try {
            log.info("查看元素是否展示");
            findElement(driver,by).isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
