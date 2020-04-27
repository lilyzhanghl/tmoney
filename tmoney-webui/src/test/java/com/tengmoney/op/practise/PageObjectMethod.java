package com.tengmoney.op.practise;

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
    public List<HashMap<String, String>> getSteps() {
        return steps;
    }

    public void setSteps(List<HashMap<String, String>> steps) {
        this.steps = steps;
    }

    public List<HashMap<String,String>> steps = new ArrayList<>();
}
