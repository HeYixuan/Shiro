package org.springframe.system.service.impl;

import java.io.Serializable;

import org.springframe.dao.system.SystemRoleDao;
import org.springframe.model.system.SystemRole;
import org.springframe.system.service.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemRoleServiceImpl implements SystemRoleService {
	@Autowired
	private SystemRoleDao roleDao;
	
	public Serializable save(SystemRole role) {
		return roleDao.save(role);
	}

}
