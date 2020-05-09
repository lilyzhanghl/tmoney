package po;

import util.LoadDefaultConfig;
import util.ReadYAML;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static io.restassured.RestAssured.when;

/**
 * tmoney
 * 2020/5/7 20:41
 * 解析yaml用例
 *
 * @author zhzh.yin
 **/
public class PageObjectModel {
    private static final Logger log = LoggerFactory.getLogger(PageObjectModel.class);
    public HashMap<String, PageObjectAPI> apilist;
    private static String host = LoadDefaultConfig.getHost();
    private static HashMap<String, Object> params = new HashMap<>();

    public static void setParams(HashMap<String, Object> params) {
        PageObjectModel.params = params;
    }

    public static Response parseAPI(Class frontTestClazz) throws APINotFoundException {
        String path = ReadYAML.transClasspathToYamlpath(frontTestClazz);
        log.info(" parse the path : " + path);
        PageObjectModel model = ReadYAML.getYamlConfig(path, PageObjectModel.class);
        log.info("载入yaml中写的apilist");
        String methodname = Thread.currentThread().getStackTrace()[2].getMethodName();
        log.info("载入method :" + methodname);
        return  parseApiFromYaml(model.apilist.get(methodname));
    }
    public static HashMap<String, Object> getParams() {
        return params;
    }

    private static String transParams(String str) {
        if (getParams().size() <= 0) {
            log.info("没有需要进行字符串转换的参数");
            return str;
        }
        log.info("before replace ,the str is :" + str);
        for (String k : getParams().keySet()) {
            if (str.contains("$"+k)) {
                str = str.replace("$"+k, getParams().get(k).toString());
                log.info("");
            }
        }
        log.info("after replace,the string is  :" + str);
        return str;
    }

    private static Response parseApiFromYaml(PageObjectAPI apilist) throws APINotFoundException {
        if (apilist.getApi().size() <= 0) {
            log.info("没找到配置的api信息");
            throw new APINotFoundException("yaml 配置错误，未找到配置的api");
        }
        for(String apiType: apilist.getApi().keySet()){
            String apiParam = transParams(apilist.getApi().get(apiType));
            log.info("k is "+apiType+",v is "+apiParam);
            if(apiType.equals("get")){
                log.info("get方法，api is "+apiType);
                return
                        (Response) when().get(host+apiParam)
                        .then()
                                .log().all()
                        .extract();
            }else if (apiType.equals("post")){
                log.info("post方法，api is "+apiParam);
                return (Response) when().post(host+apiParam)
                        .then()
                        .log().all()
                        .extract();
            }else{
                throw new APINotFoundException("yaml配置的对应的api类型不存在");
            }
        }
        throw new APINotFoundException("理论上不会走到这里来");
    }
}
