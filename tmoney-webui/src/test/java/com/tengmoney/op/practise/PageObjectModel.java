package com.tengmoney.op.practise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.tengmoney.op.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.HashMap;

/**
 * @ClassName: PageObjectModel
 * @Description: pageObject model
 * @Author: zhzh.yin
 * @Date: 2020-04-27 16:59
 * @Verion: 1.0
 */
public class PageObjectModel {
    public HashMap<String,PageObjectMethod>methods = new HashMap<>();
    public HashMap<String,PageObjectElement>elements = new HashMap<>();
    public HashMap<String,PageObjectAssert>asserts = new HashMap<>();
    public void parseSteps(String method,String path,WebDriver driver){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        PageObjectModel model = null;
        try {
            model = mapper.readValue(BasePage.class.getResourceAsStream(path), PageObjectModel.class);

        parseStepsFromYaml(model.methods.get(method),driver);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseStepsFromYaml(PageObjectMethod steps, WebDriver driver) {
        steps.getSteps().forEach(step ->{
                    WebElement element = null;
                    String url = step.get("url");
                    String id = step.get("id");
                    String xpath = step.get("xpath");
                    String css = step.get("css");
                    String linkText = step.get("linkText");
                    String aid = step.get("aid");
                    String send = step.get("send");

                    if (url != null){
                     driver.get(url);
                     driver.manage().window().maximize();
                    }else if (id != null){
                        element = driver.findElement(By.id(id));
                    }else if (xpath != null){
                        element = driver.findElement(By.xpath(xpath));
                    }else if (css != null){
                        element = driver.findElement(By.cssSelector(css));
                    }else if (linkText != null){
                        element = driver.findElement(By.linkText(linkText));
                    }else if (aid != null) {
                        if(aid.equals("click")){
                            element.click();
                        }else if(aid.equals("hide")){
                            String js = "arguments[0].style.display=\"none\";";
                            ((JavascriptExecutor) driver).executeScript(js, element);
                        }else if(aid.equals("sendkeys")){
                            element.sendKeys(send);
                        }
                    }
                    });
    }

}
