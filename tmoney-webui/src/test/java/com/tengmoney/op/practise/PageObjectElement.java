package com.tengmoney.op.practise;

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
    public List<HashMap<String, String>> getElements() {
        return elements;
    }

    public void setElements(List<HashMap<String, String>> elements) {
        this.elements = elements;
    }

    public List<HashMap<String,String>> elements = new ArrayList<>();

}
