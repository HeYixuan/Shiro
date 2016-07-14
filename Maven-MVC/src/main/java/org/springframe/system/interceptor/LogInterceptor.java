package org.springframe.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import eu.bitwalker.useragentutils.UserAgent;



public class LogInterceptor implements HandlerInterceptor {
	
	private Long beginTime;// 1、开始时间
	private Long endTime;// 2、结束时间
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		beginTime = System.currentTimeMillis();//计时
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		endTime = System.currentTimeMillis();
		String requestURI = request.getRequestURI();
		String uriPrefix = request.getContextPath();
		String operationCode=StringUtils.substringAfter(requestURI,uriPrefix);	//操作编码
		
		//String requestParam=(new JsonMapper()).toJson(request.getParameterMap());	//请求参数
		
		//如果是GET请求，请求编码包含create，update(添加修改页)不记录日志
		if(request.getMethod().equals("GET")){
			if(operationCode.contains("create")||operationCode.contains("update")){
				return;
			}
		}
		Long executeTime=endTime-beginTime;
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent")); 
		String os=userAgent.getOperatingSystem().getName();	//获取客户端操作系统
		String browser=userAgent.getBrowser().getName();	//获取客户端浏览器
		
		System.out.println(executeTime);
	}

}
