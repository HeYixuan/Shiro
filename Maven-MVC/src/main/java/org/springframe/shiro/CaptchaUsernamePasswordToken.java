package org.springframe.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;
/**
 * Shiro 表单认证，页面提交的用户名密码等信息，
 * 用 UsernamePasswordToken 类来接收，
 * 很容易想到，要接收页面验证码的输入，我们需要扩展此类：
 * @author 340819
 *
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
	
	private static final long serialVersionUID = 1L;
	
	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken(String username, char[] password, boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

}
