package org.springframe.dao.impl.system;

import java.io.Serializable;

import org.springframe.base.BaseDao;
import org.springframe.dao.system.SystemRoleDao;
import org.springframe.model.system.SystemRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SystemRoleDaoImpl implements SystemRoleDao {
	@Autowired
	private BaseDao<SystemRole>  baseDao;

	public Serializable save(SystemRole role) {
		return baseDao.save(role);
	}
	
}
