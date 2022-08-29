package com.barclays.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microsoft.graph.models.Presence;
import com.microsoft.graph.models.User;

@Service
public class UserService {
	
	@Autowired
	private GraphService graphService;
	
	public User getUser() throws Exception {
        // Ensure client isn't null
        if (this.graphService.getGraphClient() == null) {
            throw new Exception("Graph has not been initialized for user auth");
        }

        return this.graphService.getGraphClient().me()
            .buildRequest()
            .select("displayName,mail,userPrincipalName")
            .get();
    }
	
	public User getUser(String userId) {
		return this.graphService.getGraphClient().users(userId)
			.buildRequest()
			.get();
	}
	
	public User getUserByEmail(String userEmail) {
		User user = graphService.getGraphClient().users(userEmail)
				.buildRequest()
				.get();
		
		return user;
	}
	
	public String getStatus(String userId) {
		try {
			Presence presence = graphService.getGraphClient().users(userId).presence()
					.buildRequest()
					.get();
			return presence.availability;
		} catch (NullPointerException expection) {
			return null;
		}
		
	}

}
