package util;

import com.github.mustachejava.DeferringMustacheFactory;
import com.github.mustachejava.Mustache;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.PageObjectModel;

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
    private static final Logger log = LoggerFactory.getLogger(JSONTemplate.class);

    public static String template(String jsonPath, HashMap map) {
        Writer writer = new StringWriter();
        DeferringMustacheFactory mf = new DeferringMustacheFactory();
        log.info("开始加工json文件，jsonPath is :" + jsonPath);
        Mustache mustache = mf.compile(jsonPath);
        try {
            log.info("填充map的数据：" + map.keySet());
            mustache.execute(writer, map)
                    .flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public static String template(String jsonPath) {
        Writer writer = new StringWriter();
        DeferringMustacheFactory mf = new DeferringMustacheFactory();
        Mustache mustache = mf.compile(jsonPath);
        try {
            mustache.execute(writer, new JSONTemplate())
                    .flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }



}
