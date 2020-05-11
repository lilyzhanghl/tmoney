package horizon.page.base;
import io.restassured.http.Cookie;
import po.APINotFoundException;
import po.PageObjectModel;
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
    public Response login(String userId, String corpId) throws APINotFoundException {
        HashMap<String,String> map = new HashMap<>(2);
        map.put("userId",userId);
        map.put("corpId",corpId);
        PageObjectModel.setParams(map);
        return PageObjectModel.parseAPI(BaseAPI.class);
    }
    public Map<String, String> getAuthCookie(String userId, String corpId) throws APINotFoundException {
        return login(userId,corpId).cookies();
    }
}
