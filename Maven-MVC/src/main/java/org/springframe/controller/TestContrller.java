package org.springframe.controller;

import javax.jms.Destination;

import org.springframe.jms.TemplateProducer;
import org.springframe.model.system.SystemUser;
import org.springframe.system.service.SystemUserService;
import org.springframe.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/in")
public class TestContrller {
	@Autowired
	private SystemUserService systemUserService;
	@Autowired  
    @Qualifier("queueDestination")  
    private Destination destination;  
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private TemplateProducer producer;
	
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
		//producer.sendMessage(destination, "买电影票");
		//redisUtil.setString("SA", "3326");
		System.err.println("--------------没有任何异常-------------");
		System.err.println("key1:"+redisUtil.getString("SA"));
		/*SystemUser user = systemUserService.loadByUsername("HeYixuan");
		System.out.println("user对象:"+user.getId());*/
	}
}
