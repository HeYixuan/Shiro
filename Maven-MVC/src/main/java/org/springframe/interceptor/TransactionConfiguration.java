package org.springframe.interceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class TransactionConfiguration {
	private final static Log log = LogFactory.getLog(TransactionConfiguration.class);

	/**
	 * 声明事务前置通知
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("execution (* org.springframe.service.impl.*.*(..))")
	public void beginTransaction(JoinPoint joinPoint) {
		Long begin = System.currentTimeMillis();
		System.out.println("开始事物" + joinPoint);
	}

	/**
	 * 声明事务后置通知
	 * 
	 * @throws Throwable
	 */
	@After("execution (* org.springframe.service.impl.*.*(..))")
	public void endTransaction(JoinPoint joinPoint) {
		Long end = System.currentTimeMillis();
		System.out.println("结束事务:"+ joinPoint);
	}

	/**
	 * 声明环绕通知
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution (* org.springframe.service.impl.*.*(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object o;
		if (true) {
			System.out.println("进入环绕通知...");
			o = pjp.proceed();
			System.out.println("退出环绕通知...");
		}
		return o;
	}

}
