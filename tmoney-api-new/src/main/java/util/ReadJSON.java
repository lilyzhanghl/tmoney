package util;

import com.github.mustachejava.DeferringMustacheFactory;
import com.github.mustachejava.Mustache;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;

/**
 * @ClassName: ReadJson
 * @Description: 读取json文件
 * @Author: zhzh.yin
 * @Date: 2020-05-13 16:58
 * @Verion: 1.0
 */
public class ReadJSON {
   @Test
    public void getJSON(String jsonName) throws IOException {
        DeferringMustacheFactory mf = new DeferringMustacheFactory();
        HashMap<String ,String >map = new HashMap<>();
        map.put("paperId","17727441126");
        Mustache mustache = mf.compile("viewPaper.json");
        Writer writer = new OutputStreamWriter(System.out);
        mustache.execute(writer,map);
        writer.flush();
    }



}
