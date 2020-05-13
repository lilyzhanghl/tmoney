package horizon.page.paper;

import horizon.page.base.BaseAPI;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.PageObjectModel;

import java.util.HashMap;
import java.util.Map;
import static util.APITools.combineMap;

public class Paper {
    private static final Logger log = LoggerFactory.getLogger(Paper.class);
    Map cookie = BaseAPI.getAuthCookie();
    public Response viewPaper(Map<String, String> map) {
        return PageObjectModel.parseAPI(Paper.class, combineMap(map,cookie));
    }






}
