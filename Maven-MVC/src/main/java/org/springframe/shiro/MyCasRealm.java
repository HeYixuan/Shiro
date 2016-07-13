package org.springframe.shiro;

import java.util.Collection;

import javax.net.ssl.*;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframe.model.system.SystemRole;
import org.springframe.model.system.SystemUser;
import org.springframe.service.system.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义单点登录
 * @author HeYixuan
 *
 */
public class MyCasRealm extends CasRealm {

	@Autowired
	private SystemUserService systemUserService;

	/*static {
		// for localhost testing only
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			public boolean verify(String hostname, SSLSession sslSession) {
				if (hostname.equals("localhost")) {
					return true;
				}
				return false;
			}
		});
	}*/

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.fromRealm(getName()).iterator().next();
		SystemUser systemUser = systemUserService.loadByUsername(username);
		if (systemUser != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 用户的角色对应的所有权限，如果只使用角色定义访问权限
			Collection<SystemRole> roles = systemUser.getRoles();
			if (!roles.isEmpty()) {
				info.setRoles(systemUser.getRoleName());
				for (SystemRole role : roles) {
					info.setStringPermissions(role.getPermissionsName());
				}
				return info;
			}

		}
		return null;
	}

	/*public String getCasServerUrlPrefix() {
		return "http://locahost:18080/cas-server/login";
	}

	public String getCasService() {
		return "http://casclient/shiro-cas";
	}*/
}
