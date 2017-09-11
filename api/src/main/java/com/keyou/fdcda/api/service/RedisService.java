package com.keyou.fdcda.api.service;

/**
 * Created by zzq on 2017-08-23.
 */
public interface RedisService {

    /**
     * 设置缓存，覆盖原值
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 设置缓存，覆盖原值
     * @param key
     * @param value
     * @param millionseconds 缓存时间 单位毫秒
     */
    void set(String key, Object value, long millionseconds);

    /**
     * 获取缓存
     * @param key
     * @param clazz 缓存对象类型
     * @return
     */
    Object get(String key, Class clazz);

    /**
     * 设置缓存，已有值则设置失败返回false，成功返回true
     * @param key
     * @param value
     */
    Boolean setnx(String key, Object value);

    /**
     * 设置缓存，已有值则设置失败返回false，成功返回true
     * @param key
     * @param value
     * @param millionseconds 缓存时间 单位毫秒
     */
    Boolean setnx(String key, Object value, long millionseconds);

    /**
     * 删除缓存
     * @param key
     */
    void del(String key);

    /**
     * 查看是否存在key
     * @param key
     * @return
     */
    Boolean exists(String key);
}
