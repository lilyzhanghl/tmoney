package commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName: ReadYAML
 * @Description: read yaml
 * @Author: zhzh.yin
 * @Date: 2020-04-23 21:43
 * @Verion: 1.0
 */
public class  ReadYAML<T> {
    //todo yaml公共改造
    private T tValue;
    private String tName;
    private String filePath;

    public ReadYAML(String filePath) throws IOException {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
    ReadYAML yaml =
            mapper.readValue(
                    new File(filePath),
                    ReadYAML.class);
    }
}
