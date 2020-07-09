package com.tengmoney.appui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.time.LocalTime;

public class WorkPage extends BasePage {
    public static String COMPNAME = "深圳腾银";
    LocalTime localTime = LocalTime.now();
    private final By update = By.id("com.tencent.wework:id/him");
    private final By 上班打卡 = By.xpath("//*[@text='上下班打卡']");
    private final By 更新下班卡 = By.xpath("//*[@text='更新下班卡']");
    private final By 下班打卡 = By.xpath("//*[@text='下班打卡']");

    public WorkPage(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    /**
     * 公司不对 0， 未在当前地址 -1，  未上班打卡 1， 以上班打卡 2， 早退打卡 3，未下班打卡 4
     * 已下班打卡 5，当前时间21点以后 6，当前时间22.30以后 7
     * @return
     */
    public int getStatus() {
        int status = -1;

        click(上班打卡);
        int hour = localTime.getHour();
        int min = localTime.getMinute();
        System.out.println("hour :" + hour + "min :" + min);
        if (hour <= 9 && min <= 30) {
            status = 1;
        } else if (hour >= 19) {
            status = 4;
        } else if (hour >= 21) {
            status = 6;
        } else if (hour >= 22 && min >= 30) {
            status = 7;
        }
        return status;
    }
    public String getResult(){
        return driver.findElement(By.id("com.tencent.wework:id/aoi")).getText();
    }
    public WorkPage click() {
        int currentStatus = getStatus();
        if (currentStatus == 1) {
            click(上班打卡);
        } else if (currentStatus >= 4) {
            if (elementIsExist(下班打卡)) {
                click(下班打卡);
            }
            System.out.println("update is :"+elementIsExist(更新下班卡));
            if (elementIsExist(更新下班卡)) {
                click(更新下班卡);
                click();
            }
        }
        return this;
    }
}
