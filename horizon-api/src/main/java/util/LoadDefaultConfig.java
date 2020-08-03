package util;

import api.dto.AppDTO;
import api.dto.CorpDTO;
import api.dto.StaffDTO;
import api.item.AppType;
import api.item.Env;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @ClassName: loadDefaultConfig
 * @Description: mini api config
 * @Author: zhzh.yin
 * @Date: 2020-04-24 18:30
 * @Verion: 1.0
 */
@Slf4j
public class LoadDefaultConfig {
    public HashMap<String, Env> current = new HashMap<String, Env>();
    public HashMap<Env, String> host = new HashMap<>();
    public HashMap<Env, CorpDTO> corp = new HashMap<Env, CorpDTO>();
    public HashMap<Env, HashMap<AppType,AppDTO>> app = new HashMap<Env, HashMap<AppType,AppDTO>>();
    public HashMap<Env, StaffDTO> staff = new HashMap<Env, StaffDTO>();
    /**
     * todo 优化硬编码
     */
    static String srcPath = "src/main/resources/application.yaml";
    private static LoadDefaultConfig config =HandelYaml
            .getYamlConfig(srcPath, LoadDefaultConfig.class);
    public static Env env = config.current.get("env");

    @Override
    public String toString() {
        return "LoadDefaultConfig{" +
                "current=" + current +
                ", host=" + host +
                ", corp=" + corp +
                ", app=" + app +
                ", staff=" + staff +
                '}';
    }


    public static  String getHost() {
        return config.host.get(env);
    }
    public static CorpDTO getCorp(){
        CorpDTO corp = config.corp.get(env);
        return corp;
    }
    public static AppDTO getApp(AppType appType){
        AppDTO app = config.app.get(env).get(appType);
        return app;
    }
    public static StaffDTO getStaff(){
        StaffDTO staff = config.staff.get(env);
        return staff;
    }
    @Test
    public void test(){
        System.out.println(HandelYaml.getYamlConfig("src/main/resources/application.yaml", LoadDefaultConfig.class));
        System.out.println(env);
        System.out.println(getHost());
        System.out.println(getApp(AppType.H5STATION));
        System.out.println(getCorp());
        System.out.println(getStaff());
    }
}
