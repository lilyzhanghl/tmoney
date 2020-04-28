package com.tengmoney.op.practise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
/**
 * @ClassName: PageObjectModel
 * @Description: pageObject model
 * @Author: zhzh.yin
 * @Date: 2020-04-27 16:59
 * @Verion: 1.0
 */
public class PageObjectModel {
    static final Logger log = LoggerFactory.getLogger(PageObjectModel.class);

    public HashMap<String,String> basis = new HashMap<String, String>();
    public HashMap<String, PageObjectMethod> methods = new HashMap<String,PageObjectMethod>();
    public HashMap<String, PageObjectElement> elements = new HashMap<String,PageObjectElement>();
    public HashMap<String, PageObjectAssert> asserts = new HashMap<String,PageObjectAssert>();

    private static WebDriver driver;

    /**
     *
     * @param filePath yaml配置文件路径
     * @param clazz 要转换yaml的目标配置类的class
     * @param <T> 目标配置类
     * @return 返回读出来的配置
     */
    private static <T> T getYamlConfig(String filePath, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        try {
            T t =mapper.readValue(
                    new File(filePath),
                    clazz);
            log.info("读取配置yaml");
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static final WebDriver setDriver(PageObjectModel model) {
        if (driver == null) {
            log.info("初始化webDriver");
            System.setProperty("webdriver.chrome.driver",model.basis.get("chromeDriverPath"));
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
            driver = new ChromeDriver(chromeOptions);
        }
        return driver;
    }
    public static void parseSteps(String method, String path) {
        PageObjectModel model = getYamlConfig(path,PageObjectModel.class);
        log.info("载入yaml中写的执行步骤");
        parseStepsFromYaml(model,model.methods.get(method));
    }
    public static void waitForLoad(WebDriver driver, final By locator, int timeOut) {
        log.info("等待By操作搜索element，最长等待（秒）："+timeOut);
        WebDriverWait wait = new WebDriverWait(driver, timeOut);// timeOut为等待时间，单位秒
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean loadcomplete = d.findElement(locator).isDisplayed();
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
    private static void parseStepsFromYaml(PageObjectModel model ,PageObjectMethod steps) {
        WebDriver driver = setDriver(model);
        steps.getStep().forEach(step -> {
            WebElement element = null;
            String url = step.get("url");
            String id = step.get("id");
            String xpath = step.get("xpath");
            String css = step.get("css");
            String linkText = step.get("linkText");
            String aid = step.get("aid");
            String send = step.get("send");

            if (url != null) {
                log.info("访问链接："+url);
                driver.get(url);
                log.info("最大化窗口");
                driver.manage().window().maximize();
            } else if (id != null) {
                log.info("按照id搜索元素，id="+id);
                element = findElement(driver,By.id(id));
            } else if (xpath != null) {
                log.info("按照xpath搜索元素，xpath="+xpath);
                element = findElement(driver,By.xpath(xpath));
            } else if (css != null) {
                log.info("按照css搜索元素，css="+css);
                element = findElement(driver,By.cssSelector(css));
            } else if (linkText != null) {
                log.info("按照linktext搜索元素，linktext="+linkText);
                element = findElement(driver,By.linkText(linkText));
            } else if (aid != null) {
                if (aid.equals("click")) {
                    log.info("点击元素");
                    click(driver,element);
                } else if (aid.equals("hide")) {
                    log.info("隐藏元素");
                    hideElement(driver,element);
                } else if (aid.equals("sendkeys")) {
                    log.info("键入输入值");
                    sendKeys(driver,element,send);
                }
            }
        });
    }

}
