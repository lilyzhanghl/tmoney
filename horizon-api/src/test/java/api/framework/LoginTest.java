package api.framework;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Execution(CONCURRENT)  //CONCURRENT表示支持多线程
@Slf4j
public class LoginTest {
    private static ApiHelper apiHelper = new ApiHelper();
    private static HashMap<String, ApiContent> apis;
    @BeforeEach
    void setUp(){
         apis = apiHelper.load("src/test/resources/apiyaml/login.yaml").getContents();
    }



    @ParameterizedTest(name = "{index}{1}")
    @MethodSource("baseYamlProvider")
    @DisplayName("登录")
    void testBaseYaml(String apiName, String assertPath, int code) {
        Response response = apiHelper.run(apis.get("getDetail"));
        assertThat("验证返回code码",response.path(assertPath).equals(code));
    }
    /**
     * 接口，接口参数->response->实际值vs预期值
     *
     * @return
     */
    static Stream<Arguments> baseYamlProvider() {
        return Stream.of(
                arguments( "login", "ret", 0),
                arguments("testLoginFailure", "ret", 1000002)
        );
    }
}
