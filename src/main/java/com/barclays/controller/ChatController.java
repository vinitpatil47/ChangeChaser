package com.barclays.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.service.ChatService;

@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@GetMapping("/chats/{userEmail}")
	public void sendChat(@PathVariable String userEmail) {
		chatService.sendChat(userEmail);
	}
}
