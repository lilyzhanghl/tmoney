package api.framework;

public class LoginHelper {
    public static void login(){
        ApiHelper apiHelper = new ApiHelper();
        apiHelper.run(apiHelper.load("src/test/resources/apiyaml/base.yaml").getContents().get("login"));
    }
}
