package com.barclays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.barclays.service.GraphService;

@SpringBootApplication
public class ChangeChaserApplication {
	
	@Autowired
	private GraphService graphService;

	public static void main(String[] args) {
		SpringApplication.run(ChangeChaserApplication.class, args);
	}

}
