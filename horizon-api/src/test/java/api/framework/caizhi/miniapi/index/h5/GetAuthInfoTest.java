package api.framework.caizhi.miniapi.index.h5;

import api.framework.ApiModel;
import api.framework.LoginHelper;
import api.item.AppType;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.LoadDefaultConfig;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

public class GetAuthInfoTest {
    ApiModel model = ApiModel.load(this.getClass());

    @BeforeAll
    void beforeAll() {
        LoginHelper.login();
    }
    @ParameterizedTest
    @CsvSource({
            "H5STATION",
            "MINIPRO",
            "H5STATION",
            "H5PRODUCT"
    })
    @Story("小站首页-getAuthInfo")
    void testGetAuthInfo(AppType type) {
        HashMap<String, String> map = new HashMap<>(16);
        map.put("currentCorpId", LoadDefaultConfig.getCorp().getCorpId());
        map.put("agentId", LoadDefaultConfig.getApp(type).getAgentId());
        model.get("getAuthInfo")
                .importParam(map)
                .run()
                .then()
                .statusCode(200)
                .body("ret", equalTo(0))
                .body("retdata.appId",equalTo(LoadDefaultConfig.getApp(type).getAppId()))
                .body("retdata.authCorpId",equalTo(LoadDefaultConfig.getCorp().getCorpId()))
                .body("retdata.componentAppid",equalTo(LoadDefaultConfig.getApp(type).getComponentAppid()))
        ;
    }

}
