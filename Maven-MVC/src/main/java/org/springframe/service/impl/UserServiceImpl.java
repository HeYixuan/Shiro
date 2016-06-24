package org.springframe.service.impl;


import org.springframe.dao.UsersDao;
import org.springframe.model.Users;
import org.springframe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 *  类            名:      UserServiceImpl
 *  修 改 记 录:     // 修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:     版权所有(C)2010-2014
 *  Service用来组装业务
 *  @version      V1.0
 *  @date              2016年6月9日
 *  @author        何壹轩
 *
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UsersDao usersDao;
	
	/**
	 * 新增
	 * @param user
	 */
	public void save(Users user) {
		usersDao.save(user);
	}
	
	
	/**
	 * 根据id查询
	 * 返回对象
	 * @param id
	 * @return
	 */
	public Users get(String id){
		return usersDao.get(id);
	}
}
