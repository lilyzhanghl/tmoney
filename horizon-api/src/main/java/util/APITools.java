package util;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: APITools
 * @Description: 一些杂七杂八的工具
 * @Author: zhzh.yin
 * @Date: 2020-05-13 17:20
 * @Verion: 1.0
 */

@Slf4j
public class APITools {
    public static Map<String,String> combineMap(Map map1, Map map2){
        Map<String, String> combineMap = new HashMap<>();
        if(map1!=null && map2!=null){
            log.info(map1.keySet().toString());
            log.info(map2.keySet().toString());
            combineMap.putAll(map1);
            combineMap.putAll(map2);
            return combineMap;
        }else if(map1!=null ){
            log.info(map1.keySet().toString());
            return map1;
        }else if (map2!=null ){
            log.info(map2.keySet().toString());
            return map2;
        }else {
            log.info("map都没有值，报错了");
            return null;
        }
    }
}
