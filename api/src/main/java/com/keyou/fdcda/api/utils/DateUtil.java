package com.keyou.fdcda.api.utils;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zzq on 2017/8/28.
 */
public class DateUtil {
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal();
    private static final Object object = new Object();
    
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public static String getDate(Date date, String parttern) {
        return dateToString(date, parttern);
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

    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        if(StringUtils.isBlank(pattern)) {
            pattern = DATETIME_FORMAT;
        }

        SimpleDateFormat dateFormat = (SimpleDateFormat)threadLocal.get();
        if(dateFormat == null) {
            Object var2 = object;
            synchronized(object) {
                dateFormat = new SimpleDateFormat(pattern);
                dateFormat.setLenient(false);
                threadLocal.set(dateFormat);
            }
        }

        dateFormat.applyPattern(pattern);
        return dateFormat;
    }
}
