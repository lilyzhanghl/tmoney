package horizon.api.base;

import org.kohsuke.rngom.parse.host.Base;
import po.APIObjectModel;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: BaseAPI
 * @Description: base api of all
 * @Author: zhzh.yin
 * @Date: 2020-04-23 21:31
 * @Verion: 1.0
 */
public class BaseAPI {
    private static BaseAPI api = new BaseAPI();
    private BaseAPI(){}
    public static BaseAPI getInstance(){
        return api;
    }
    public Map<String, String> getAuthCookie() {
        Map <String,String> map= APIObjectModel.parseParam(BaseAPI.class);
        return login(map).cookies();
    }
    public Response login( Map<String, String> map) {
        return APIObjectModel.parseAPI(BaseAPI.class, map);
    }
}
