package horizon.page.base;
import po.APINotFoundException;
import po.PageObjectModel;
import io.restassured.response.Response;
import java.util.HashMap;

/**
 * @ClassName: BaseAPI
 * @Description: base api of all
 * @Author: zhzh.yin
 * @Date: 2020-04-23 21:31
 * @Verion: 1.0
 */
public class BaseAPI {
    public Response login(String userId, String corpId) throws APINotFoundException {
        PageObjectModel.setParams(new HashMap(
        ){{
            put("userId",userId);
            put("corpId",corpId);
        }});
        return PageObjectModel.parseAPI(BaseAPI.class);
    }
}
