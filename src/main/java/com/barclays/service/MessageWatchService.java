package com.barclays.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessageWatchService {

//	@Autowired
//	private WatchService watchService;
	
	@Async
    public void watchMessage() {
        for (;;) {
            System.out.println("Hi");
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
