package auto.framework;

import api.framework.ApiPO;
import app.framework.AppPO;

public class ModelFactory {
    public static BasePO initAuto(String autoName){
        /*if(autoName=="web"||autoName=="selenium"){
            return new ApiObj();
        }*/
        if(autoName=="app"||autoName=="appium"){
            return new AppPO();
        }
        if(autoName=="api"){
            return new ApiPO();
        }
        return null;
    }
}
