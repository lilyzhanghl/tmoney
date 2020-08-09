package api.dto;

import api.item.ParamType;
import io.restassured.response.Response;
import lombok.Data;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.lang.reflect.Method;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static util.DefaultConfig.getStrFromDefaultConfig;

@Data
public class TestCaseDTO {
    public String expect;
    public String matcher;
    public HashMap<ParamType,String> assertParam;

    public boolean result(Response response){
        boolean flag = false;
        String expectString= getStrFromDefaultConfig(expect);
        String actualString ="";
        for(ParamType type :assertParam.keySet()){
            if(type.equals(ParamType.PATH)){
                actualString = response.path(assertParam.get("path"));
            }
        }

        if(matcher.equals("equals")){
            return actualString.equals(expectString);
        }

        return flag;
    }

}
