package org.springframe.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * 获取当前用户工具类
 * 
 * @author HeYixuan
 *
 */
public class SystemUser {

	/**
	 * 获取当前用户对象
	 * 
	 * @return systemUser
	 */
	public static SystemUser getCurrentUser() {
		Session session = SecurityUtils.getSubject().getSession();
		if (null != session) {
			return (SystemUser) session.getAttribute("currentUser");
		} else {
			return null;
		}
	}

	/**
	 * 获取当前用户Session
	 * 
	 * @return Session
	 */
	public static Session getSession() {
		Session session = SecurityUtils.getSubject().getSession();
		return session;
	}
}
