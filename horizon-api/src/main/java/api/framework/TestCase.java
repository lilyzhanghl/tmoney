package api.framework;

import api.dto.AppDTO;
import api.dto.CorpDTO;
import api.dto.StaffDTO;
import api.dto.TestCaseDTO;
import api.item.AppType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import util.DefaultConfig;

import java.util.HashMap;
import java.util.List;

import static api.item.AppType.H5STATION;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.DefaultConfig.getStrFromDefaultConfig;

/**
 * 测试用例
 *
 * @author zhzh.yin
 * @create 2020/8/5
 */
@Data
@Slf4j
public class TestCase {
    public String api;
    public String action;
    public HashMap<String, String> param;
    public List<TestCaseDTO> results;

    public Boolean result(Response res) {
        final Boolean flag = true;
        assertAll(() -> {
            results.forEach(result -> {
                assertTrue(result.result(res));
            });
        });
        return flag;
    }



    private Api insertParam(Api api) {
        HashMap<String, String> newParam = new HashMap<>(16);
        if (param != null) {
            param.forEach(
                    (key, values) -> {
                        String value = getStrFromDefaultConfig(values);
                        newParam.put(key, values);

                    });
            api.importParam(newParam);
        }
        return api;
    }

    public Api initApi() {
        ApiList apiList = ApiList.load(this.api);
        Api api = apiList.get(action);
        return api = insertParam(api);
    }

    public Response run() {

        return initApi().run();
    }

    public RequestSpecification showRequest() {
        return initApi().setApiBody();
    }

}
