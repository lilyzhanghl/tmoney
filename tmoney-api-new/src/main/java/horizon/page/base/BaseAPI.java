package horizon.page.base;
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
    public static Map<String ,String >getAuthCookie()  {
        HashMap<String,String> map = PageObjectModel.parseParam(BaseAPI.class);
            return login(map).cookies();
    }

    public static Response login(HashMap<String, String> map)   {
            return PageObjectModel.parseAPI(BaseAPI.class,map);
    }

    public Map<String, String> getAuthCookie(HashMap<String,String> map)   {
        return login(map).cookies();
    }
}
