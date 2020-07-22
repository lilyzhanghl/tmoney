package api.framework;

import auto.framework.BasePO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LoginTest {
    private static BasePO basePO;
    @ParameterizedTest
    @MethodSource("baseYamlProvider")
    void testBaseYaml(int target,List<ApiContent> list,int code) {
        assertAll(
                ()->assertTrue(ApiPO.parseApi(list.get(target)).path("ret").equals(code))
        );
    }

    static Stream<Arguments> baseYamlProvider() {
        return Stream.of(
                arguments(0,basePO.load("src/test/resources/apiyaml/base.yaml").getContents(),0),
                arguments(1,basePO.load("src/test/resources/apiyaml/base.yaml").getContents(),0),
                arguments(2,basePO.load("src/test/resources/apiyaml/base.yaml").getContents(),1000002)

        );
    }

}
