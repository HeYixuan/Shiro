package org.springframe.dao.impl;

import org.springframe.base.BaseDao;
import org.springframe.dao.UsersDao;
import org.springframe.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDaoImpl implements UsersDao {
	
	@Autowired
	private BaseDao<Users>  baseDao;
	
	/**
	 * 新增
	 * @param user
	 */
	public void save(Users user) {
		baseDao.save(user);
	}
	
	/**
	 * 根据id查询
	 * 返回对象
	 * @param id
	 * @return
	 */
	public Users get(String id){
		return baseDao.get(Users.class, id);
	}

	
}
