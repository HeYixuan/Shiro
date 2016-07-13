package org.springframe.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframe.constant.GlobalConstant;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisUtil {
	private static Logger logger = Logger.getLogger(RedisUtil.class);
	
	private static JedisPool jedisPool = null; //非切片连接池
	
	// Redis服务器IP
	private static String SERVER = PropertiesUtils.readProperties(GlobalConstant.REDIS_PROPERTY, GlobalConstant.SERVER);
	// Redis的端口号
	private static int PORT = PropertiesUtils.readPropertiesIntVal(GlobalConstant.REDIS_PROPERTY, GlobalConstant.PORT);
	// 访问密码

	private static int MAX_ACTIVE = PropertiesUtils.readPropertiesIntVal(GlobalConstant.REDIS_PROPERTY,
			GlobalConstant.MAX_ACTIVE);
	private static int MAX_IDLE = PropertiesUtils.readPropertiesIntVal(GlobalConstant.REDIS_PROPERTY,
			GlobalConstant.MAX_IDLE);
	private static int MAX_WAIT = PropertiesUtils.readPropertiesIntVal(GlobalConstant.REDIS_PROPERTY,
			GlobalConstant.MAX_WAIT);
	private static int TIMEOUT = PropertiesUtils.readPropertiesIntVal(GlobalConstant.REDIS_PROPERTY,
			GlobalConstant.TIMEOUT);
	private static boolean BORROW = Boolean.valueOf(PropertiesUtils.readProperties(GlobalConstant.REDIS_PROPERTY, GlobalConstant.BORROW));

	/*private static ShardedJedisPool shardedJedisPool = null; //切片连接池
	private static Jedis jedis = null;//非切片额客户端连接
    private static ShardedJedis shardedJedis = null;//切片额客户端连接
*/	
	public RedisUtil() {
		initialPool();
	}
	/**
	 * 初始化Redis连接池
	 */
	private static void initialPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(20);
			config.setMaxIdle(5);
			config.setMaxWaitMillis(10001);
			config.setTestOnBorrow(true);
			jedisPool = new JedisPool(config, SERVER, PORT);
	        
		} catch (Exception e) {
			logger.error("First create JedisPool error : " + e);
		}
	}
	
	/**
	 * 在多线程环境同步初始化
	 */
	private static void poolInit() {
		if (jedisPool == null) {
			initialPool();
		}
	}

	/**
	 * 同步获取Jedis实例
	 * 
	 * @return Jedis
	 */
	public static Jedis getJedis() {
		if (jedisPool == null) {
			poolInit();
		}
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
	public static void returnResource(final Jedis jedis) {

		if (jedis != null && jedisPool != null) {
			jedis.close();
			jedisPool.close();
		} else {
			logger.error("jedisPool close error!");
		}
	}

	/**
	 * 设置 String
	 * 
	 * @param key
	 * @param value
	 */
	public static void setString(String key, String value) {
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
	public static void setString(String key, int seconds, String value) {
		try {
			value = StringUtils.isEmpty(value) ? "" : value;
			getJedis().setex(key, seconds, value);
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
	public static String getString(String key) {
		if (getJedis() == null || !getJedis().exists(key)) {
			return null;
		}
		return getJedis().get(key);
	}
	
	public static void main(String[] args) {
        RedisUtils.setString("login", "1231213");
        System.out.println("========================================");
        System.out.println(RedisUtils.getString("login"));
    }
}
