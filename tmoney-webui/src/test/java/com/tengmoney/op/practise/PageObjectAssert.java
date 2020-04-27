package com.tengmoney.op.practise;

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
    public List<HashMap<String,String>> asserts = new ArrayList<>();

    public List<HashMap<String, String>> getAsserts() {
        return asserts;
    }

    public void setAsserts(List<HashMap<String, String>> asserts) {
        this.asserts = asserts;
    }
}
