package com.tengmoney.appui;


import org.openqa.selenium.By;

public class HomePage extends AppBasePage {
    public  HomePage()   {
        super("com.tencent.wework",".launch.LaunchSplashActivity");
    }

    public WorkPage toWork() {
        click(By.xpath("//*[@text='其他企业']"));
        click(By.xpath("//*[@text='深圳腾银']"));
        if(elementIsExist(By.id("com.tencent.wework:id/h9e"))){
            click(By.id("com.tencent.wework:id/h9e"));
        }
        click(By.xpath("//*[@text='工作台']"));
        click(By.xpath("//*[@text='打卡']"));
        return new WorkPage(driver);
    }

}
