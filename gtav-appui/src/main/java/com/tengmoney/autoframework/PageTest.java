package com.tengmoney.autoframework;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PageTest {
    private static BasePage basePage;

    @BeforeAll
    static void beforeAll() {
        //todo: 加载通用配置

    }

    @BeforeEach
    void beforeEach() {
        //todo: 每个用例相关
    }

    @ParameterizedTest(name = "{index} {1}")
    @MethodSource
    void classic(UIAuto uiAuto, String path) {
        basePage.run(uiAuto);
    }

    static List<Arguments> classic() {
        basePage = PageFactory.create("app");
        List<Arguments> all = new ArrayList<Arguments>();
        Arrays.asList(
                "src/test/resources/homePage.yaml",
                "src/test/resources/workPage.yaml"
        ).stream().forEach(path -> {
            UIAuto uiAuto = basePage.load(path);
            uiAuto.description = path;
            all.add(arguments(uiAuto, path));
        });
        return all;
    }
}
