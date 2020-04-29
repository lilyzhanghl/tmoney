package zelda.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: PageObjectMethod
 * @Description: method
 * @Author: zhzh.yin
 * @Date: 2020-04-27 17:06
 * @Verion: 1.0
 */
public class PageObjectMethod {
    public List<HashMap<String,String>> step = new ArrayList<>();
    public List<HashMap<String, String>> getStep() {
        return step;
    }
    public void setStep(List<HashMap<String, String>> step) {
        this.step = step;
    }
}
