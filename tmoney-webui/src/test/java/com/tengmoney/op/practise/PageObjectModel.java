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

    /**
     * 加工普通测试类的yaml地址，默认该yaml地址和测试类同一个路径
     * 且yaml文件名称为该测试类的小写
     * @return
     */
    private static String transClasspathToYamlpath(Class clazz){
        return "src/test/java/"+clazz.getCanonicalName()
                .replace(".","/")
                .toLowerCase()
                +".yaml";
    }
    private String handleMethod(Class clazz){
        return "";
    }

    /**
     * 设置解析普通的yaml文件的step的名称
     * @param method
     */
    public static void parseStepsUseHandledPath(String method,Class frontTestClazz) {
        String path = transClasspathToYamlpath(frontTestClazz);
        PageObjectModel model = getYamlConfig(transClasspathToYamlpath(frontTestClazz),PageObjectModel.class);
        log.info("载入yaml中写的执行步骤");
        parseStepsFromYaml(path,model.methods.get(method));
    }

    /**
     * 设置解析的step的名称
     * @param method
     */
    public static void parseSteps(String method,String path) {
        PageObjectModel model = getYamlConfig(path,PageObjectModel.class);
        log.info("载入yaml中写的执行步骤");
        parseStepsFromYaml(path,model.methods.get(method));
    }
    public static String parseBasis(String key,String path){
        PageObjectModel model = getYamlConfig(path,PageObjectModel.class);
        log.info("读取basis");
        return model.basis.get(key);
    }
    public static WebDriver initDriver(String path){
        return InitializeDriver.getDriver(parseBasis("chromeDriverPath",path));
    }
    /**
     * 解析step，将yaml中的配置转化为操作
     * @param steps
     */
    private static void parseStepsFromYaml(String path ,PageObjectMethod steps) {
        WebDriver driver = initDriver(path);
        steps.getStep().forEach(step -> {
            WebElement element = null;
            String url = step.get("url");
            String id = step.get("id");
            String xpath = step.get("xpath");
            String css = step.get("css");
            String linkText = step.get("linkText");
            String aid = step.get("aid");
            String send = step.get("send");
            log.info("aid is "+aid);
            if (url != null) {
                log.info("访问链接："+url);
                driver.get(url);
                log.info("最大化窗口");
                driver.manage().window().maximize();
            } else if (id != null) {
                log.info("按照id搜索元素，id="+id);
                element = InitializeDriver.findElement(driver,By.id(id));
            } else if (xpath != null) {
                log.info("按照xpath搜索元素，xpath="+xpath);
                element = InitializeDriver.findElement(driver,By.xpath(xpath));
            } else if (css != null) {
                log.info("按照css搜索元素，css="+css);
                element = InitializeDriver.findElement(driver,By.cssSelector(css));
            } else if (linkText != null) {
                log.info("按照linktext搜索元素，linktext="+linkText);
                element = InitializeDriver.findElement(driver,By.linkText(linkText));
            }
            if (aid != null) {
                if (aid.equals("click")) {
                    log.info("点击元素:"+element.getText());
                    InitializeDriver.click(driver,element);
                } else if (aid.equals("hide")) {
                    log.info("隐藏元素");
                    InitializeDriver.hideElement(driver,element);
                } else if (aid.equals("sendkeys")) {
                    log.info("键入输入值");
                    InitializeDriver.sendKeys(driver,element,send);
                }
            }
        });
    }

}
