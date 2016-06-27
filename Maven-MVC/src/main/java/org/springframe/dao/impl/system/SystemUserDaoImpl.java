package org.springframe.dao.impl.system;

import java.io.Serializable;

import org.springframe.base.BaseDao;
import org.springframe.dao.system.SystemUserDao;
import org.springframe.model.system.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SystemUserDaoImpl implements SystemUserDao {
	@Autowired
	private BaseDao<SystemUser>  baseDao;
	
	public SystemUser loadByUsername(String username) {
		String hql = "From SystemUser where username=?";
		return baseDao.get(hql, new Object[]{username});
	}

	public Serializable save(SystemUser systemUser) {
		return baseDao.save(systemUser);
	}

}
