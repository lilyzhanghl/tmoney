package zelda.po;

import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zelda.util.InitializeDriver;
import zelda.util.LoadDefaultConfig;
import zelda.util.ReadYAML;

import java.util.HashMap;

/**
 * @ClassName: PageObjectModel
 * @Description: 读取用例yaml的方法，参数，断言
 * @Author: zhzh.yin
 * @Date: 2020-04-27 16:59
 * @Verion: 1.0
 */
public class PageObjectModel {
    private static final Logger log = LoggerFactory.getLogger(PageObjectModel.class);
    private static WebDriver driver = InitializeDriver.getDriver();
    private static String host = LoadDefaultConfig.getHost();
    private static HashMap<String, Object> params = new HashMap<>();


    public HashMap<String, String> basis = new HashMap<String, String>();
    public HashMap<String, PageObjectMethod> methods = new HashMap<String, PageObjectMethod>();
    public HashMap<String, PageObjectElement> elements = new HashMap<String, PageObjectElement>();
    public HashMap<String, PageObjectAssert> asserts = new HashMap<String, PageObjectAssert>();


    /**
     * 加工普通测试类的yaml地址，默认该yaml地址和测试类同一个路径
     * 且yaml文件名称为该测试类的小写
     *
     * @return
     */
    private static String transClasspathToYamlpath(Class clazz) {
        return "src/test/java/" + clazz.getCanonicalName()
                .replace(".", "/")
                .toLowerCase()
                + ".yaml";
    }


    /**
     * 根据传入class，解析同名yaml配置
     */
    public static void parseSteps(Class frontTestClazz) {
        String path = transClasspathToYamlpath(frontTestClazz);
        log.info(" parse the path : " + path);
        PageObjectModel model = ReadYAML.getYamlConfig(path, PageObjectModel.class);
        log.info("载入yaml中写的执行步骤");
        String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        log.info("载入method :" + method);
        parseStepsFromYaml(driver, model.methods.get(method));
    }

    @Deprecated
    private static String parseBasis(String key, String path) {
        PageObjectModel model = ReadYAML.getYamlConfig(path, PageObjectModel.class);
        log.info("读取basis");
        return model.basis.get(key);
    }

    /**
     * 判断yaml配置中是否有需要替换的参数，替换参数以$符号标记，该方法不够完善
     *
     * @param str
     * @return
     */
    private static Boolean needTransParams(String str) {
        return str.contains("$");
    }

    /**
     * 对str做循环的判断，是否包含hashMap中的$key，如果包含，就将$key替换为value
     *
     * @param str
     * @return
     */
    private static String transParams(String str) {
        getParams().forEach(
                (k, v) ->
                {
                    log.info("key :"+k+",value: "+v);
                    switch (k) {
                        case "userId":
                            str.replace("$userId", v.toString());
                        case "corpId":
                            str.replace("$coprId", v.toString());
                        case "sendText":
                            str.replace("$sendText", v.toString());
                    }
                }
        );
        return str;
    }
    //todo 优化代码结构,对arraylist内部的HashMap做扫描和str替换

    /**
     * 解析step，将yaml中的配置转化为操作
     *
     * @param steps
     */
    private static void parseStepsFromYaml(WebDriver driver, PageObjectMethod steps) {
        steps.getStep().forEach(step -> {
            WebElement element = null;
            step.forEach((k,v)->{
                 WebElement element1 = null;
                switch(k){
                    case "url":
                        driver.get(v);
                        driver.manage().window().maximize();
                    case "id":
                        element1 = InitializeDriver.findElement(driver, By.id(v));
                    case "xpath":
                        element1 = InitializeDriver.findElement(driver, By.xpath(v));
                    case "css":
                        element1 = InitializeDriver.findElement(driver, By.cssSelector(v));
                    case "linkText":
                        element1 = InitializeDriver.findElement(driver, By.linkText(v));
                }
            });
            String url = step.get("url");
            String id = step.get("id");
            String xpath = step.get("xpath");
            String css = step.get("css");
            String linkText = step.get("linkText");
            String aid = step.get("aid");
            String send = step.get("send");
            log.info("aid is " + aid);
            if (url != null) {
                log.info(needTransParams(url).toString());
                if (needTransParams(url)) {

                    url = transParams(url);
                }
                log.info("访问链接：" + host + url);
                driver.get(host + url);
                log.info("最大化窗口");
                driver.manage().window().maximize();
            } else if (id != null) {
                log.info("按照id搜索元素，id=" + id);
                element = InitializeDriver.findElement(driver, By.id(id));
            } else if (xpath != null) {
                log.info("按照xpath搜索元素，xpath=" + xpath);
                element = InitializeDriver.findElement(driver, By.xpath(xpath));
            } else if (css != null) {
                log.info("按照css搜索元素，css=" + css);
                element = InitializeDriver.findElement(driver, By.cssSelector(css));
            } else if (linkText != null) {
                log.info("按照linktext搜索元素，linktext=" + linkText);
                element = InitializeDriver.findElement(driver, By.linkText(linkText));
            }
            if (aid != null) {
                if (aid.equals("click")) {
                    log.info("点击元素:" + element.getText());
                    InitializeDriver.click(driver, element);
                } else if (aid.equals("hide")) {
                    log.info("隐藏元素");
                    InitializeDriver.hideElement(driver, element);
                } else if (aid.equals("sendkeys")) {
                    log.info("键入输入值");
                    InitializeDriver.sendKeys(driver, element, send);
                }
            }
        });
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public static HashMap<String, Object> getParams() {
        return params;
    }
}
