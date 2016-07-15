package org.springframe.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyJob {

	@Scheduled(cron = "0/10 * * * * ?") // 每10秒执行一次
	private static void executeInternal() {
		System.err.println("Hello World!  MyJob is executing.");
	}
}
