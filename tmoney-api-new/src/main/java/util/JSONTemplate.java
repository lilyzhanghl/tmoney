package util;

import com.github.mustachejava.DeferringMustacheFactory;
import com.github.mustachejava.Mustache;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ReadJson
 * @Description: 读取json文件
 * @Author: zhzh.yin
 * @Date: 2020-05-13 16:58
 * @Verion: 1.0
 */
public class JSONTemplate {
   @Test
   public void test() throws IOException {
//       getJSON("viewPaper.json");
       HashMap<String,String> map = new HashMap<String,String>();
       map.put("name","xxx");
       template("viewPaper.json",map);
//       getJSON(map);
   }
public static String template(String jsonPath,HashMap map)  {
       Writer writer = new StringWriter();
    DeferringMustacheFactory mf = new DeferringMustacheFactory();
//    Mustache mustache = mf.compile(this.getClass().getResource(jsonPath).getPath());
    Mustache mustache = mf.compile(jsonPath);
    try {
        mustache.execute(writer,map)
                .flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return writer.toString();
}
    public static String template(String jsonPath)   {
        Writer writer = new StringWriter();
        DeferringMustacheFactory mf = new DeferringMustacheFactory();
        Mustache mustache = mf.compile(jsonPath);
        try {
            mustache.execute(writer,new JSONTemplate())
                    .flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }
    public void getJSON( Map<String, String>map) throws IOException {
        DeferringMustacheFactory mf = new DeferringMustacheFactory();
        Mustache mustache = mf.compile(new StringReader("{{name}}ccc"),"ex");
        mustache.execute(new PrintWriter(System.out),map)
                .flush();
    }



}
