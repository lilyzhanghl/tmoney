package api.framework.miniapi.station;/**
 * @author zhzh.yin
 * @create 2020-08-05 11:51
 */

import api.framework.ApiModel;
import api.framework.miniapi.login.LoginHelper;
import api.item.AppType;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * 〈小站首页接口〉
 * @author zhzh.yin
 * @create 2020/8/5
 */
@Execution(CONCURRENT)  //CONCURRENT表示支持多线程
@Slf4j
@Feature("早报")
@Owner("zhzh.yin")
public class StationPageTest {
    ApiModel model = ApiModel.load("src/test/resources/miniapi/station/stationPage.yaml");

    @BeforeAll
    static void beforeAll() {
        LoginHelper.login(AppType.H5STATION);
    }

    @Test
    @DisplayName("managerStation.do")
    @Story("小站首页-managerStation")
    void testManagerStation() {
        model.run("managerStation")
                .then()
                .statusCode(200)
                .body("ret", equalTo(0));
//                .body("ret", hasItem(0))
//                .body("xx.xx", hasItems(1, 2));
    };

}
