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
/**
 * tmoney
 * 2020/7/25 18:57
 * 手动传入的数据类型
 *
 * @author zhzh.yin
 **/
public class ApiList {
    public String name;
    public String describle;
    public HashMap<String, Api> api;


    public Api get(String apiName){
        return api.get(apiName);
    }
    
    public Response runWithoutConfig(String apiName){
        if (api.get(apiName) != null) {
            return api.get(apiName).run();
        }
        return null;
    }
    public static ApiList load(String yamlName){
        String yamlPath ="src/test/resources/api/"
                +yamlName
                + ".yaml";
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            ApiList model = objectMapper.readValue(new File(yamlPath), ApiList.class);
            if (model.getApi().keySet().size() > 0) {
                for (String s : model.getApi().keySet()) {
                    String string = yamlPath.replace(yamlPath.split("/")[yamlPath.split("/").length - 1], "");
                    log.error("jsonFilePath is :" + string);
                    Api api = model.getApi().get(s);
                    log.info("当前api是：" + api);
                    if (api != null) {
                        api.setJsonFilePath(string);
                    }
                }
            }
            return model;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("读取apiList.yaml失败,yamlName不在对应的yamlPath,yamlName:"+yamlName
            +", yamlPath is "+yamlPath);
        }
        return null;
    }
    public static ApiList load(Class clazz) {
        String yamlPath ="src/test/java/" + clazz.getCanonicalName()
                .replace(".", "/")
                .toLowerCase()
                + ".yaml";
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            ApiList model = objectMapper.readValue(new File(yamlPath), ApiList.class);
            if (model.getApi().keySet().size() > 0) {
                for (String s : model.getApi().keySet()) {
                    String string = yamlPath.replace(yamlPath.split("/")[yamlPath.split("/").length - 1], "");
                    log.error("jsonFilePath is :" + string);
                    Api api = model.getApi().get(s);
                    log.info("当前api是：" + api);
                    if (api != null) {
                        api.setJsonFilePath(string);
                    }
                }
            }
            return model;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("yaml转换失败");
        }
        log.error("yaml转换失败");
        return null;
    }

    /**
     * 废弃
     * @param yamlPath
     * @return
     */
    private static ApiList load1(String yamlPath) {

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            ApiList model = objectMapper.readValue(new File(yamlPath), ApiList.class);
            if (model.getApi().keySet().size() > 0) {
                for (String s : model.getApi().keySet()) {
                    String string = yamlPath.replace(yamlPath.split("/")[yamlPath.split("/").length - 1], "");
                    log.error("jsonFilePath is :" + string);
                    Api api = model.getApi().get(s);
                    log.info("当前api是：" + api);
                    if (api != null) {
                        api.setJsonFilePath(string);
                    }
                }
            }
            return model;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("yaml转换失败");
        }
        log.error("yaml转换失败");
        return null;
    }
}
