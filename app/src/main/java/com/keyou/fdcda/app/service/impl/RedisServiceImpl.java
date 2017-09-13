package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.utils.GsonUtil;
import com.keyou.fdcda.api.utils.driver.JedisDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by zzq on 2017-08-23.
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {
    private final static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    @Autowired
    private JedisDriver jedisDriver;

    /**
     * 设置缓存对象
     * @param key
     * @param value
     */
    public void set(String key, Object value){
        jedisDriver.set(key, value);
    }

    public void set(String key, Object value, long millionseconds){
        jedisDriver.set(key, value, millionseconds);
    }

    /**
     * 获取缓存对象
     * @param key
     * @return
     */
    public Object get(String key, Class clazz){
        return jedisDriver.get(key, clazz);
    }

    @Override
    public Boolean setnx(String key, Object value) {
        return jedisDriver.setnx(key, value);
    }

    @Override
    public Boolean setnx(String key, Object value, long millionseconds) {
        return jedisDriver.setnx(key, value, millionseconds);
    }

    /**
     * 删除缓存对象
     * @param key
     */
    public void del(String key){
        jedisDriver.del(key);
    }

    @Override
    public Boolean exists(String key) {
        return jedisDriver.exists(key);
    }

    @Override
    public Long incr(String key) {
        return jedisDriver.incr(key);
    }
}
