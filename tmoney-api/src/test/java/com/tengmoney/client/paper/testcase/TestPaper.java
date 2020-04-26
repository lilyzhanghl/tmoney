package com.tengmoney.client.paper.testcase;


import com.tengmoney.client.paper.api.Paper;
import com.tengmoney.client.product.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TestPaper {
//    private String validobjid = Data4Test.getInstance().getValidPaperId();
//    private String validStuffid =Data4Test.getInstance().getValidStaffId();
//
/*

    static Paper paper = new Paper();

    @BeforeAll
    public static void beforeAll() {
        //nothing to do
    }

    @Test
    public void selectProdList() {
        paper.selectProdList("b7ff64fc7fee44869ca40c0d13f91784","88d41b05dec44f9cbbbdbcd25ea742b5")
                .then()
                .body("ret", equalTo(0))
                .statusCode(200);
    }

    @Test
    public void updateProdList() {
        Product product1 = new Product("", "", "", "");
        Product product2 = new Product("", "", "", "");
        Product product3 = new Product("", "", "", "");
        ArrayList<Product> productList = new ArrayList<Product>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        paper.updateProdList(productList)
                .then()
                .body("retdata.id.size()", equalTo(3))
                .statusCode(200);
    }

    @Test
    public void viewPaper() {
        paper.viewPaper("")
                .then()
                .body("ret",equalTo(0))
                .body("retdata.personInfo.name",notNullValue())
                .statusCode(200);
    }*/
}
