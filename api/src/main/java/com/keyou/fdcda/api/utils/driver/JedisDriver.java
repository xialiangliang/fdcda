package com.keyou.fdcda.api.utils.driver;

import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.utils.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by zzq on 2017-08-23.
 */
public class JedisDriver implements RedisService {
    private final static Logger logger = LoggerFactory.getLogger(GsonUtil.class);
    @Autowired
    private JedisPool jedisPool;

    /**
     * 设置缓存对象
     * @param key
     * @param value
     */
    public void set(String key, Object value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String json = GsonUtil.serialize(value);
            jedis.set(key, json);
        } catch (Exception e) {
            logger.error("redis异常", e);
        } finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public void set(String key, Object value, long millionseconds){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String json = GsonUtil.serialize(value);
            jedis.psetex(key, millionseconds, json);
        } catch (Exception e) {
            logger.error("redis异常", e);
        } finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 获取缓存对象
     * @param key
     * @return
     */
    public Object get(String key, Class clazz){
        Object result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = GsonUtil.unserialize(jedis.get(key), clazz);
        } catch (Exception e) {
            logger.error("redis异常", e);
        } finally{
            if(jedis != null){
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public Boolean setnx(String key, Object value) {
        Boolean result = Boolean.FALSE;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String json = GsonUtil.serialize(value);
            result = jedis.setnx(key, json).equals(1);
        } catch (Exception e) {
            logger.error("redis异常", e);
        } finally{
            if(jedis != null){
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public Boolean setnx(String key, Object value, long millionseconds) {
        Boolean result = Boolean.FALSE;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String json = GsonUtil.serialize(value);
            Long nr = jedis.setnx(key, json);
            if (nr.equals(1)) {
                jedis.pexpire(key, millionseconds);
                result = Boolean.TRUE;
            }
        } catch (Exception e) {
            logger.error("redis异常", e);
        } finally{
            if(jedis != null){
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 删除缓存对象
     * @param key
     */
    public void del(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            logger.error("redis异常", e);
        } finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }
}
