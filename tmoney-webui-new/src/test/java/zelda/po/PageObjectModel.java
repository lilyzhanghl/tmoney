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
        if (getParams().size() <= 0) {
            log.info("没有需要进行字符串转换的参数");
            return str;
        }
        log.info("before replace ,the str is :" + str);
        for (String k : getParams().keySet()) {
            if (k.equals("userId")) {
                str = str.replace("$userId", getParams().get(k).toString());
                log.info("");
            } else if (k.equals("corpId")) {
                str = str.replace("$corpId", getParams().get(k).toString());
            } else if (k.equals("sendText")) {
                str = str.replace("$sendText", getParams().get(k).toString());
            }
        }
        log.info("after replace,the string is  :" + str);
        return str;
    }
    //todo 优化代码结构,对arraylist内部的HashMap做扫描和str替换

    /**
     * 解析step，将yaml中的配置转化为操作
     *
     * @param steps
     */
    private static void parseStepsFromYaml(WebDriver driver, PageObjectMethod steps) {
        if (steps.getStep().size() <= 0)
            throw new NullPointerException("step中没有执行步骤");
        steps.getStep().forEach(
                step -> {

                    step.forEach((k, v) -> {
                        if (v.equals(null)) {
                            throw new NullPointerException("step" + k.toString() + "没有对应操作");
                        }
                        WebElement element=null;
                        String sendText = "";
                        log.info("k is " + k + ",v is" + v);
                        if(k.equals("url")){
                            String url = transParams(host + v);
                            driver.get(url);
                            driver.manage().window().maximize();
                        }else if (k.equals("id")){
                            element = InitializeDriver.findElement(driver, By.id(v));
                        }else if (k.equals("xpath")){
                            element = InitializeDriver.findElement(driver, By.xpath(v));
                        }else if (k.equals("css")){
                            element = InitializeDriver.findElement(driver, By.cssSelector(v));
                            log.info("element is displayed:"+element.isDisplayed());
                        }else if(k.equals("linkText")){
                            element = InitializeDriver.findElement(driver, By.linkText(v));
                        }else if(k.equals("send")){
                            sendText = transParams(v);
                        }else {
                            log.info("当前定位符为:"+k+",该操作无对应内容");
                        }
                        //todo 使用switch后，在switch内部进行的对element对象的赋值，并没有保存到switch语句结束后
                        /*switch (k) {
                            case "url":
                                String url = transParams(host + v);
                                driver.get(url);
                                driver.manage().window().maximize();
                                break;
                            case "id":
                                element = InitializeDriver.findElement(driver, By.id(v));
                                break;
                            case "xpath":
                                element = InitializeDriver.findElement(driver, By.xpath(v));
                                break;
                            case "css":
                                element = InitializeDriver.findElement(driver, By.cssSelector(v));
                                log.info("element is displayed:"+element.isDisplayed());
                                break;
                            case "linkText":
                                element = InitializeDriver.findElement(driver, By.linkText(v));
                                break;
                            case "send":
                                sendText = transParams(v);
                                break;
                            default:
                                log.info("当前定位符为:"+k);
                                break;
                        }*/
                        if (k.equals("aid")) {
                            if (v.equals("click")) {
//                                System.out.println("element is displayed:"+element.isDisplayed());
                                InitializeDriver.click(driver, element);
                                log.info("点击元素:" + element.getText());
                            } else if (v.equals("hide")) {
                                InitializeDriver.hideElement(driver, element);
                                log.info("隐藏元素");
                            } else if (v.equals("sendkeys")) {
                                InitializeDriver.sendKeys(driver, element, sendText);
                                log.info("键入输入值");
                            }
                        }
                    });
                });
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public static HashMap<String, Object> getParams() {
        return params;
    }
}