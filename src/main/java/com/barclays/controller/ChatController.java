package com.barclays.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
//	@GetMapping("/chats/{userEmail}")
//	public void sendChat(@PathVariable String userEmail) {
//		chatService.sendChat(userEmail);
//	}
	
	@Scheduled(fixedDelay = 11000)
	public void checkAndSendSimpleChat() {
		chatService.checkAndSendSimpleChat(true);
	}
	
	@Scheduled(cron = "0 0 */1 * * *")
	public void checkAndSendRecurringChat() {
		chatService.checkAndSendRecurringChat();
	}
	
	@GetMapping("/changerequest/{changeNo}/{chatId}")
	public void sendDetailsMessage(@PathVariable String changeNo, @PathVariable String chatId) {
		chatService.sendDetailsMessage(changeNo, chatId);
	}
	
	@GetMapping("/date")
	public void diff() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = sdf.parse("2022-08-30 02:22:00");
		Date date2 = sdf.parse("2022-08-31 10:59:00");
		
//		Date date = new Date();
		
		System.out.println(chatService.checkLastHalfHour(date2));
	}
}
