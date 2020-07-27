package api.framework;

import org.junit.Test;

public class LoginHelper {
    public static void login(){
        ApiModel.load("src/test/resources/apiyaml/base.yaml")
                .run("login");
    }
}
