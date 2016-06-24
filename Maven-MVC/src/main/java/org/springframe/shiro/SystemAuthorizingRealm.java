package org.springframe.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class SystemAuthorizingRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission("sys:manager");
		info.addStringPermission("user");
		System.out.println("开始授权");
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

		String username = token.getUsername();

		String password = new String(token.getPassword());

		/*
		 * User user=new User();
		 * 
		 * user.setLoginName(username);
		 */
		// user=userDao.get(user);

		System.out.println("===========");

		/*if (user != null) {

			if (user.getPassword().equals(password)) {

				return new SimpleAuthenticationInfo(username, password, getName());

			}
		}*/
		throw new UnauthenticatedException();
	}

	/**
	 * 保存登录名
	 */
	private void setSession(Object key, Object value) {
		Session session = getSession();
		System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
		if (null != session) {
			session.setAttribute(key, value);
		}
	}

	private Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
		} catch (InvalidSessionException e) {

		}
		return null;
	}

}
