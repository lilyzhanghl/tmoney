package horizon.page.base;

import org.junit.jupiter.api.Test;
import po.PageObjectModel;

import java.util.HashMap;
import java.util.Map;

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
    private BaseAPI api = new BaseAPI();

    @Test
    public void testLoginSuccess()   {
        HashMap map = PageObjectModel.parseParam(BaseAPI.class);
        api.login(map)
                .then()
                .body("ret", equalTo(0));
    }

    @Test
    public void testLoginFailure()   {
        HashMap map = PageObjectModel.parseParam(BaseAPI.class);
        api.login(map)
                .then()
                .body("ret", not(0));
    }

//    @Test
    public void testGetAuthCookie()   {
        HashMap map = PageObjectModel.parseParam(BaseAPI.class);
        api.getAuthCookie(map);
    }

//    @Test
    public void test()   {
        Map map = api.getAuthCookie();
        for (Object str : map.keySet()) {
            System.out.println(str + "," + map.get(str));
        }
        ;
    }

}
