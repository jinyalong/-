package lab2;

import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtil {
    public static String getValue(String key){
        String ret = null;
        try{
            InputStream in = PropertiesUtil.class.getResourceAsStream("config.properties");
            Properties properties = new Properties();
            properties.load(in);
            ret = properties.getProperty(key);
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
}
