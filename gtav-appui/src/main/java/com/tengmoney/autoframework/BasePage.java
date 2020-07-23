package com.tengmoney.autoframework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class BasePage {
    public void click(HashMap<String, Object> map) {
        System.out.println(map);
//        driver.findElement(by).click
    }

    public void find() {

    }

    public void sendKeys(HashMap<String, Object> map) {
        System.out.println("sendKeys");
        System.out.println(map);
    }

    public void action(HashMap<String, Object> map) {
        System.out.println("action");
        System.out.println(map);
    }


    public void getText() {

    }

    public void run(UIAuto uiAuto) {
        uiAuto.steps.stream().forEach(m -> {
            if (m.keySet().contains("click")) {
                HashMap<String, Object> by = (HashMap<String, Object>) m.get("click");
                click(by);
            }
            if (m.containsKey("sendKeys")) {
                sendKeys(m);
            }

            if (m.containsKey("action")) {
                action(m);
            }
        });

    }

    public UIAuto load(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        UIAuto uiauto = null;
        try {
            uiauto = mapper.readValue(
//                    BasePage.class.getResourceAsStream(path),
                    new File(path),
                    UIAuto.class
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uiauto;
    }
}
