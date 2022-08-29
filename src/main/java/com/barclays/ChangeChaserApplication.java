package com.barclays;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.barclays.service.GraphService;
import com.barclays.service.MessageWatchService;

@SpringBootApplication
public class ChangeChaserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChangeChaserApplication.class, args);
	}

}
