package api.framework;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.junit.jupiter.params.provider.Arguments.arguments;
@Execution(CONCURRENT)  //CONCURRENT表示支持多线程
@Slf4j
public class LoginTest {
    private static ApiPO basePO=new ApiPO();
    private static List<ApiContent> list= basePO.load("src/test/resources/apiyaml/login.yaml").getContents();
    @ParameterizedTest(name="{index}...")
    @MethodSource("baseYamlProvider")
    @DisplayName("登录")
    void testBaseYaml(ApiContent apiContent, int code) {
        assertTrue(ApiPO.parseApi(apiContent).path("ret").equals(code));
/*        assertAll(
                ()->assertTrue(response.path("ret").equals(code))
        );*/
    }

    static Stream<Arguments> baseYamlProvider() {
        return Stream.of(
                arguments(list.get(0),0),
                arguments(list.get(1),1000002)
        );
    }

}
