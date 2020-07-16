package com.tengmoney.autoframework;

import org.junit.jupiter.api.Test;

public class PageFactoryTest {
    @Test
    void homePageTest() {
        BasePage app= PageFactory.create("app");
        UIAuto uiAuto=app.load("src/test/resources/homePage.yaml");
        app.run(uiAuto);
    }

}
