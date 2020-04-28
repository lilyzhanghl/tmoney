package com.tengmoney.op.practise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.tengmoney.op.page.BasePage;
import com.tengmoney.op.util.ReadYAML;
import com.tengmoney.op.util.WechatLoginConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
    private static WebDriver driver;
    public static final String src = "src/test/resources/wechatlogin.yaml";
    public static final WechatLoginConfig config = ReadYAML.getYamlConfig(src, WechatLoginConfig.class);

    public static WebDriver getDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", config.chromedrvierPath);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
            driver = new ChromeDriver(chromeOptions);
        }
        return driver;
    }

    public HashMap<String, PageObjectMethod> methods = new HashMap<>();
    public HashMap<String, PageObjectElement> elements = new HashMap<>();
    public HashMap<String, PageObjectAssert> asserts = new HashMap<>();

    public static void parseSteps(String method, String path) {
        WebDriver driver = getDriver();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        PageObjectModel model = null;
        try {
            System.out.println(path);
            model = mapper.readValue(path, PageObjectModel.class);

            parseStepsFromYaml(model.methods.get(method));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseStepsFromYaml(PageObjectMethod steps) {
        WebDriver driver = getDriver();
        steps.getSteps().forEach(step -> {
            WebElement element = null;
            String url = step.get("url");
            String id = step.get("id");
            String xpath = step.get("xpath");
            String css = step.get("css");
            String linkText = step.get("linkText");
            String aid = step.get("aid");
            String send = step.get("send");

            if (url != null) {
                driver.get(url);
                driver.manage().window().maximize();
            } else if (id != null) {
                element = getDriver().findElement(By.id(id));
            } else if (xpath != null) {
                element = getDriver().findElement(By.xpath(xpath));
            } else if (css != null) {
                element = getDriver().findElement(By.cssSelector(css));
            } else if (linkText != null) {
                element = driver.findElement(By.linkText(linkText));
            } else if (aid != null) {
                if (aid.equals("click")) {
                    element.click();
                } else if (aid.equals("hide")) {
                    String js = "arguments[0].style.display=\"none\";";
                    ((JavascriptExecutor) driver).executeScript(js, element);
                } else if (aid.equals("sendkeys")) {
                    element.sendKeys(send);
                }
            }
        });
    }

}
