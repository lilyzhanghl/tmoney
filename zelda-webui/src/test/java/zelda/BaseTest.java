package zelda;

import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zelda.page.AppLogin;

/**
 * @ClassName: BaseCase
 * @Description: baseCase
 * @Author: zhzh.yin
 * @Date: 2020-04-29 15:47
 * @Verion: 1.0
 */
public class BaseTest {
    private static AppLogin login = new AppLogin();
    static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeAll
    public static void login() {
        log.info("before all :login");
        login.loginWithCookie("mr.joker", "ww8c83d949a80b562d");
    }

    @Test
    public void testLogin() throws InterruptedException {

    }

    @AfterAll
    public static void shutdown() {
        login.getDriver().quit();
    }
}
