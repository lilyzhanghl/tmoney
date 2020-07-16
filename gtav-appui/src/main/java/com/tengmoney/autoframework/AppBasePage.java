package com.tengmoney.autoframework;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppBasePage extends BasePage {
    private final int timeOutInSecondsDefault = 60;
    AppiumDriver<MobileElement> driver;
    WebDriverWait wait;
    String packageName ;
    String activityName ;
    public AppBasePage(String packageName, String activityName) {
        this.packageName = packageName;
        this.activityName = activityName;
        startApp(this.packageName, this.activityName);

    }

    private void startApp(String packageName, String activityName) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appPackage", packageName);
        desiredCapabilities.setCapability("appActivity", activityName);
        desiredCapabilities.setCapability("noReset", true);
        desiredCapabilities.setCapability("autoGrantPermissions", true);
        desiredCapabilities.setCapability("udid", "APH0219430006864");
        desiredCapabilities.setCapability("dontStopAppOnReset", "true");
        desiredCapabilities.setCapability("skipLogcatCapture", "true");
        URL remoteUrl = null;
        try {
            remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,timeOutInSecondsDefault);

        long start = System.currentTimeMillis();
        new WebDriverWait(driver, 40)
                .until(x -> {
                    String xml = driver.getPageSource();
                    Boolean exist = xml.contains("工作台") || xml.contains("image_cancel");
                    System.out.println((System.currentTimeMillis() - start) / 1000);
                    System.out.println(exist);
                    return exist;
                });
    }

    public AppBasePage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, timeOutInSecondsDefault);
    }

    //通用元素定位与异常处理机制
    public WebElement findElement(By by) {
        //todo: 递归是更好的
        //todo: 如果定位的元素是动态变化位置

        System.out.println(by);
        try {
            return driver.findElement(by);
        } catch (Exception e) {
            handleAlert();

            return driver.findElement(by);
        }
    }

    public Boolean elementIsExist(By by) {
        List<MobileElement> wrongLoc = driver.findElements(by);
        if (wrongLoc.size() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public void click(By by) {
        //todo: 递归是更好的
        System.out.println(by);
        try {
            driver.findElement(by).click();
        } catch (Exception e) {
            handleAlert();
            driver.findElement(by).click();
        }
    }


    public List<MobileElement> findElements(By by) {
        System.out.println(by);
        return driver.findElements(by);
    }

    public void handleAlert() {
        By tips = By.id("com.xueqiu.android:id/snb_tip_text");
        List<By> alertBoxs = new ArrayList<>();
        //todo: 不需要所有的都判断是否存在
        alertBoxs.add(By.id("com.xueqiu.android:id/image_cancel"));
        alertBoxs.add(tips);
//        alertBoxs.add(By.id("com.xueqiu.android:id/md_buttonDefaultNegative"));
//        alertBoxs.add(By.xpath("dddd"));

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        alertBoxs.forEach(alert -> {
            List<MobileElement> ads = driver.findElements(alert);

            if (alert.equals(tips)) {
                System.out.println("snb_tip found");
                Dimension size = driver.manage().window().getSize();
                try {
                    if (driver.findElements(tips).size() >= 1) {
                        new TouchAction(driver).tap(PointOption.point(size.width / 2, size.height / 2)).perform();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("snb_tip clicked");
                }
            } else if (ads.size() >= 1) {
                ads.get(0).click();
            }
        });
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    private void handleAlertByPageSource() {
        //todo: xpath匹配， 标记 定位
        String xml = driver.getPageSource();
        List<String> alertBoxs = new ArrayList<>();
        alertBoxs.add("xxx");
        alertBoxs.add("yyy");

        alertBoxs.forEach(alert -> {
            if (xml.contains(alert)) {
                driver.findElement(By.id(alert)).click();
            }
        });
    }

    public void quit() {
        driver.quit();
    }

    @Override
    public void click(HashMap<String, Object> map) {
        super.click(map);
        String key= (String) map.keySet().toArray()[0];
        String value= (String) map.values().toArray()[0];

        By by = null;
        if(key.toLowerCase().equals("id")){
            by=By.id(value);
        }
        if(key.toLowerCase().equals("xpath")){
            by = By.xpath(value);
        }
        if(key.toLowerCase().equals("linkText".toLowerCase())){
            by=By.linkText(value);
        }

        if(key.toLowerCase().equals("partialLinkText".toLowerCase())){
            by=By.partialLinkText(value);
        }

        click(by);
    }

    @Override
    public void sendKeys(HashMap<String, Object> map) {
        super.sendKeys(map);

    }

    @Override
    public void action(HashMap<String, Object> map) {
        super.action(map);
        if(map.get("action").toString().toLowerCase().equals("get")){
            driver.get(map.get("url").toString());
        }else {
            System.out.println("error get");
        }
    }
}
