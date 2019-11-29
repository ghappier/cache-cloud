package com.lizq.cache.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期格式判断工具类
 */
public class DateFormatUtils {

    private static final Pattern pattern1 = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");// yyyy-MM-dd HH:mm:ss
    private static final Pattern pattern2 = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");// yyyy-MM-dd
    private static final Pattern pattern3 = Pattern.compile("^\\d{4}-\\d{2}$");// yyyy-MM
    private static final String format1 = "yyyy-MM-dd HH:mm:ss";
    private static final String format2 = "yyyy-MM-dd";
    private static final String format3 = "yyyy-MM";

    /**
     * 判断给定日期字符串的格式，如果识别不了，返回null
     *
     * @param date 日期字符串，如：2018-01-01 01:01:01
     * @return 日期的格式
     */
    public static String judge(String date) {
        Matcher m1 = pattern1.matcher(date);
        if (m1.matches()) {
            return format1;
        }
        Matcher m2 = pattern2.matcher(date);
        if (m2.matches()) {
            return format2;
        }
        Matcher m3 = pattern3.matcher(date);
        if (m3.matches()) {
            return format3;
        }
        return null;
    }
}
