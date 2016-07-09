package org.springframe.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframe.constant.GlobalConstant;

/**
 * 验证码处理类
 * 
 * @author 何壹轩
 *
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

	private static final Logger logger = Logger.getLogger(SystemAuthorizingRealm.class);

	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

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

	/**
	 * 创建 Token
	 */
	protected CaptchaUsernamePasswordToken createToken(ServletRequest request,ServletResponse response) {

		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);

		return new CaptchaUsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha);
	}

	/**
	 * 验证码校验 doCaptchaValidate 方法中，验证码校验使用了框架 KAPTCHA 所提供的 API。
	 * 
	 * @param request
	 * @param token
	 */
	protected void doCaptchaValidate(CaptchaUsernamePasswordToken token) {
//		Constants.KAPTCHA_SESSION_KEY
		String captcha = (String) SecurityUtils.getSubject().getSession().getAttribute(GlobalConstant.KEY_CAPTCHA);

		if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
			throw new IncorrectCaptchaException("验证码错误！");
		}
	}

	/**
	 * 认证
	 */
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		CaptchaUsernamePasswordToken token = createToken(request,response);
		String username = token.getUsername();
		try {
			doCaptchaValidate(token);
			Subject currentUser = getSubject(request, response);
			currentUser.login(token);
			logger.info("对用户[" + username + "]进行登录验证..验证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
			return onLoginSuccess(token, currentUser, request, response);
			
		} catch (AuthenticationException ae) {
			logger.info("对用户[" + username + "]进行登录验证..验证失败..尝试重新登录");
			return onLoginFailure(token, ae, request, response);
		}
		
	}

}
