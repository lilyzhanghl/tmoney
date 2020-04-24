package com.tengmoney.client.banner.testcase;

import com.tengmoney.client.banner.api.Banner;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.Matchers.equalTo;

/**
 * @ClassName: TestBanner
 * @Description: test banner
 * @Author: zhzh.yin
 * @Date: 2020-04-23 16:23
 * @Verion: 1.0
 */
public class TestBanner {
    private Banner banner = new Banner() ;


    /**
     * 规则：小程序端过滤url banner
     */
    @Test
    @Description("早报关联banner")
    @ParameterizedTest
    @ValueSource(ints = {0,1,2})
    public void testPaperBanner(int n){
        //todo jdbc数据处理
        int bannerNum = n;
        banner.paperBannerList()
                .then()
                .body("ret",equalTo(0))
                .body("retdata.total",equalTo(bannerNum));

    }


}
