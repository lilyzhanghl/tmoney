package horizon.api.base;

import io.qameta.allure.Description;
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

public class BaseTest {
    private  BaseAPI api =new BaseAPI();


    public   void setUp()   {
        HashMap map = APIObjectModel.parseParam(BaseAPI.class);
        api.login(map)
                .then()
                .body("ret", equalTo(0));
    }
    @Test
    @Order(2)
    @Description("登录成功")
    public  void testLoginSuccess()   {
        HashMap map = APIObjectModel.parseParam(BaseAPI.class);
        api.login(map)
                .then()
                .body("ret", equalTo(0));
    }
    @Test
    @Description("登录失败")
    public void testLoginFailure()   {
        HashMap map = APIObjectModel.parseParam(BaseAPI.class);
        api.login(map)
                .then()
                .body("ret", not(0));
    }
}
