package api.framework.miniapi.login;

import api.framework.ApiModel;

public class LoginHelper {
    public static void login(){
        ApiModel.load("src/test/resources/apiyaml/base.yaml")
                .run("login");
    }
}
