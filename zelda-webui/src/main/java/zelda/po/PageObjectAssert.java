package zelda.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: PageObjectAssert
 * @Description:
 * @Author: zhzh.yin
 * @Date: 2020-04-27 17:17
 * @Verion: 1.0
 */
public class PageObjectAssert {
    public List<HashMap<String,String>> result = new ArrayList<>();

    public List<HashMap<String, String>> getResult() {
        return result;
    }

    public void setResult(List<HashMap<String, String>> result) {
        this.result = result;
    }
}
