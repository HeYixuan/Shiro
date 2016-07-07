package org.springframe.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.google.code.kaptcha.Constants;

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

		String captcha = (String) SecurityUtils.getSubject().getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

		if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
			throw new IncorrectCaptchaException("验证码错误！");
		}
	}

	/**
	 * 认证
	 */
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		CaptchaUsernamePasswordToken token = createToken(request,response);

		try {
			doCaptchaValidate(token);
			String username = token.getUsername();
			Subject currentUser = getSubject(request, response);
			try {
				// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
				// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
				// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
				logger.info("对用户[" + username + "]进行登录验证..验证开始");
				// 验证是否登录成功
				if (currentUser.isAuthenticated()) {
					currentUser.login(token);
					logger.info("对用户[" + username + "]进行登录验证..验证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
				} else {
					token.clear();
				}
			} catch (UnknownAccountException uae) {
				logger.error("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			} catch (IncorrectCredentialsException ice) {
				logger.error("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			} catch (LockedAccountException lae) {
				logger.error("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			} catch (ExcessiveAttemptsException eae) {
				logger.error("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
			} catch (AuthenticationException ae) {
				// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
				logger.error("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下", ae);
			}
			/*currentUser.login(token);
			logger.info("对用户[" + username + "]进行登录验证..验证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");*/
			return onLoginSuccess(token, currentUser, request, response);
			
		} catch (AuthenticationException ae) {
			return onLoginFailure(token, ae, request, response);
		}
		
	}

}
