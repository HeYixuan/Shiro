package org.springframe.dao;

import org.springframe.model.Users;

public interface UsersDao {
	/**
	 * 新增
	 * @param user
	 */
	public void save(Users user);
	
	
	/**
	 * 根据id查询
	 * 返回对象
	 * @param id
	 * @return
	 */
	public Users get(String id);
}
