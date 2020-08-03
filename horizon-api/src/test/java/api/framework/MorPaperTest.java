package api.framework;

import api.item.ManuData;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.hamcrest.BaseMatcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Execution(CONCURRENT)  //CONCURRENT表示支持多线程
@Slf4j
@Feature("早报")
@Owner("zhzh.yin")
public class MorPaperTest {
    ApiModel model = ApiModel.load("src/test/resources/apiyaml/paper.yaml");

    @BeforeEach
    void beforeAll() {
        LoginHelper.login();
    }

    @BeforeEach
    void setUp(){
    }

    @Test
    @DisplayName("一个简单的断言")
    @Story("detail.do接口")
    void test1() {
        model.run("getDetail")
                .then()
                .statusCode(200)
                .body("ret", equalTo(0));
//                .body("ret", hasItem(0))
//                .body("xx.xx", hasItems(1, 2));
    };

    @ParameterizedTest(name ="早报接口：{0}-{index}")
    @CsvSource({
            "getDetail,ret,0",
            "getDetail,ret,0"
    })
    @Story("csv测试detail.do接口")
    void testPaper(String apiName, String responsePath, Integer expectValue) throws InvocationTargetException, IllegalAccessException {
//        BeanUtils.copyProperties(apiContent, apis.get(apiName));
        assertTrue(model.run(apiName)
                .path(responsePath).equals(expectValue));
    }

    @ParameterizedTest(name ="早报接口：{0}-{index}")
    @MethodSource("paperYamlProvider")
    @Story("一大堆早报接口")
    void testPaperYaml(String apiName, HashMap<ManuData, HashMap<String, String>> map, String responsePath, Integer expectValue) {
        assertTrue(model.run(apiName, map)
                .path(responsePath)
                .equals(expectValue));
        /*assertAll(
                ()->assertTrue(ApiPO.parseApi(api).path("ret").equals(0))
        );*/
    }

    static Stream<Arguments> paperYamlProvider() {
        HashMap<ManuData, HashMap<String, String>> map1 = new HashMap<>();
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", "f09a04b775974f98bee9aaed8c492d24");
        map1.put(ManuData.REQUEST_PARAM, requestParam);

        HashMap<ManuData, HashMap<String, String>> map2 = new HashMap<>();
        HashMap<String,String> jsonFileName = new HashMap<>();
        jsonFileName.put("jsonFileName","viewPaper");
        map2.put(ManuData.JSON_FILE_NAME,jsonFileName);
        return Stream.of(
                arguments("getDetail", null, "ret", 0),
                arguments("getDetail2" , map1, "ret", 0),
                arguments("getDetail" , null, "ret", 0),
                arguments("getDetail" , map2, "ret", 0),
                arguments("viewPaper",null, "ret", 0),
                arguments("filter",null, "ret", 0),
                arguments("paperConfigAPI",null, "ret", 0),
                arguments("paperSaveAPI",null, "ret", 0),
                arguments("paperListAPI",null, "ret", 0)
        );
    }


    @ParameterizedTest(name = "接口-{0}-{index}")
    @MethodSource("oneApi")
    @Story("接口的另一种写法")
    void testPaper2(String apiName,HashMap map, String str, BaseMatcher matcher) {
        Object result = model.run(apiName, map).getBody().jsonPath().get(str);
        assertThat(result, matcher);
    }


    static Stream<Arguments> oneApi() {
        HashMap<ManuData, HashMap<String, String>> map1 = new HashMap<>();
        HashMap<String, String> requestParam = new HashMap<>();
        requestParam.put("id", "f09a04b775974f98bee9aaed8c492d24");
        map1.put(ManuData.REQUEST_PARAM, requestParam);

        HashMap<ManuData, HashMap<String, String>> map2 = new HashMap<>();
        HashMap<String,String> jsonFileName = new HashMap<>();
        jsonFileName.put("jsonFileName","viewPaper");
        map2.put(ManuData.JSON_FILE_NAME,jsonFileName);
        return Stream.of(
                arguments("getDetail",map1, "ret",is(0)),
                arguments("getDetail",map2, "ret",is(0))
        );

    }
}
