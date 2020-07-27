package api.framework;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Execution(CONCURRENT)  //CONCURRENT表示支持多线程
@Slf4j
public class LoginTest {
    private static ApiModel model= ApiModel.load("src/test/resources/apiyaml/login.yaml");

    @ParameterizedTest(name = "{0}-{index}")
    @CsvSource({
                    "login, ret, 0",
                    "login, ret, 0",
                    "login, ret, 0",
                    "login, ret, 0",
            "testLoginFailure,ret,1000002",
            "testLoginFailure,ret,1000002",
            "testLoginFailure,ret,1000002",
            "testLoginFailure,ret,1000002"
            })
    @DisplayName("登录")
    void testBaseYaml(String apiName, String assertPath, int code) {
        Response response = model.run(apiName);
        assertThat("验证返回code码",response.path(assertPath).equals(code));
    }

}
