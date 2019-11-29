package com.lizq.cache.web.commons.config;

import com.lizq.cache.commons.exception.ServiceException;
import com.lizq.cache.commons.utils.DateFormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 该类用于springboot 2.x
 */
@Slf4j
public class CustomDateFormatter implements Formatter<Date> {
    @Override
    public Date parse(String s, Locale locale) throws ParseException {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        String fmt = DateFormatUtils.judge(s);
        if (fmt == null) {
            log.error("字符串(" + s + ")转Date失败，格式不支持");
            throw new ServiceException("请输入正确的日期格式，支持yyyy-MM-dd HH:mm:ss、yyyy-MM-dd和yyyy-MM三种格式");
        }
        SimpleDateFormat format = new SimpleDateFormat(fmt);
        Date date;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            log.error("字符串(" + s + ")转Date失败", e);
            throw new ServiceException("请输入正确的日期格式");
        }
        return date;
    }

    @Override
    public String print(Date date, Locale locale) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
