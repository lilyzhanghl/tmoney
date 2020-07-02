package com.tengmoney.appui;

import org.openqa.selenium.By;

import java.time.LocalTime;

public class WorkPage extends BasePage {
    public static String COMPNAME = "深圳腾银";
    LocalTime localTime = LocalTime.now();
    public int getStatus() {
        int status = -1;
        //公司不对 0
        //未在当前地址 -1
        //未上班打卡 1
        //以上班打卡 2
        //早退打卡 3
        //未下班打卡 4
        // 已下班打卡 5
        //当前时间21点以后 6
        //当前时间22.30以后 7
        click(By.xpath("//*[@text='上下班打卡']"));
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
        System.out.println("currentStatus is "+currentStatus);
        if (currentStatus == 1) {
            click(By.xpath("//*[@text='上班打卡']"));
        } else if (currentStatus >= 4) {
            By error = By.xpath("//*[@text='更新下班卡']");
            if (elementIsExist(error)) {
                click(error);
            }
            By normal = By.xpath("//*[@text='下班打卡']");
            if (elementIsExist(normal)) {
                click(normal);
            }
            By update = By.id("com.tencent.wework:id/him");
            System.out.println("update is :"+elementIsExist(update));
            if (elementIsExist(update)) {
                click(update);
                click(By.xpath("//*[@text='更新下班卡']"));
            }
        }
        return this;
    }
}
