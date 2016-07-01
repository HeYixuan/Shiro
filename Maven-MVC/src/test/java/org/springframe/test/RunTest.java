package org.springframe.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframe.model.system.SystemUser;
import org.springframe.service.system.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-common.xml","classpath:spring-mvc.xml","classpath:spring-shiro.xml"})
public class RunTest extends AbstractJUnit4SpringContextTests{
	@Autowired
	private SystemUserService systemUserService;
	
	@Test
	public void testa() {
		String username="HeYixuan";
		SystemUser systemUser = systemUserService.loadByUsername(username);
		System.out.println(systemUser.getId());
	}

}
