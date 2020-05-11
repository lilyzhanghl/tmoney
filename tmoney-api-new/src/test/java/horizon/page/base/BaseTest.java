package horizon.page.base;

import po.APINotFoundException;
import org.junit.jupiter.api.Test;

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
    public void testLoginSuccess() throws APINotFoundException {
        api.login("mr.joker","ww8c83d949a80b562d")
        .then()
        .body("ret",equalTo(0));
    }
    @Test
    public void testLoginFailure() throws APINotFoundException {
        api.login("zhangsan","ali")
        .then()
        .body("ret",not(0));
    }
    @Test
    public void testGetAuthCookie() throws APINotFoundException {
        System.out.println(api.getAuthCookie("lisi", "baidu"));

    }
}
