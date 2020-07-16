package com.tengmoney.autoframework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasePageTest {
    private static BasePage basePage;
    @BeforeAll
    static void beforeAll(){
        basePage = new BasePage();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void run() {
        UIAuto uiauto=basePage.load("/uiauto.yaml");
        basePage.run(uiauto);
    }

    @Test
    void load() throws JsonProcessingException {
        UIAuto uiauto=basePage.load("/uiauto.yaml");
        ObjectMapper mapper=new ObjectMapper();
        System.out.println(mapper.writeValueAsString(uiauto));
    }
}