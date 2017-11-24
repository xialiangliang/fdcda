package com.keyou.fdcda.api.utils;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zzq on 2017/8/28.
 */
public class DateUtil {
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();
    private static final Object object = new Object();
    
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public static String getDate(Date date, String parttern) {
        return dateToString(date, parttern);
    }
    
    public static String getDate(String pattern) {
        return getDate(new Date(), pattern);
    }

    public static String dateToString(Date date, String pattern) {
        String dateString = null;
        if(date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception var4) {
                ;
            }
        }

        return dateString;
    }
    
    public static String getCurrentTimeStamp() {
        return getDate(new Date(), "yyyyMMddHHmmss.SSS");
    }

    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        if(StringUtils.isBlank(pattern)) {
            pattern = DATETIME_FORMAT;
        }

        SimpleDateFormat dateFormat = (SimpleDateFormat)threadLocal.get();
        if(dateFormat == null) {
            synchronized(object) {
                dateFormat = new SimpleDateFormat(pattern);
                dateFormat.setLenient(false);
                threadLocal.set(dateFormat);
            }
        }

        dateFormat.applyPattern(pattern);
        return dateFormat;
    }
    
    

    /**
    * 把传入的日期字符串，转换成指定格式的日期对象
    * @param dateString 日期字符串
    * @param pattern 指定转换格式
    * @return Date  日期对象  
    */
    public static Date getDate(String dateString, String pattern) {
        SimpleDateFormat df = null;
        Date date = null;
        if (dateString != null) {
            try {
                df = new SimpleDateFormat(pattern);
                date = df.parse(dateString);
            } catch (Exception e) {
            }
        }
        return date;
    }
}
