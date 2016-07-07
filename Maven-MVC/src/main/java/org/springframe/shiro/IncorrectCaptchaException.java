package org.springframe.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码异常类
 * @author HeYixuan
 * @date 2016年07月07日 下午14:22:54
 */
public class IncorrectCaptchaException extends AuthenticationException {
	private static final long serialVersionUID = -158351126089224979L;

	public IncorrectCaptchaException() {
		super();
	}
	public IncorrectCaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectCaptchaException(String message) {
		super(message);
	}

	public IncorrectCaptchaException(Throwable cause) {
		super(cause);
	}
}
