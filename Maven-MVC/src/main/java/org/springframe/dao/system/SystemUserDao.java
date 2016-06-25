package org.springframe.dao.system;

import org.springframe.model.system.SystemUser;

public interface SystemUserDao {
	/**
	 * 根据用户名查询返回对象
	 * @param username
	 * @return
	 */
	public SystemUser loadByUsername(String username);
}
