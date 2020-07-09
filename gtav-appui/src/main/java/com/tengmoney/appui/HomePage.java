package com.tengmoney.appui;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HomePage extends BasePage{
    public  HomePage()   {
        super("com.tencent.wework",".launch.LaunchSplashActivity");
    }





        //速度会比较慢
/*        By adsLocator=By.id("xxx");
        List<WebElement> ads=driver.findElements(adsLocator);
        if(ads.size()>=1){
            ads.get(0).click();
        }*/


    public WorkPage toWork() {
        click(By.xpath("//*[@text='其他企业']"));
        click(By.xpath("//*[@text='深圳腾银']"));
        click(By.id("com.tencent.wework:id/h9e"));
        click(By.xpath("//*[@text='工作台']"));
        click(By.xpath("//*[@text='打卡']"));
        return new WorkPage(driver);
    }

}
