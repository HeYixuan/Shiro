package org.springframe.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframe.constant.GlobalConstant;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisUtils {
	protected static Logger logger = Logger.getLogger(RedisUtils.class);

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

	private static JedisPool jedisPool = null; //非切片连接池
	private static ShardedJedisPool shardedJedisPool = null; //切片连接池
	private static Jedis jedis = null;//非切片额客户端连接
    private static ShardedJedis shardedJedis = null;//切片额客户端连接

	public RedisUtils() {
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
	private static synchronized void poolInit() {
		if (jedisPool == null) {
			initialPool();
		}
	}

	/**
	 * 同步获取Jedis实例
	 * 
	 * @return Jedis
	 */
	public synchronized static Jedis getJedis() {
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
			jedisPool.destroy();
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

	/********************* 追加 *********************/

	/**
     * 判断key是否在Redis存在,存在返回ture,否则返回false
     *
     * @param key
     * @return
     */
    public boolean isExists(String key) {
        ShardedJedis shardedJedis = null;
        boolean flag = false;
        try {
            shardedJedis = shardedJedisPool.getResource();
            flag = shardedJedis.exists(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return flag;
    }

    /**
     * 向list左边追加加元素
     *
     * @param visitKey
     * @param item
     */
    public void lpush(String visitKey, String item) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.lpush(visitKey, item);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
    }

    /**
     * 查询返回list中指定索引位置的值
     *
     * @param key
     * @param index
     * @return
     */
    public String lindex(String key, long index) {
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result = shardedJedis.lindex(key, index);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return result;
    }

    /**
     * 获取指定范围记录,可以做分页使用 end是-1的时候,从start位置到列表结束
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end) {
        ShardedJedis shardedJedis = null;
        List<String> list = new ArrayList<String>();
        try {
            shardedJedis = shardedJedisPool.getResource();
            list = shardedJedis.lrange(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return list;
    }

    /**
     * 删除一个key
     *
     * @param visitKey
     * @return
     */
    public long del(String visitKey) {
        ShardedJedis shardedJedis = null;
        long delNum = 0;
        try {
            shardedJedis = shardedJedisPool.getResource();
            delNum = shardedJedis.del(visitKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return delNum;
    }

    /**
     * 删除指定index索引位置的list元素
     *
     * @param visitKey
     * @param index
     * @return
     */
    public long delIndex(String visitKey, long index) {
        ShardedJedis shardedJedis = null;
        long delNum = 0;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.lset(visitKey, index, "del");// 将原有索引位置内容替换设置成需要删除的标记
            delNum = shardedJedis.lrem(visitKey, 0, "del");// 删除
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return delNum;
    }

    /**
     * 删除模糊匹配的key
     *
     * @param likeKey
     *            模糊匹配的key
     * @return 删除成功的条数
     */
    public long delKeysLike(final String likeKey) {
        ShardedJedis shardedJedis = null;
        long count = 0;
        try {
            shardedJedis = shardedJedisPool.getResource();
            Collection<Jedis> jedisC = shardedJedis.getAllShards();
            Iterator<Jedis> iter = jedisC.iterator();
            while (iter.hasNext()) {
                Jedis _jedis = iter.next();
                Set<String> keys = _jedis.keys(likeKey + "*");
                count += _jedis.del(keys.toArray(new String[keys.size()]));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return count;
    }

    /**
     * 修改替换指定key指定index索引位置的值
     *
     * @param visitKey
     * @param index
     * @param value
     */
    public String lset(String visitKey, long index, String value) {
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result = shardedJedis.lset(visitKey, index, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return result;
    }

    /**
     * 获取集合总长度
     *
     * @param visitKey
     * @return
     */
    public Long llen(String visitKey) {
        ShardedJedis shardedJedis = null;
        long len = 0;
        try {
            shardedJedis = shardedJedisPool.getResource();
            len = shardedJedis.llen(visitKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return len;
    }

    /**
     * 向Redis保存一个字符串类型的 key-value键值对,如果原先有则把原先的替换掉
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(key, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
    }

    /**
     * 根据key得到在Redis中的一个字符串
     *
     * @param key
     */
    public String get(String key) {
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result = shardedJedis.get(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return result;
    }

    /**
     * 向Redis保存 一个键值对的集合
     *
     * @param key
     *            redis储存名称
     * @param field
     *            键
     * @param value
     *            值
     */
    public void hset(String key, String field, String value) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.hset(key, field, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
    }

    /**
     * 通过redis储存名和键名查询值
     *
     * @param key
     *            redis储存名称
     * @param field
     *            键
     */
    public boolean hexists(String key, String field) {
        ShardedJedis shardedJedis = null;
        boolean flag = false;
        try {
            shardedJedis = shardedJedisPool.getResource();
            flag = shardedJedis.hexists(key, field);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return flag;
    }

    /**
     * 通过redis储存名和键名查询值获得 值
     *
     * @param key
     *            redis储存名称
     * @param field
     *            键
     * @return
     */
    public String hget(String key, String field) {
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            result = shardedJedis.hget(key, field);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return result;
    }

    public Long hdel(String key, String field) {
        ShardedJedis shardedJedis = null;
        long delNum = 0;
        try {
            shardedJedis = shardedJedisPool.getResource();
            delNum = shardedJedis.hdel(key, field);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(shardedJedis!=null){
                try {
                    shardedJedisPool.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return delNum;
    }

    public static void main(String[] args) {
        RedisUtils redisUtil = new RedisUtils();
        redisUtil.setString("login", "1231213");
        System.out.println("========================================");
        System.out.println(redisUtil.getString("login"));
    }
    

}
