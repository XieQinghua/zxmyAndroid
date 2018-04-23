package com.weishi.yiye.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/2/7
 * @Description：时间处理工具类
 * @Version:v1.0.0
 *****************************/
public class DateUtils {
    public final static String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public final static String YYYYMMDD = "yyyy-MM-dd";

    /**
     * 将java.sql.Timestamp对象转化为String字符串
     *
     * @param time      要格式的java.sql.Timestamp对象
     * @param strFormat 输出的String字符串格式的限定（如："yyyy-MM-dd HH:mm:ss"）
     * @return 表示日期的字符串
     */
    public static String dateToStr(java.sql.Timestamp time, String strFormat) {
        DateFormat df = new SimpleDateFormat(strFormat);
        String str = df.format(time);
        return str;
    }
    public static String getTimeStr(long time) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int cDay=c.get(Calendar.DATE);
        c.setTimeInMillis(time);
        int setYear = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String minuteStr = String.valueOf(minute);
        if (minute < 10) {
            minuteStr = "0" + minute;
        }
        if (cDay != day) {
            return "" + setYear + "-" + month + "-" + day + "  " + hour + ":" + minuteStr ;
        }else {
            return "今天" + hour + ":" + minuteStr ;
        }
    }
}
