package org.springframe.dao.system;

import java.io.Serializable;

import org.springframe.model.system.SystemRole;

public interface SystemRoleDao {
	/**
	 * 角色新增
	 * @param role
	 */
	public Serializable save(SystemRole role);
}
