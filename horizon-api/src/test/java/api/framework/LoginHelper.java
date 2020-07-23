package api.framework;

public class LoginHelper {
    public static void login(){
        ApiPO basePO = new ApiPO();
        ApiPO.parseApi(basePO.load("src/test/resources/apiyaml/base.yaml").getContents().get(0));
    }
}
