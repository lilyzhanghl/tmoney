package api.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.restassured.response.Response;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Data
@Slf4j
public class ApiModel {
    public String name;
    public String describle;
    public HashMap<String, Api> contents;


    public Response run(String apiName) {
        return contents.get(apiName).run();
    }
    public Response run(String apiName,HashMap map) {
        return contents.get(apiName).run(map);
    }
    public static ApiModel load(String yamlPath) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            return objectMapper.readValue(new File(yamlPath), ApiModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("yaml转换失败");
        }
        log.error("yaml转换失败");
        return null;
    }
}
