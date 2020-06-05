package horizon.api.base;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import apiobject.APIObjectModel;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * @ClassName: TestLogin
 * @Description: testcase of login
 * @Author: zhzh.yin
 * @Date: 2020-04-22 20:51
 * @Verion: 1.0
 */
@Epic("登录接口")
public class BaseTest {
    private static BaseAPI api =new BaseAPI();
    public static Boolean notLogin = true;
@BeforeAll
    public  static void setUp()   {
    HashMap map = APIObjectModel.parseParam(BaseAPI.class);
        api.login(map)
                .then()
                .body("ret", equalTo(0));
        notLogin=false;
    }
    @Test
    @Order(1)
    @Story("登录成功")
    @Severity(SeverityLevel.CRITICAL)
    public  void testLoginSuccess()   {
        HashMap map = APIObjectModel.parseParam(BaseAPI.class);
        api.login(map)
                .then()
                .body("ret", equalTo(0));
    }
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("登录失败")
    public void testLoginFailure()   {
        HashMap map = APIObjectModel.parseParam(BaseAPI.class);
        api.login(map)
                .then()
                .body("ret", not(0));
    }
}
