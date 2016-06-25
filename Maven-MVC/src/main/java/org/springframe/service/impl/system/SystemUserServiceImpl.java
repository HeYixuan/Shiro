package org.springframe.service.impl.system;

import org.springframe.dao.system.SystemUserDao;
import org.springframe.model.system.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemUserServiceImpl implements org.springframe.service.system.SystemUserService {
	@Autowired
	private SystemUserDao systemUserDao;
	
	public SystemUser loadByUsername(String username) {
		return systemUserDao.loadByUsername(username);
	}

}
