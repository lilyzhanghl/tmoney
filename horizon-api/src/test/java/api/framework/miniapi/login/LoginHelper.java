package api.framework.miniapi.login;

import api.framework.ApiModel;
import api.item.AppType;

public class LoginHelper {
    private static ApiModel model = ApiModel.load("src/test/resources/miniapi/login/base.yaml");
//    @Test
    void test(){
        login(AppType.MINIPRO);
    }
    public static void login(AppType type){
        switch (type){
            case MANAGE:
                model.run("manage");
                break;
            case MARKET:
                model.run("market");
                break;
            case MINIPRO:
                model.run("minipro");
                break;
            default:
                model.run("h5");
        }
    }
}