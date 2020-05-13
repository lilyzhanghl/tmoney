package util;

import horizon.page.paper.Paper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: APITools
 * @Description: 一些杂七杂八的工具
 * @Author: zhzh.yin
 * @Date: 2020-05-13 17:20
 * @Verion: 1.0
 */
public class APITools {
    private static final Logger log = LoggerFactory.getLogger(APITools.class);
    public static Map<String,String> combineMap(Map map1, Map map2){
        if(map1.size()+map2.size()<=0){
            return null;
        }else if(map1.size()<=0 ){
            return map2;
        }else if (map2.size()<=0){
            return map1;
        }
        Map<String, String> combineMap = new HashMap<>();
        log.info(map1.keySet().toString());
        log.info(map2.keySet().toString());
        combineMap.putAll(map1);
        combineMap.putAll(map2);
        return combineMap;
    }
}
