package com.tengmoney.client.paper.api;

import com.tengmoney.client.product.Product;
import commons.ExeConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class Paper {
    public String host= ExeConfig.getInstance().getHost();
    public String selProdListAPI=ExeConfig.getInstance().getSelProdListAPI();
    public String updateProdListAPI=ExeConfig.getInstance().getUpdateProdListAPI();
    public String viewPaperAPI=ExeConfig.getInstance().getViewPaperAPI();
    public Response selectProdList(String objid,String staffid) {
        return given()
//                .queryParam("objid", objid)
//                .queryParam("staffid", staffid)
                .when().log().all()
//                .get(host+selProdListAPI)
                .get("https://www.tengmoney.com/caizhi_webapp/financeNews/news?appId=wxc875800df639d3e6&authCorpId=ww535912ccc9fb5be5&objId=5a89d0b5fcff4962b207439241ae0108&agentId=wx8f451774e4ac42d3&currentCorpId=ww4bdfb9aea769a221&componentAppid=wx8f451774e4ac42d3&staffId=1a052627842b454aa82a317165326e92&snapshotId=853237&from=singlemessage&isappinstalled=0")
                .then().log().all()
                .body("ret", equalTo(0))
                .extract().response();
    }

    public Response updateProdList(ArrayList<Product> productlist) {
        HashMap<String,Object> relateProductList = new HashMap<String, Object>();
        relateProductList.put("relateProductList", productlist);

        return given()
                .contentType(ContentType.JSON)
                .body(relateProductList)
                .when().log().all()
                .post(host+updateProdListAPI)
                .then().log().all()
                .body("ret", equalTo(0))
                .extract().response();
    }



    public Response viewPaper(String paperid) {
        return given()
                .queryParam("id", paperid)
                .contentType(ContentType.JSON)
                .when().log().all()
                .get(host+viewPaperAPI)
                .then().log().all()
                .body("ret", equalTo(0))
                .extract().response();
    }
}
