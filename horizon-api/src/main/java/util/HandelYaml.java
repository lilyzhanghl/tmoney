package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import horizon.api.base.Auth;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName: ReadYAML
 * @Description: read yaml
 * @Author: zhzh.yin
 * @Date: 2020-04-23 21:43
 * @Verion: 1.0
 */
@Slf4j
public class HandelYaml<T> {
    public static <T> T getYamlConfig(String filePath, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        try {
            T t =mapper.readValue(
                    new File(filePath),
                    clazz);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //todo 优化硬编码
    public static Map readFromAuth(){
        return getYamlConfig("src/main/java/horizon/api/base/auth.yaml",Auth.class).getCookies();
    }
    public static void writeToAuth(Map map )  {
        Yaml yaml = new Yaml();
        File file = new File("src/main/java/horizon/api" +
                "/base/auth.yaml");
        Auth auth = new Auth(map);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(yaml.dump(auth));
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
