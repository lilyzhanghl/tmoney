package api.framework;

import api.dto.TestCaseDTO;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Slf4j
public class TestCaseList {

    @ParameterizedTest(name = "接口：{0}-{index}")
    @MethodSource("apiTestCase")
    @Story("一大堆接口")
    public void test(TestCase testCase) {
        if (null == testCase.action
                || null == testCase.api
                || testCase.results.size()<=0) {
            log.error("testcase元素没写完整：需要填写action，api，result字段");
            assertTrue(false, "testcase元素没写完整：需要填写action，api，result字段");
            return;
        }
        RequestSpecification request = testCase.showRequest();
        Response response = testCase.run();

        assertTrue(testCase.result(response));
    }

    static List<TestCase> apiTestCase() {
        List<String> testcasePath = new ArrayList<>();
        testcasePath.add("src/test/resources/testcase/morningtestcase.yaml");
        List<ApiTestCaseModel> apitestcase = new ArrayList<>();
        List<TestCase> testcaseList = new ArrayList<>();
        testcasePath.forEach(
                path -> {
                    apitestcase.add(ApiTestCaseModel.load(path));
                }
        );
        apitestcase.forEach(
                apiTestCaseModel -> {
                    testcaseList.addAll(apiTestCaseModel.testCaseList);
                }
        );
        return testcaseList;
    }

}
