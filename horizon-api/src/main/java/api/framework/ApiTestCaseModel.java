package api.framework;

import io.restassured.response.Response;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import util.HandelYaml;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * 测试用例模板
 *
 * @author zhzh.yin
 * @create 2020/8/5
 */
@Data
@Slf4j
public class ApiTestCaseModel {
    public String name;
    public String describle;
    public HashMap<String, String> param;
    public  List<TestCase> testCaseList;
    private List<ApiList> apiList;

    private List<ApiList> initApiList() {
        File dir = new File("");
        return null;
    }


    public static ApiTestCaseModel load(String yamlPath) {
        ApiTestCaseModel model = HandelYaml.getYamlConfig(yamlPath, ApiTestCaseModel.class);
        return model;
    }

    public void run() {
        testCaseList.stream().forEach(
                testCase -> {
                    if(null==testCase.action
                    ||null==testCase.api
                    ||testCase.results.size()<=0){
                        log.error("testcase元素没写完整：需要填写action，api，result字段");
                        return;
                    }
                    testCase.run();
                }
        );


    }

}
