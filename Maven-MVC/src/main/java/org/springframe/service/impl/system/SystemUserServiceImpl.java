package org.springframe.service.impl.system;

import java.io.Serializable;

import org.springframe.dao.system.SystemRoleDao;
import org.springframe.model.system.SystemRole;
import org.springframe.service.system.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemUserServiceImpl implements SystemRoleService {
	@Autowired
	private SystemRoleDao systemRoleDao;

	public Serializable save(SystemRole role) {
		return systemRoleDao.save(role);
	}
	
}
