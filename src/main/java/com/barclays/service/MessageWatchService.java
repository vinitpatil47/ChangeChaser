package com.barclays.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@RestController
public class MessageWatchService {

//	@Autowired
//	private WatchService watchService;
	
//	@Async
//    public void watchMessage() {
//        for (;;) {
//            System.out.println("Hi");
//            try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//    }
	
	@Autowired
	private UserService userService;
	
//	@Scheduled(fixedDelay = 10000)
//	public void watch() {
//		System.out.println("Hello");
//		System.out.println(userService.getStatus("fe6092a8-a0ea-4473-b808-56d5f852e08b"));
//	}
}
