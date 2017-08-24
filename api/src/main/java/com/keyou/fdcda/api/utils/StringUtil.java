package com.keyou.fdcda.api.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zzq on 2017/8/24.
 */
public class StringUtil extends StringUtils {
    
    public static boolean isBlank(final CharSequence cs) {
        return StringUtils.isBlank(cs);
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return StringUtils.isNotBlank(cs);
    }
    
    public static boolean isEmpty(final CharSequence cs) {
        return StringUtils.isEmpty(cs);
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return StringUtils.isNotEmpty(cs);
    }
}
