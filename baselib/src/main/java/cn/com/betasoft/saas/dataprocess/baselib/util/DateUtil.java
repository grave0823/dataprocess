package cn.com.betasoft.saas.dataprocess.baselib.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String formatDate(Date date,String formatStr){
        SimpleDateFormat formatter = new SimpleDateFormat (formatStr);
        String dateStr = formatter.format(date);
        return dateStr;
    }
}
