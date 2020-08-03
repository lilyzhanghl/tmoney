package api.framework;/**
 * @author zhzh.yin
 * @create 2020-08-05 13:46
 */

import api.dto.AppDTO;
import api.item.AppType;
import util.LoadDefaultConfig;

import java.util.HashMap;

/**
 * 〈h5小站api〉
 * @author zhzh.yin
 * @create 2020/8/5
 */
public class H5StationApi extends  Api{
    @Override
    protected void importDefaultConfig() {
        super.importDefaultConfig();
        HashMap<String,String> configMap = this.getRequestParam();
        if(null==configMap){
            configMap = new HashMap<>();
        }
        HashMap<String,String> defaultMap = new HashMap<>();

        AppDTO app = LoadDefaultConfig.getApp(AppType.H5STATION);
        defaultMap.put("appId",app.getAppId());
        defaultMap.put("agentId",app.getAgentId());
        defaultMap.put("componentAppid",app.getAgentId());

        defaultMap.keySet().removeAll(configMap.keySet());
        configMap.putAll(defaultMap);
        this.setRequestParam(configMap);
    }
}
