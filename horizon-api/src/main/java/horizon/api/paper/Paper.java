package horizon.api.paper;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import apiobject.APIObjectModel;
import util.APITools;
import util.HandelYaml;

import java.util.Map;

public class Paper  {
    private static final Logger log = LoggerFactory.getLogger(Paper.class);
    Map cookie = HandelYaml.readFromAuth();

    public Response viewPaper(Map map) {
        return APIObjectModel.parseAPI(Paper.class, APITools.combineMap(cookie, map));
    }
    public Response getDetail(Map map) {
        return APIObjectModel.parseAPI(Paper.class, cookie);
    }
}