package org.springframe.controller;

import org.springframe.model.system.SystemUser;
import org.springframe.service.system.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/in")
public class TestContrller {
	@Autowired
	private SystemUserService systemUserService;
	
	@RequestMapping("/save")
	public String save(){
		SystemUser systemUser = new SystemUser();
		systemUser.setFirstName("He");
		systemUser.setLastName("Yixuan");
		systemUser.setUsername("HYX15517551511");
		systemUser.setPassword("********");
		systemUser.setEmail("15517551511@126.com");
		systemUser.setEnabled(1);
		systemUserService.save(systemUser);
		return "redirect:/login.html";
	}
	
	@RequestMapping("/find")
	public void find(){
		SystemUser user = systemUserService.loadByUsername("HeYixuan");
		System.out.println("user对象:"+user.getId());
	}
}
