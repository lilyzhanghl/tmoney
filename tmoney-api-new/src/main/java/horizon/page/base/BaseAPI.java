package horizon.page.base;
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
    public Map<String ,String >getAuthCookie()  {
        HashMap<String,String> map = PageObjectModel.parseParam(BaseAPI.class);
            return login(map).cookies();
    }
    public Response login(HashMap<String,String> map){

        try {
            return PageObjectModel.parseAPI(BaseAPI.class,map);
        } catch (APINotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Map<String, String> getAuthCookie(HashMap<String,String> map)  {
        return login(map).cookies();
    }
}
