package com.barclays.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonPrimitive;
import com.microsoft.graph.models.AadUserConversationMember;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.ChatMessage;
import com.microsoft.graph.models.ChatMessageAttachment;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Team;
import com.microsoft.graph.requests.ChannelCollectionPage;
import com.microsoft.graph.requests.TeamCollectionWithReferencesPage;

@Service
public class TeamService {
	
	@Autowired
	private GraphService graphService;
	
	public Team createTeam(String teamName) {
		Team team = new Team();
		team.additionalDataManager().put("template@odata.bind", new JsonPrimitive("https://graph.microsoft.com/v1.0/teamsTemplates('standard')"));
		team.displayName = teamName;
		team.description = "My Sample Team’s Description";
		

//		Team team = new Team();
//		team.additionalDataManager().put("template@odata.bind", new JsonPrimitive("https://graph.microsoft.com/v1.0/teamsTemplates('standard')"));
//		team.displayName = teamName;
//		team.description = "My Sample Team’s Description";
//		
//		LinkedList<ConversationMember> membersList = new LinkedList<ConversationMember>();
//		
//		AadUserConversationMember members = new AadUserConversationMember();
//		LinkedList<String> rolesList = new LinkedList<String>();
//		rolesList.add("owner");
//		members.additionalDataManager().put("@odata.type",new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
//		members.roles = rolesList;
//		members.additionalDataManager().put("user@odata.bind", new JsonPrimitive("https://graph.microsoft.com/v1.0/users('admin@6sscnx.onmicrosoft.com')"));
//		membersList.add(members);
//		
//		AadUserConversationMember members1 = new AadUserConversationMember();
//		LinkedList<String> rolesList1 = new LinkedList<String>();
//		rolesList1.add("owner");
//		members1.additionalDataManager().put("@odata.type",new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
//		members1.roles = rolesList1;
//		members1.additionalDataManager().put("user@odata.bind", new JsonPrimitive("https://graph.microsoft.com/v1.0/users('ashishurfravi@6sscnx.onmicrosoft.com')"));
//		membersList.add(members1);
//		
//		ConversationMemberCollectionResponse conversationMemberCollectionResponse = new ConversationMemberCollectionResponse();
//		conversationMemberCollectionResponse.value = membersList;
//		ConversationMemberCollectionPage conversationMemberCollectionPage = new ConversationMemberCollectionPage(conversationMemberCollectionResponse, null);
//		team.members = conversationMemberCollectionPage;

		team = graphService.getGraphClient().teams()
			.buildRequest()
			.post(team);
		
		return team;
	}
	
	public String getTeamId(String teamName) {
		
		TeamCollectionWithReferencesPage joinedTeams = graphService.getGraphClient().me().joinedTeams()
				.buildRequest()
				.select("id,displayName")
				.get();
		List<Team> teams = joinedTeams.getCurrentPage();
		for(Team team : teams) {
			System.out.println(team.displayName);
			if(team.displayName.equals(teamName))
				return team.id;
		}
		return null;
	}
	
	public String getChannelId(String teamId) {
		
		ChannelCollectionPage channels = graphService.getGraphClient().teams(teamId).channels()
				.buildRequest()
				.select("id,displayName")
				.get();
		if(channels.getCurrentPage().size() > 0)
			return channels.getCurrentPage().get(0).id;
		else
			return null;
	}
	
	public void addMember(String teamId) {
		AadUserConversationMember conversationMember = new AadUserConversationMember();
		LinkedList<String> rolesList = new LinkedList<String>();
		rolesList.add("member");
		conversationMember.additionalDataManager().put("@odata.type", new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		conversationMember.roles = rolesList;
		conversationMember.additionalDataManager().put("user@odata.bind", new JsonPrimitive("https://graph.microsoft.com/v1.0/users('vinitpatil@6sscnx.onmicrosoft.com')"));

		graphService.getGraphClient().teams(teamId).members()
			.buildRequest()
			.post(conversationMember);
	}
	
	public void sendChatToChannel(String teamId, String channelId) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.subject = null;
		ItemBody body = new ItemBody();
		body.contentType = BodyType.HTML;
		body.content = "<attachment id=\"74d20c7f34aa4a7fb74e2b30004247c5\"></attachment>";
		chatMessage.body = body;
		LinkedList<ChatMessageAttachment> attachmentsList = new LinkedList<ChatMessageAttachment>();
		ChatMessageAttachment attachments = new ChatMessageAttachment();
		attachments.id = "74d20c7f34aa4a7fb74e2b30004247c5";
		attachments.contentType = "application/vnd.microsoft.card.thumbnail";
		attachments.contentUrl = null;
		attachments.content = "{\n  \"title\": \"<h3>CHG123456789 Approval</h3>\",\n  \"subtitle\": \"<h5>Hello All</h5>\",\n  \"text\": \"Please review CHG123456789.<br>If find good Please approve or reject.\",\n  \"buttons\": [\n    {\n      \"type\": \"messageBack\",\n      \"title\": \"Approve\",\n      \"text\": \"Approved\",\n      \"displayText\": \"Approved\",\n      \"value\": \"Approved\"\n    },\n    {\n      \"type\": \"messageBack\",\n      \"title\": \"Reject\",\n      \"text\": \"Rejected\",\n      \"displayText\": \"Rejected\",\n      \"value\": \"Rejected\"\n    }\n  ]\n}";
		attachments.name = null;
		attachments.thumbnailUrl = null;
		attachmentsList.add(attachments);
		chatMessage.attachments = attachmentsList;

		graphService.getGraphClient().teams(teamId).channels(channelId).messages()
			.buildRequest()
			.post(chatMessage);
	}

}
