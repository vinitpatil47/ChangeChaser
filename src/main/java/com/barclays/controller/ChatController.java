package com.barclays.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.service.ChatService;

@EnableScheduling
@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@GetMapping("/chats/{userEmail}")
	public void sendChat(@PathVariable String userEmail) {
		chatService.sendChat(userEmail);
	}
	
//	@Scheduled(fixedDelay = 11000)
//	public void checkAndSendSimpleChat() {
//		chatService.checkAndSendSimpleChat(true);
//	}
//	
//	@Scheduled(fixedDelay = 53000)
//	public void checkAndSendRecurringChat() {
//		chatService.checkAndSendRecurringChat();
//	}
}
