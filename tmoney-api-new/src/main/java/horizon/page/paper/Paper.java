package horizon.page.paper;

import horizon.page.base.BaseAPI;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.PageObjectModel;
import util.APITools;
import java.util.Map;

public class Paper {
    private static final Logger log = LoggerFactory.getLogger(Paper.class);
     Map cookie = BaseAPI.getInstance().getAuthCookie();

    public Response viewPaper(Map map) {
        return PageObjectModel.parseAPI(Paper.class, APITools.combineMap(cookie, map));
    }
}