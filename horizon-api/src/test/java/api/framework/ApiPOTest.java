package api.framework;

import auto.framework.BasePO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ApiPOTest {
    private static BasePO basePO;
    @BeforeAll
    static void beforeAll(){
        basePO = new ApiPO();
        ApiPO.parseApi(basePO.load("src/test/resources/apiyaml/base.yaml").getContents().get(0));
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

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
