package lifeassistant.zk.com.mylifeassistant.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 099 on 2017/8/1.
 * 日期工具类
 */

public class DateUtil {
    /**
     * date值转换成年月日
     * @param date
     * @return
     */
    public static String getDay(Date date){
        DateFormat f_day=new SimpleDateFormat("yyyyMMdd");
        return f_day.format(date).toString();
    }


}
