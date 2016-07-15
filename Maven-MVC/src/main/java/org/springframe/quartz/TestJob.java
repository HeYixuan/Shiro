package org.springframe.quartz;

import javax.jms.Destination;

import org.springframe.jms.TemplateProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestJob{

	@Autowired
	@Qualifier("queueDestination") 
	private Destination destination;
	@Autowired
	private TemplateProducer producer;
	
	@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次  
    private void myTest(){  
          System.out.println("进入测试"); 
          producer.sendMessage(destination, "买电影票");
    }
	
	/*@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		System.out.println("中兴手机");
	}*/

}
