package org.springframe.controller;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframe.model.Picture;
import org.springframe.model.Users;
import org.springframe.service.PictureService;
import org.springframe.service.UserService;
import org.springframe.utils.FileUtils;
import org.springframe.utils.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	private static final Logger logger=Logger.getLogger(HomeController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PictureService pictureService;
	
	
	@RequestMapping("/index2")
	public String index2(){
		
		Users u = new Users();
		u.setUsername("何壹轩何");
		u.setPassword("123456");
		u.setEmail("15517551511@126.com");
		u.setCreateTime(new Date());
		u.setUpdateTime(new Date());
		u.setEnabled(1);
		
		
		Users user = new Users();
		user.setUsername("何壹轩");
		user.setPassword("123456");
		user.setEmail("15517551511@126.com");
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setEnabled(0);
		
		try {
			userService.save(u);
			userService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户保存异常", e);
		}
		
		
		return "success";
	}
	
	@RequestMapping("/login")
	public String login(){
		return "Login";
	}

	@RequestMapping(value="/upload",method= RequestMethod.POST)
	public String upload(@RequestParam MultipartFile file,HttpServletRequest request) throws Exception{
		String path = PropertiesUtils.readProperties("config.properties", "local");
		String fileName = FileUtils.FileUpload(request, file, path);
		Picture picture = new Picture();
		picture.setImgType(1);
		picture.setImgUrl(fileName);
		picture.setCreateTime(new Date());
		pictureService.save(picture);
		return "success";
	}
	
	@RequestMapping(value="/uploads",method= RequestMethod.POST)
	public String upload(@RequestParam MultipartFile[] files,HttpServletRequest request) throws Exception{
		String path = PropertiesUtils.readProperties("config.properties", "local");
		Picture picture = null;
		
		for (int i = 0; i < files.length; i++) {
			String fileName= FileUtils.FileUpload(request,files, path);
			picture = new Picture();
			picture.setImgType(1);
			picture.setImgUrl(fileName);
			picture.setCreateTime(new Date());
			pictureService.save(picture);
		}
		return "success";
	}

}
