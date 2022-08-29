package com.barclays.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.service.GraphService;
import com.barclays.service.MessageWatchService;
import com.barclays.service.TeamService;
import com.microsoft.graph.models.Team;


@RestController
public class TeamController {
	
	@Autowired
	private GraphService graphservice;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@GetMapping("/initialize")
	public void initializeClient() {
		System.out.println("Java Graph Tutorial");
        System.out.println();

        try {
            graphservice.initializeGraphForUserAuth(
                challenge -> System.out.println(challenge.getMessage()));
            
//            applicationContext.getBean(MessageWatchService.class).watchMessage();
        } catch (Exception e)
        {
            System.out.println("Error initializing Graph for user auth");
            System.out.println(e.getMessage());
        }
	}

	@GetMapping("/teams/{teams}")
	public void createTeams(@PathVariable String teams) throws InterruptedException {
		
		teamService.createTeam(teams);
		Thread.sleep(15000);
		String id = teamService.getTeamId(teams);
		System.out.println(id);
		if(id != null)
			teamService.addMember(id);
	}
	
	@GetMapping("/teams/{teams}/chat")
	public void sendChat(@PathVariable String teams) {
		String teamId = teamService.getTeamId(teams);
		String channelId = teamService.getChannelId(teamId);
		
		if(channelId != null)
			teamService.sendChatToChannel(teamId, channelId);
		
	}

}
