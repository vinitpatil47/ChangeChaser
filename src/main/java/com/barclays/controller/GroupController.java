package com.barclays.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.service.GroupService;

@RestController
public class GroupController {

	@Autowired
	private GroupService groupService;
	
	@GetMapping("groups/{groupName}")
	public void createGroup(@PathVariable String groupName) {
		
		String groupId = groupService.createGroup(groupName);
		if(groupId != null)
			groupService.sendchat(groupId);
	}
}
