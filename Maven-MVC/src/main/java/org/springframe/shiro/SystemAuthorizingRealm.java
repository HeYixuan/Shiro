package org.springframe.shiro;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframe.model.system.SystemRole;
import org.springframe.model.system.SystemUser;
import org.springframe.service.system.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义realm类
 * 
 * @author Administrator
 *
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {

	private static final Logger logger = Logger.getLogger(SystemAuthorizingRealm.class);

	@Autowired
	private SystemUserService systemUserService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.fromRealm(getName()).iterator().next();
		SystemUser systemUser = systemUserService.loadByUsername(username);
		if (systemUser != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.setRoles(systemUser.getRoleName());
			// 用户的角色对应的所有权限，如果只使用角色定义访问权限
			Collection<SystemRole> roles = systemUser.getRoles();
			if (!roles.isEmpty()) {
				for (SystemRole role : roles) {
					info.setStringPermissions(role.getPermissionsName());
				}
				return info;
			}

		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission("sys:manager");
		info.addStringPermission("user");
		System.out.println("开始授权");
		return info;
	}

	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		logger.info("*************获取基于用户名和密码的令牌开始***************");
		// 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		logger.info(
				"验证当前Subject时获取到token为:" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
		if (token.getUsername() != null && !"".equals(token.getUsername())) {
			SystemUser account = systemUserService.loadByUsername(token.getUsername());

			if (account != null) {
				if (StringUtils.isNotBlank(account.getPassword()) && account.getPassword().equals(token.getPassword())) {
					this.setSession("currentUser", account);
					return new SimpleAuthenticationInfo(account.getUsername(), account.getPassword(), this.getName());
				}else{
					throw new IncorrectCredentialsException();
				}
				
			} else {
				throw new UnknownAccountException("No account found for user [" + token.getUsername() + "]");
			}
		} else {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	/**
	 * 保存登录名 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			logger.info("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

}
