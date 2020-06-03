package horizon.api.base;

import apiobject.APIObjectModel;
import io.restassured.response.Response;
import util.HandelYaml;

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
    public BaseAPI(){
        login();
    }
    public  Map<String, String> getAuthCookie() {
        Map <String,String> map= APIObjectModel.parseParam(BaseAPI.class);
        return login().cookies();
    }
    public  Response login( ) {
        Response response =  APIObjectModel.parseAPI(BaseAPI.class, APIObjectModel.parseParam(BaseAPI.class));
        HandelYaml.writeToAuth( response.getCookies());
        return response;
    }
}
