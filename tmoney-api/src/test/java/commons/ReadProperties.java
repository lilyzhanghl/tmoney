package commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @ClassName: ReadProperties
 * @Description:
 * @Author: zhzh.yin
 * @Date: 2020-03-31 17:34
 * @Verion: 1.0
 */
public class ReadProperties {
//todo 直接将配置文件内容，装配到config并返回config
    private Properties pro = new Properties();
    private static ReadProperties prop;
    public static ReadProperties getInstance(){
        if(prop==null){
            prop=new ReadProperties();
        }
        return prop;
    }

    public String getConfig(String configname){
        loadProperties();
        Enumeration en = pro.propertyNames(); //得到配置文件的名字
        while (en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            if(strKey.equals(configname)){
                return pro.getProperty(strKey);
            }
        }
        throw new IllegalArgumentException("cannot find "+configname+" in properties");
    }
    public  void loadProperties() {
        try {
            InputStream inputStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("env.properties");
            pro.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
