package cn.com.betasoft.saas.dataprocess.baselib.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 将日期转换为指定格式的字符串
     * @param date
     * @param formatStr
     * @return
     */
    public static String formatDate(Date date,String formatStr){
        SimpleDateFormat formatter = new SimpleDateFormat (formatStr);
        String dateStr = formatter.format(date);
        return dateStr;
    }

    /**
     * 调整日期的天数
     * @param date  被调整的日期
     * @param days  调整的天数，正数为向后N天，负数为向前N天
     * @return
     */
    public static Date adjDataByDay(Date date,int days){
        Calendar calendar = Calendar.getInstance();
        if(date!=null){
            calendar.setTime(date);
        }
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day+days);
        return calendar.getTime();
    }
}
