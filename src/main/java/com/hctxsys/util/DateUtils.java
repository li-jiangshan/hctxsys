package com.hctxsys.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    //获取时间戳字符串
    public static String getTime(String user_time,SimpleDateFormat sdf) {
        String re_time = null;
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    /**
     * 获取当前时间时间戳
     * @return
     */
    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String user_time = sdf.format(date);
        String re_time = null;
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }
    //获取日期字符串
    public static String getStrTime(String cc_time,SimpleDateFormat sdf) {
        String re_StrTime = null;
// 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     *将字符串时间转换成Timestamp
     * @param date 时间
     * @param simpleDateFormat
     * @return
     */
    public static Timestamp getTimestamp(String date,SimpleDateFormat simpleDateFormat) {
        Date parse=null;
        try {
             parse = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Timestamp(parse.getTime());
    }

}
