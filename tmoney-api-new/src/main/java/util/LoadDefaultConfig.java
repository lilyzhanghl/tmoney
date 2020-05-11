package util;


import java.util.HashMap;

/**
 * @ClassName: loadDefaultConfig
 * @Description: mini api config
 * @Author: zhzh.yin
 * @Date: 2020-04-24 18:30
 * @Verion: 1.0
 */
public class LoadDefaultConfig {
    public HashMap<String, String> host = new HashMap<>();
    public HashMap<String, String> current = new HashMap<String, String>();
    static String srcPath = "src/main/resources/application.yaml";
    public static String getHost() {
        LoadDefaultConfig config = ReadYAML
                .getYamlConfig(srcPath, LoadDefaultConfig.class);
        String env  =config.current.get("host");
        return config.host.get(env);
    }
}
