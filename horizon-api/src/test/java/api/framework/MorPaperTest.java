package api.framework;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
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
public class MorPaperTest {
    private static ApiPO basePO=new ApiPO();
    private static List<ApiContent> list = basePO.load("src/test/resources/apiyaml/paper.yaml").getContents();
    @BeforeAll
    static void beforeAll(){
        LoginHelper.login();
    }
    @ParameterizedTest
    @MethodSource("paperYamlProvider")
    @DisplayName("早报")
    void testPaperYaml(ApiContent api) {
        assertTrue(ApiPO.parseApi(api).path("ret").equals(0));
        /*assertAll(
                ()->assertTrue(ApiPO.parseApi(api).path("ret").equals(0))
        );*/
    }

    static Stream<Arguments> paperYamlProvider() {
        return Stream.of(
                arguments(list.get(0)),
                arguments(list.get(1)),
                arguments(list.get(2)),
                arguments(list.get(3)),
                arguments(list.get(4)),
                arguments(list.get(5))
        );
    }



}
