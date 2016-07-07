package org.springframe.service.impl.system;

import java.io.Serializable;

import org.springframe.dao.system.SystemUserDao;
import org.springframe.model.system.SystemUser;
import org.springframe.service.system.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemUserServiceImpl implements SystemUserService {
	@Autowired
	private SystemUserDao systemUserDao;

	public SystemUser loadByUsername(String username) {
		return systemUserDao.loadByUsername(username);
	}

	public Serializable save(SystemUser systemUser) {
		return systemUserDao.save(systemUser);
	}

	
}
