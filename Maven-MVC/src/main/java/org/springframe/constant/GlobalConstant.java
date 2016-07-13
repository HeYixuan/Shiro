package org.springframe.constant;

/**
 * 全局常量
 * 
 * @author Administrator
 *
 */
public class GlobalConstant {
	public static final Integer ENABLE = 0; // 启用
	public static final Integer DISABLE = 1; // 禁用

	/**
	 * 验证码
	 */
	public static final String KEY_CAPTCHA = "captcha";

	/**
	 * Redis配置文件
	 */
	public static final String REDIS_PROPERTY = "redis.properties";

	/**
	 * 服务
	 */
	public static final String SERVER = "redis.host";

	/**
	 * 端口
	 */
	public static final String PORT = "redis.port";
	public static final String PASSWORD = "redis.password";
	public static final String MAX_ACTIVE = "redis.maxActive";
	public static final String MAX_IDLE = "redis.maxIdle";
	public static final String MAX_WAIT = "redis.maxWait";
	public static final String BORROW = "redis.testOnBorrow";
	public static final String TIMEOUT = "redis.timeOut";

	/**
	 * redis过期时间,以秒为单位
	 */
	public final static int EXRP_HOUR = 60 * 60; // 一小时
	public final static int EXRP_DAY = 60 * 60 * 24; // 一天
	public final static int EXRP_MONTH = 60 * 60 * 24 * 30; // 一个月

}
