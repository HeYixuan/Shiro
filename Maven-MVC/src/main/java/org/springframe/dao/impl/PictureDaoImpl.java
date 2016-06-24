package org.springframe.dao.impl;

import org.springframe.base.BaseDao;
import org.springframe.dao.PictureDao;
import org.springframe.model.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PictureDaoImpl implements PictureDao {
	
	@Autowired
	private BaseDao<Picture> baseDao;
	
	public void save(Picture picture) {
		baseDao.save(picture);
	}

}
