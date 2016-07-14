package org.springframe.utils;

import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@Transactional
public class RedisUtil {
	private static Logger logger = Logger.getLogger(RedisUtil.class);
	
	@Autowired
	private JedisPool jedisPool; //非切片连接池
	

	/**
	 * 同步获取Jedis实例
	 * 
	 * @return Jedis
	 */
	public Jedis getJedis() {
		Jedis jedis = null;
		try {
			if (jedisPool != null) {
				jedis = jedisPool.getResource();
			}
		} catch (Exception e) {
			logger.error("Get jedis error : " + e);
		} finally {
			returnResource(jedis);
		}
		return jedis;
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public void returnResource(final Jedis jedis) {

		if (jedis != null && jedisPool != null) {
			jedisPool.close();
		} else {
			logger.error("jedisPool close error!");
		}
	}
	
	/**
     * 检查是否连接成功
     * @return
     */
    public String ping(){
        return this.getJedis().ping();
    }
    
    /**
     * 通过key删除（字节）
     * @param key
     */
    public void del(byte [] key){
        this.getJedis().del(key);
    }
    /**
     * 通过key删除
     * @param key
     */
    public void del(String key){
        this.getJedis().del(key);
    }
    
	
	/**
     * 添加key value 并且设置存活时间(byte)
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte [] key,byte [] value,int liveTime){
        this.set(key, value);
        this.getJedis().expire(key, liveTime);
    }
	
	/**
     * 检查key是否已经存在
     * @param key
     * @return
     */
    public boolean exists(String key){
        return this.getJedis().exists(key);
    }
	
	
	/**添加key value (字节)(序列化)
     * @param key
     * @param value
     */
    public void set(byte [] key,byte [] value){
        this.getJedis().set(key, value);
    }
    
    /**
     * 获取redis value (byte [] )(反序列化)
     * @param key
     * @return
     */
    public byte[] get(byte [] key){
        return this.getJedis().get(key);
    }

	/**
	 * 设置 String
	 * 
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		try {
			value = StringUtils.isEmpty(value) ? "" : value;
			getJedis().set(key, value);
		} catch (Exception e) {
			logger.error("Set key error : " + e);
		}
	}

	/**
	 * 设置 过期时间
	 * 
	 * @param key
	 * @param seconds
	 *            以秒为单位
	 * @param value
	 */
	public void setString(String key, int seconds, String value) {
		try {
			value = StringUtils.isEmpty(value) ? "" : value;
			/*getJedis().setex(key, seconds, value);*/
			getJedis().expire(key, seconds);
		} catch (Exception e) {
			logger.error("Set keyex error : " + e);
		}
	}
	

	/**
	 * 获取String值
	 * 
	 * @param key
	 * @return value
	 */
	public String getString(String key) {
		if (getJedis() == null || !getJedis().exists(key)) {
			return null;
		}
		return getJedis().get(key);
	}
	
	/**
     * 清空redis 所有数据
     * @return
     */
    public String flushDB(){
        return this.getJedis().flushDB();
    }
    
    /**
     * 查看redis里有多少数据
     */
    public long dbSize(){
        return this.getJedis().dbSize();
    }
    
    /**
     * 通过正则匹配keys
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
        return this.getJedis().keys(pattern);
    }
}
