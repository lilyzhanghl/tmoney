package testdemo.json;

import api.framework.ApiModel;
import org.junit.Test;
import util.JsonTemplate;

public class TestJson {
    @Test
    public void test() {
        String a = "src/test/resources/miniapi/paper/paper.yaml";
//        String b = "src/test/resources/miniapi/paper/viewPaper.json";
//        System.out.println(ApiModel.load(a).toString());
//        System.out.println(JsonTemplate.template(b).toString());
        String d = "viewPaper";
        String[] c = a.split("/");
        System.out.println(d = a.replace(a.split("/")[a.split("/").length-1], d)+".json");
        String path = a.replace(a.split("/")[a.split("/").length-1],"");
        System.out.println(path);
    }
}
