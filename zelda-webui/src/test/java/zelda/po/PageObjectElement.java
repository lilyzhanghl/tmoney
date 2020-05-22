package zelda.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: PageObjectElement
 * @Description:
 * @Author: zhzh.yin
 * @Date: 2020-04-27 17:26
 * @Verion: 1.0
 */
public class PageObjectElement {

    public List<HashMap<String, String>> getElement() {
        return element;
    }

    public void setElement(List<HashMap<String, String>> element) {
        this.element = element;
    }

    public List<HashMap<String,String>> element = new ArrayList<>();

}
