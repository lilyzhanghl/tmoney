package com.tengmoney.appui;


import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App extends BasePage{
    private static App app;
    public static App getInstance(){
        if(app==null){
            app=new App();
        }
        return app;
    }

    public void start() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appPackage", "com.tencent.wework");
        desiredCapabilities.setCapability("appActivity", ".launch.LaunchSplashActivity");
        desiredCapabilities.setCapability("noReset", true);
        desiredCapabilities.setCapability("autoGrantPermissions", true);
//        desiredCapabilities.setCapability("udid", System.getenv("UDID"));
        desiredCapabilities.setCapability("udid", "APH0219430006864");


        URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");
//        URL remoteUrl = new URL("http://192.168.31.199:4444/wd/hub");


        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        long start=System.currentTimeMillis();
/*        for(int i =0;i<=3;i++){
            By allowButton = By.xpath("//*[@text='始终允许']");
            List<WebElement> ads = driver.findElements(allowButton);
            if(ads.size()>=1){
                for(WebElement element :ads){
                    element.click();
                }
            }
        }*/

        new WebDriverWait(driver, 40)
                .until(x -> {
                    String xml=driver.getPageSource();
                    Boolean exist=xml.contains("工作台") || xml.contains("image_cancel") ;
                    System.out.println((System.currentTimeMillis() - start)/1000);
                    System.out.println(exist);
                    return exist;
                });
        //速度会比较慢
/*        By adsLocator=By.id("xxx");
        List<WebElement> ads=driver.findElements(adsLocator);
        if(ads.size()>=1){
            ads.get(0).click();
        }*/
    }

    public WorkPage toWork() {
        click(By.xpath("//*[@text='其他企业']"));
        click(By.xpath("//*[@text='深圳腾银']"));
        click(By.id("com.tencent.wework:id/h9e"));
        click(By.xpath("//*[@text='工作台']"));
        click(By.xpath("//*[@text='打卡']"));

        return new WorkPage();
    }

}
