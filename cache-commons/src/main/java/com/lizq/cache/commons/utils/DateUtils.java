package com.lizq.cache.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 计算两个日期相差的时间，格式：xx天xx小时xx分钟
     * @param start 开始时间
     * @param end 结束时间
     * @return 相差的时间
     */
    public static String dateDiffer(Date start, Date end) {
        long time = end.getTime() - start.getTime();
        long days = time/(24*60*60*1000);
        long leave1 = time%(24*60*60*1000);
        long hours = leave1/(60*60*1000);
        long leave2 = leave1%(60*60*1000);
        long minutes = leave2/(60*1000);
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append("天");
        }
        if (hours > 0) {
            sb.append(hours).append("小时");
        }
        sb.append(minutes).append("分");
        return sb.toString();
    }

    /**
     * 返回给定时间在当天的最初时刻，如2018-01-01，实际时间应为2018-01-01 00:00:00.0，即当天的最初时刻
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 返回给定时间在当天的最后时刻，如2018-01-01，实际时间应为2018-01-01 23:59:59.999，即当天的最后时刻
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static void main(String[] args) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse("2018-07-18 12:10:10");
            Date end = new Date();
            System.out.println(DateUtils.dateDiffer(start, end));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
