package org.springframe.service.system;

import org.springframe.model.system.SystemUser;

public interface SystemUserService {
	/**
	 * 根据用户名查询返回对象
	 * @param username
	 * @return
	 */
	public SystemUser loadByUsername(String username);
}
