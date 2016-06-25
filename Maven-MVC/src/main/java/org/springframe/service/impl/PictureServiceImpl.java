package org.springframe.service.impl;


import org.springframe.dao.PictureDao;
import org.springframe.model.Picture;
import org.springframe.service.PictureService;
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
public class PictureServiceImpl implements PictureService {
	
	@Autowired
	private PictureDao pictureDao;
	
	/**
	 * 新增
	 * @param picture
	 */
	public void save(Picture picture) {
		pictureDao.save(picture);
	}
	
	
}
