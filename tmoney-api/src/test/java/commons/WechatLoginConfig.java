package commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName: Environment
 * @Description: wechatloginconfig
 * @Author: zhzh.yin
 * @Date: 2020-04-24 14:40
 * @Verion: 1.0
 */
public class WechatLoginConfig {
    private String miniAuth;
    private String userId;
    private String corpId;
    private static WechatLoginConfig config;
    private WechatLoginConfig(){}
    public String getManageAuth() {
        return miniAuth;
    }
    public String getUserId() {
        return userId;
    }
    public String getCorpId() {
        return corpId;
    }


    private static void readAndLoadConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        File yaml = new File("src/test/resources/wechatlogin.yaml");
        config = mapper.readValue(
                yaml, WechatLoginConfig.class
        );
    }


    public static WechatLoginConfig getInstance() {
        try {
            if (null == config) {
                readAndLoadConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }


}
