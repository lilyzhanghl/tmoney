package api.framework;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MorPaperTest {
    private static ApiPO basePO;
    @BeforeAll
    static void beforeAll(){
        LoginHelper.login();
    }

    @ParameterizedTest
    @MethodSource("paperYamlProvider")
    void testPaperYaml(int target,List<ApiContent> list) {
        assertAll(
                ()->assertTrue(ApiPO.parseApi(list.get(target)).path("ret").equals(0))
        );
    }

    static Stream<Arguments> paperYamlProvider() {
        return Stream.of(
                arguments(0,basePO.load("src/test/resources/apiyaml/paper.yaml").getContents()),
                arguments(1,basePO.load("src/test/resources/apiyaml/paper.yaml").getContents()),
                arguments(2,basePO.load("src/test/resources/apiyaml/paper.yaml").getContents()),
                arguments(3,basePO.load("src/test/resources/apiyaml/paper.yaml").getContents()),
                arguments(4,basePO.load("src/test/resources/apiyaml/paper.yaml").getContents()),
                arguments(5,basePO.load("src/test/resources/apiyaml/paper.yaml").getContents())
        );
    }



}
