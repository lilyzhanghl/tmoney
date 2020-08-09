package util;

import api.dto.AppDTO;
import api.dto.CorpDTO;
import api.dto.StaffDTO;
import api.item.AppType;
import api.item.Env;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @ClassName: loadDefaultConfig
 * @Description: mini api config
 * @Author: zhzh.yin
 * @Date: 2020-04-24 18:30
 * @Verion: 1.0
 */
@Slf4j
public class DefaultConfig {
    public HashMap<String, Env> current = new HashMap<String, Env>();
    public HashMap<Env, String> host = new HashMap<>();
    public HashMap<Env, CorpDTO> corp = new HashMap<Env, CorpDTO>();
    public HashMap<Env, HashMap<AppType,AppDTO>> app = new HashMap<Env, HashMap<AppType,AppDTO>>();
    public HashMap<Env, StaffDTO> staff = new HashMap<Env, StaffDTO>();
    /**
     * todo 优化硬编码
     */
    static String srcPath = "src/main/resources/application.yaml";
    private static DefaultConfig config =HandelYaml
            .getYamlConfig(srcPath, DefaultConfig.class);
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
    public static String getStrFromDefaultConfig(String values) {
        HashMap<String, String> newParam = new HashMap<>(16);
        String[] value = values.split("\\.");
        if (value.length > 1) {
            if (value[0].equals("app")) {
                try {
                    AppType type = AppType.valueOf(value[1]);
                    AppDTO app = DefaultConfig.getApp(type);
                    //todo 反射
                    if (value[2].equals("appId")) {
                        return app.getAppId();
                    } else if (value[2].equals("agentId")) {
                        return app.getAgentId();
                    } else if (value[2].equals("componentAppid")) {
                        return app.getComponentAppid();
                    } else {
                        log.error("没有你写的元素：" + value[2]);
                    }
                } catch (Exception e) {
                    log.error("appType 必须是H5STATION,MINIPRO,H5PRODUCT等等");
                    e.printStackTrace();
                }
            } else if (value[0].equals("corp")) {
                CorpDTO corp = DefaultConfig.getCorp();
                if (value[1].equals("corpId")) {
                    return corp.getCorpId();
                } else {
                    log.error("没有你写的元素：" + value[1]);
                }
            } else if (value[0].equals("staff")) {
                StaffDTO staff = DefaultConfig.getStaff();
                if (value[1].equals("name")) {
                    return staff.getName();
                } else if (value[1].equals("userId")) {
                    return staff.getUserId();
                } else if (value[1].equals("staffId")) {
                    return staff.getStaffId();
                } else {
                    log.error("没有你写的元素：" + value[1]);
                }
            } else {
                log.error("param参数没有在默认设置中找到");
            }
        } else {
            return values;
        }
        return values;
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
    public static HashMap<String,String> getDefaultConfig(){
        HashMap<String, String> map=new HashMap<>(16);
        map.putAll(importStaffConfig());
        map.putAll(importCorpConfig());
        return map;
    }
    public static HashMap<String,String> getDefaultConfig(AppType type){
        HashMap<String, String> map=new HashMap<>(16);
        map.putAll(importStaffConfig());
        map.putAll(importCorpConfig());
        map.putAll(importAppConfig(type));
        return map;
    }
    private static HashMap<String,String> importAppConfig(AppType type) {
        HashMap<String, String> map=new HashMap<>(16);
        AppDTO app =  getApp(type);
        map.put("appId", app.getAppId());
        map.put("agentId", app.getAgentId());
        map.put("componentAppid", app.getAgentId());
        return map;
    }

    private static HashMap<String,String> importStaffConfig() {
        HashMap map = new HashMap(16);
        StaffDTO staff = getStaff();
        log.info("staff is " + staff);
        map.put("userId", staff.userId);
        return map;
    }

    private static HashMap<String,String> importCorpConfig() {
        HashMap map = new HashMap(16);
        CorpDTO corp =  getCorp();
        log.info("corp is " + corp);
        map.put("authCorpId", corp.corpId);
        map.put("currentCorpId", corp.corpId);
        map.put("corpId", corp.corpId);
        return map;
    }
}
