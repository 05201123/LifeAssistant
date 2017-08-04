package lifeassistant.zk.com.mylifeassistant.utils;

/**
 * Created by 099 on 2017/8/1.
 * 数据工具类
 */

public class DataUtil {
    public static double getDoubleByString(String str) {
        double doubleNum;
        try {
            doubleNum = Double.parseDouble(str);
        } catch (Exception e) {
            doubleNum = 0;
        }
        return doubleNum;
    }
}
