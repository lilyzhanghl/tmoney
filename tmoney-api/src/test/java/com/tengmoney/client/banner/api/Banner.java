package com.tengmoney.client.banner.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import static io.restassured.RestAssured.given;

/**
 * @ClassName: Banner
 * @Description: banner list
 * @Author: zhzh.yin
 * @Date: 2020-04-23 16:08
 * @Verion: 1.0
 */
public class Banner {
    /*ExeConfig config = ExeConfig.getInstance();
    private String filterListapi = config.getPaperFilterListAPI();
    private String url = config.getHost();
    private String paperBannerAPI = config.getPaperFilterListAPI();

    public Response paperBannerList(

    ){
        HashMap<String ,Object>data = new HashMap<String,Object>() {
            {
                put("pageNum",1);
                put("pageSize",999);
                put("source",2);
                put("urlFilter",true);
            }
        };
        return given()
//                .cookie(cookie)
                .contentType(ContentType.JSON)
                .body(data)
//                .post(url+paperBannerAPI)
                .then()
                .log().all()
                .extract().response();
    }*/
}
