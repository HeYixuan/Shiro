package org.springframe.shiro;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframe.model.system.SystemUser;
import org.springframe.service.system.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class SystemAuthorizingRealm extends AuthorizingRealm {
	
	private static final Logger logger = Logger.getLogger(SystemAuthorizingRealm.class);

	@Autowired /* businessManager */
	private SystemUserService systemUserService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission("sys:manager");
		info.addStringPermission("user");
		System.out.println("开始授权");
		return info;

		/*
		 * String username = (String)
		 * principals.fromRealm(getName()).iterator().next();
		 * 
		 * if (username != null) { // 查询用户授权信息 Collection<String> pers =
		 * (Collection<String>) systemUserService.loadByUsername(username); if
		 * (pers != null && !pers.isEmpty()) { SimpleAuthorizationInfo info =
		 * new SimpleAuthorizationInfo(); for (String each : pers) //
		 * info.systemUserService( each );
		 * 
		 * return info; } }
		 * 
		 * return null;
		 */

	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		logger.info("*************获取基于用户名和密码的令牌开始***************");
		//实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的 
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		token.setRememberMe(true);
		logger.info("验证当前Subject时获取到token为:"+ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
		if (token.getUsername() != null && !"".equals(token.getUsername())) {
			SystemUser account = systemUserService.loadByUsername(token.getUsername());
			if (account != null) {
	            this.setSession("currentUser", account);  
				return new SimpleAuthenticationInfo(account.getUsername(), account.getPassword(), this.getName());
			}
		}
		return null;
	}

	/**
	 * 保存登录名
	 */
	/*private void setSession(Object key, Object value) {
		Session session = getSession();
		System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
		if (null != session) {
			session.setAttribute(key, value);
		}
	}*/
	
	/** 
	 * 保存登录名
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    private void setSession(Object key, Object value){  
        Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession();
            logger.info("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    } 

}
