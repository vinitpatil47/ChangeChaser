package com.barclays.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.service.GroupService;
import com.barclays.service.UserService;
import com.microsoft.graph.models.Chat;
import com.microsoft.graph.models.ChatMessage;

@RestController
public class GroupController {

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("groups/{groupName}")
	public void createGroup(@PathVariable String groupName) {
		
		String groupId = groupService.createGroup(groupName);
		if(groupId != null)
			groupService.sendchat(groupId);
	}
	
	@GetMapping("/groups/{groupName}/chats")
	public void getChat(@PathVariable String groupName) {
		List<ChatMessage> chatMessage = groupService.getChat(groupName);
		
		for(ChatMessage msg : chatMessage) {
			if(msg.body.content.equals("Approved")) {
				System.out.println(msg.from.user.id);
				System.out.println(msg.from.user.displayName);
				System.out.println(userService.getUser(msg.from.user.id).mail);
			}
		}
	}
}
