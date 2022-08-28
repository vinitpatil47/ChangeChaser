package com.barclays.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.service.UserService;
import com.microsoft.graph.models.User;

@RestController
public class UserController {
	
	@Autowired
	private UserService UserService;
	
	@GetMapping("/me")
	public void getMe() throws Exception {
		User user = UserService.getUser();
		System.out.println(user.displayName);
	}

}
