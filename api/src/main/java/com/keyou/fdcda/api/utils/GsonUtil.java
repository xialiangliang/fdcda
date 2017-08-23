package com.keyou.fdcda.api.utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zzq on 2017-08-23.
 */
public class GsonUtil {

    private final static Logger logger = LoggerFactory.getLogger(GsonUtil.class);
    
    public static String serialize(Object object) {
        try {
            return Gson.class.newInstance().toJson(object);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Gson序列化失败！", e);
        }
        return null;
    }
    
    public static Object unserialize(String json, Class clazz) {
        try {
            return Gson.class.newInstance().fromJson(json, clazz);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Gson反序列化失败！JSON=[{}]", json, e);
        }
        return null;
    }
}
