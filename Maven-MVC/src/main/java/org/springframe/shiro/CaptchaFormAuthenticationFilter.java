package org.springframe.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter{
	
	/*public static final String DEFAULT_CAPTCHA_PARAM = "captcha"; 
	   
	   private String captchaParam = DEFAULT_CAPTCHA_PARAM; 
	   
	   public String getCaptchaParam() { 
	      return captchaParam; 
	   } 
	   
	   public void setCaptchaParam(String captchaParam) { 
	      this.captchaParam = captchaParam; 
	   } 
	   
	   protected String getCaptcha(ServletRequest request) { 
	      return WebUtils.getCleanParam(request, getCaptchaParam()); 
	   } 
	   
	   // 创建 Token 
	   protected CaptchaUsernamePasswordToken createToken( 
	      ServletRequest request, ServletResponse response) { 
	   
	      String username = getUsername(request); 
	      String password = getPassword(request); 
	      String captcha = getCaptcha(request); 
	      boolean rememberMe = isRememberMe(request); 
	      String host = getHost(request); 
	                   
	      return new CaptchaUsernamePasswordToken( 
	         username, password, rememberMe, host,captcha); 
	   } 
	   
	   // 验证码校验
	   protected void doCaptchaValidate( HttpServletRequest request 
	      ,CaptchaUsernamePasswordToken token ){ 

	      String captcha = (String)request.getSession().getAttribute( 
	         com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY); 
	      
	      if( captcha!=null && 
	         !captcha.equalsIgnoreCase(token.getCaptcha()) ){ 
	         throw new IncorrectCaptchaException ("验证码错误！"); 
	      } 
	   } 
	    
	   // 认证
	   protected boolean executeLogin(ServletRequest request, 
	      ServletResponse response) throws Exception { 
	      CaptchaUsernamePasswordToken token = createToken(request, response); 
	       
	      try { 
	         doCaptchaValidate( (HttpServletRequest)request,token ); 
	                 
	         Subject subject = getSubject(request, response); 
	         subject.login(token); 
	                     
	         return onLoginSuccess(token, subject, request, response); 
	      } catch (AuthenticationException e) { 
	         return onLoginFailure(token, e, request, response); 
	      } 
	   } */
}
