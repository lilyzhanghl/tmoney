package commons;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * @ClassName: ReadYAML
 * @Description: read yaml
 * @Author: zhzh.yin
 * @Date: 2020-04-23 21:43
 * @Verion: 1.0
 */
public class ReadYAML<T> {
    private T t;

    public ReadYAML(T t) {
        this.t = t;
    }

    public static <T> T getYamlConfig(Class<T> clazz, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        T t =
                mapper.readValue(
                        new File(filePath),
                        clazz);
        return t;
    }

    @Test
    public void test() throws IOException {
        WechatLogin login = getYamlConfig(WechatLogin.class, "src/test/resources/wechatlogin.yaml");
        System.out.println(login.corpId);
    }
}
