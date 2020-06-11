package com.tengmoney.appui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalTime;
import java.util.List;

public class WorkPage extends BasePage{
    public static String COMPNAME = "深圳腾银";
    public int currentStatus(){
        int status = -1;
        //公司不对 0
        //未在当前地址 -1
        //未上班打卡 1
        //以上班打卡 2
        //早退打卡 3
        //以下班打卡 4
        //当前时间21点以后 5
        //当前时间22.30以后 6
        click(By.xpath("//*[@text='上下班打卡']"));
        //范围不对
        By compLoc = By.xpath("//*[@text='不在打卡范围内']");
        List<WebElement> wrongLoc=driver.findElements(compLoc);
        if(wrongLoc.size()>=1){
            status = -1;
            return status;
        }
        //公司不对
        By compName = By.xpath("//*[@text='"+COMPNAME +"']");
        List<WebElement> ads=driver.findElements(compName);
        if(ads.size()<=0){
            status = 0;
            return status;
        }
        //读取当前时间，再读取当前状态
        LocalTime localTime = LocalTime.now();

        return status;
    }
    public void startWork(){
            By hour = By.id("com.tencent.wework:id/bbp");
            By min = By.id("com.tencent.wework:id/bbq");
            if(Integer.parseInt(findElement(hour).getText())>=18
                    &&
                    Integer.parseInt(findElement(min).getText())>=30
            ){
                click(By.xpath("//*[@text='下班打卡']"));
            }
        }

    public void stopWork(){
        click(By.xpath("//*[@text='上下班打卡']"));
        By name = By.id("com.tencent.wework:id/ha");
        List<WebElement> ads=driver.findElements(name);

        if(ads.size()>=1){
            By hour = By.id("com.tencent.wework:id/bbp");
            By min = By.id("com.tencent.wework:id/bbq");
            if(Integer.parseInt(findElement(hour).getText())>=18
                    &&
                    Integer.parseInt(findElement(min).getText())>=30
            ){
                click(By.xpath("//*[@text='下班打卡']"));
            }
        }
    }
}
