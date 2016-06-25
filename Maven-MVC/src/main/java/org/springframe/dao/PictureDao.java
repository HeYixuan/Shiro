package org.springframe.dao;

import org.springframe.model.Picture;

public interface PictureDao {
	
	/**
	 * 新增
	 * @param picture
	 */
	public void save(Picture picture);
}
