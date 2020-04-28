package com.tengmoney.op.practise;

import com.tengmoney.op.page.MorPaperPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @ClassName: HomePage2
 * @Description: yaml剥离数据
 * @Author: zhzh.yin
 * @Date: 2020-04-27 18:59
 * @Verion: 1.0
 */
public class HomePage2{

    public HomePage2 loginWithCookie(String userId, String corpId) throws InterruptedException {
        PageObjectModel.parseSteps("loginWithCookie","src/test/resources/applictaion-test.yaml");
        return this;
    }
    @Test
    public void test() throws InterruptedException {
        loginWithCookie("","");
    }

//    public MorPaperPage toMorPaper() {
//        return new MorPaperPage();
//    }
}
