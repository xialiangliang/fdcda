package com.keyou.fdcda.api.utils;

/**
 * Created by zzq on 2017-08-31.
 */
public class Assert {
    public static void isTrue(boolean expr, String msg) throws Exception {
        if (expr) {
            throw new Exception(msg);
        }
    }
    
    public static void notTrue(boolean expr, String msg) throws Exception {
        if (!expr) {
            throw new Exception(msg);
        }
    }

    public static void isNull(Object expr, String msg) throws Exception {
        if (expr == null) {
            throw new Exception(msg);
        }
    }

    public static void notNull(Object expr, String msg) throws Exception {
        if (expr != null) {
            throw new Exception(msg);
        }
    }

    public static void isBlank(String expr, String msg) throws Exception {
        if (StringUtil.isBlank(expr)) {
            throw new Exception(msg);
        }
    }

    public static void notBlank(String expr, String msg) throws Exception {
        if (StringUtil.isNotBlank(expr)) {
            throw new Exception(msg);
        }
    }
}
