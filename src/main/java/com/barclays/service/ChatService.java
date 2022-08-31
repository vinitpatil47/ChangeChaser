package com.barclays.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barclays.model.ChangeRequest;
import com.barclays.repository.ChangeRequestRepository;
import com.google.gson.JsonPrimitive;
import com.microsoft.graph.models.AadUserConversationMember;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.Chat;
import com.microsoft.graph.models.ChatMessage;
import com.microsoft.graph.models.ChatMessageAttachment;
import com.microsoft.graph.models.ChatMessageImportance;
import com.microsoft.graph.models.ChatType;
import com.microsoft.graph.models.ConversationMember;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.requests.ChatCollectionPage;
import com.microsoft.graph.requests.ConversationMemberCollectionPage;
import com.microsoft.graph.requests.ConversationMemberCollectionResponse;

@Service
public class ChatService {

	@Autowired
	private GraphService graphService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChangeRequestRepository changeRequestRepository;
	
	public String createChat(String userEmail) {
		Chat chat = new Chat();
		chat.chatType = ChatType.ONE_ON_ONE;
		LinkedList<ConversationMember> membersList = new LinkedList<ConversationMember>();
		AadUserConversationMember members = new AadUserConversationMember();
		LinkedList<String> rolesList = new LinkedList<String>();
		members.additionalDataManager().put("@odata.type", new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		rolesList.add("owner");
		members.roles = rolesList;
		members.additionalDataManager().put("user@odata.bind", new JsonPrimitive("https://graph.microsoft.com/v1.0/users('cradmin@6sscnx.onmicrosoft.com')"));
		membersList.add(members);
		AadUserConversationMember members1 = new AadUserConversationMember();
		LinkedList<String> rolesList1 = new LinkedList<String>();
		members1.additionalDataManager().put("@odata.type", new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		rolesList1.add("owner");
		members1.roles = rolesList1;
		members1.additionalDataManager().put("user@odata.bind", new JsonPrimitive("https://graph.microsoft.com/v1.0/users('"+ userEmail +"')"));
		membersList.add(members1);
		ConversationMemberCollectionResponse conversationMemberCollectionResponse = new ConversationMemberCollectionResponse();
		conversationMemberCollectionResponse.value = membersList;
		ConversationMemberCollectionPage conversationMemberCollectionPage = new ConversationMemberCollectionPage(conversationMemberCollectionResponse, null);
		chat.members = conversationMemberCollectionPage;

		chat = graphService.getGraphClient().chats()
			.buildRequest()
			.post(chat);
		
		return chat.id;
	}
	
	public String getChat(String userEmail) {
		ChatCollectionPage chats = graphService.getGraphClient().me().chats()
				.buildRequest()
				.expand("members")
				.filter("chatType eq 'oneOnOne' and members/any(v:v/displayName eq '" + userService.getUserByEmail(userEmail).displayName + "')")
				.get();
		
		String chatId;
		if(chats.getCurrentPage().isEmpty()) {
			chatId = createChat(userEmail);
		} else {
			chatId = chats.getCurrentPage().get(0).id;
		}
		return chatId;
	}
	
	public boolean checkLastHalfHour(Date plannedDate) {
		Date date = new Date();
		
		// Calucalte time difference in milliseconds   
        long time_difference = plannedDate.getTime() - date.getTime();  
        
     // Calucalte time difference in seconds  
        long seconds_difference = (time_difference / 1000)% 60; 
        
     // Calucalte time difference in minutes  
        long minutes_difference = (time_difference / (1000*60)) % 60;  
        
     // Calucalte time difference in hours  
        long hours_difference = (time_difference / (1000*60*60)) % 24;   
        
     // Calucalte time difference in days  
        long days_difference = (time_difference / (1000*60*60*24)) % 365;  
        
     // Calucalte time difference in years  
        long years_difference = (time_difference / (1000l*60*60*24*365));
        
        
//        System.out.println(seconds_difference);
//        System.out.println(minutes_difference);
//        System.out.println(hours_difference);
//        System.out.println(days_difference);
//        System.out.println(years_difference);
        
        
        if(years_difference == 0 && days_difference == 0 && hours_difference == 0 && minutes_difference <= 30)
        	return true;
		return false;
	}
	
	public ChatMessage getSimpleMessage(ChangeRequest change) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.subject = null;
		ItemBody body = new ItemBody();
		body.contentType = BodyType.HTML;
		body.content = "<attachment id=\"74d20c7f34aa4a7fb74e2b30004247c5\"></attachment>";
		chatMessage.body = body;
		LinkedList<ChatMessageAttachment> attachmentsList = new LinkedList<ChatMessageAttachment>();
		ChatMessageAttachment attachments = new ChatMessageAttachment();
		attachments.id = "74d20c7f34aa4a7fb74e2b30004247c5";
		attachments.contentType = "application/vnd.microsoft.card.adaptive";
		attachments.contentUrl = null;
		attachments.content = "{\"$schema\":\"http://adaptivecards.io/schemas/adaptive-card.json\",\"type\":\"AdaptiveCard\",\"version\":\"1.0\",\"body\":[{\"type\":\"Container\",\"items\":[{\"type\":\"TextBlock\",\"text\":\"[" + change.getChangeRecord() +"](https://dev82962.service-now.com/change_request.do?sys_id=" + change.getSysId() + ") Approval\",\"weight\":\"bolder\",\"size\":\"large\"}]},{\"type\":\"Container\",\"items\":[{\"type\":\"TextBlock\",\"text\":\"Risk: " + change.getRisk() + "\",\"weight\":\"bolder\",\"color\":\"attention\",\"wrap\":true},{\"type\":\"TextBlock\",\"text\":\"Change implementation starts at GMT " + change.getPlannedStartDate() + "\",\"weight\":\"bolder\",\"wrap\":true},{\"type\":\"FactSet\",\"facts\":[{\"title\":\"Short Description:\",\"value\":\"" + change.getShortDescription() + "\"},{\"title\":\"Justification:\",\"value\":\"" + change.getJustification() +"\"}]}]}],\"actions\":[{\"type\":\"Action.OpenUrl\",\"title\":\"Approve\",\"url\":\"http://localhost:8084/approve/" + change.getChangeRecord() + "\"},{\"type\":\"Action.OpenUrl\",\"title\":\"Reject\",\"url\":\"http://localhost:8084/reject/" + change.getChangeRecord() + "\"}]}";
		attachments.name = null;
		attachments.thumbnailUrl = null;
		attachmentsList.add(attachments);
		chatMessage.attachments = attachmentsList;
		
		return chatMessage;
	}
	
	public ChatMessage getUrgentMessage(ChangeRequest change) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.subject = null;
		chatMessage.importance = ChatMessageImportance.URGENT;
		ItemBody body = new ItemBody();
		body.contentType = BodyType.HTML;
		body.content = "<attachment id=\"74d20c7f34aa4a7fb74e2b30004247c5\"></attachment>";
		chatMessage.body = body;
		LinkedList<ChatMessageAttachment> attachmentsList = new LinkedList<ChatMessageAttachment>();
		ChatMessageAttachment attachments = new ChatMessageAttachment();
		attachments.id = "74d20c7f34aa4a7fb74e2b30004247c5";
		attachments.contentType = "application/vnd.microsoft.card.adaptive";
		attachments.contentUrl = null;
		attachments.content = "{\"$schema\":\"http://adaptivecards.io/schemas/adaptive-card.json\",\"type\":\"AdaptiveCard\",\"version\":\"1.0\",\"body\":[{\"type\":\"Container\",\"items\":[{\"type\":\"TextBlock\",\"text\":\"[" + change.getChangeRecord() +"](https://dev82962.service-now.com/change_request.do?sys_id=" + change.getSysId() + ") Approval\",\"weight\":\"bolder\",\"size\":\"large\"}]},{\"type\":\"Container\",\"items\":[{\"type\":\"TextBlock\",\"text\":\"Risk: " + change.getRisk() + "\",\"weight\":\"bolder\",\"color\":\"attention\",\"wrap\":true},{\"type\":\"TextBlock\",\"text\":\"Change implementation starts at GMT " + change.getPlannedStartDate() + "\",\"weight\":\"bolder\",\"wrap\":true},{\"type\":\"FactSet\",\"facts\":[{\"title\":\"Short Description:\",\"value\":\"" + change.getShortDescription() + "\"},{\"title\":\"Justification:\",\"value\":\"" + change.getJustification() +"\"}]}]}],\"actions\":[{\"type\":\"Action.OpenUrl\",\"title\":\"Approve\",\"url\":\"http://localhost:8084/approve/" + change.getChangeRecord() + "\"},{\"type\":\"Action.OpenUrl\",\"title\":\"Reject\",\"url\":\"http://localhost:8084/reject/" + change.getChangeRecord() + "\"}]}";
		attachments.name = null;
		attachments.thumbnailUrl = null;
		attachmentsList.add(attachments);
		chatMessage.attachments = attachmentsList;
		
		return chatMessage;
	}
	
	public ChatMessage getThankYouMessage(String CR) {
		ChatMessage chatMessage = new ChatMessage();
		ItemBody body = new ItemBody();
		body.content = "CR " + CR + " Approved...Thank You for the approval of Change Request";
		chatMessage.body = body;
		return chatMessage;
	}
	
	public ChatMessage getApologyMessage(String CR) {
		ChatMessage chatMessage = new ChatMessage();
		ItemBody body = new ItemBody();
		body.content = "CR " + CR + " Rejected...Thank You for you time. We will review CR";
		chatMessage.body = body;
		return chatMessage;
	}
	
	public ChatMessage getDetailsMessage(ChangeRequest change) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.subject = null;
		ItemBody body = new ItemBody();
		body.contentType = BodyType.HTML;
		body.content = "<attachment id=\"74d20c7f34aa4a7fb74e2b30004247c5\"></attachment>";
		chatMessage.body = body;
		LinkedList<ChatMessageAttachment> attachmentsList = new LinkedList<ChatMessageAttachment>();
		ChatMessageAttachment attachments = new ChatMessageAttachment();
		attachments.id = "74d20c7f34aa4a7fb74e2b30004247c5";
		attachments.contentType = "application/vnd.microsoft.card.adaptive";
		attachments.contentUrl = null;
//		attachments.content = "{\"$schema\":\"http://adaptivecards.io/schemas/adaptive-card.json\",\"type\":\"AdaptiveCard\",\"version\":\"1.0\",\"body\":[{\"type\":\"Container\",\"items\":[{\"type\":\"TextBlock\",\"text\":\"[" + change.getChangeRecord() +"](https://dev82962.service-now.com/change_request.do?sys_id=" + change.getSysId() + ") Approval\",\"weight\":\"bolder\",\"size\":\"large\"}]},{\"type\":\"Container\",\"items\":[{\"type\":\"TextBlock\",\"text\":\"Risk: " + change.getRisk() + "\",\"weight\":\"bolder\",\"color\":\"attention\",\"wrap\":true},{\"type\":\"TextBlock\",\"text\":\"Change implementation starts at GMT " + change.getPlannedStartDate() + "\",\"weight\":\"bolder\",\"wrap\":true},{\"type\":\"FactSet\",\"facts\":[{\"title\":\"Short Description:\",\"value\":\"" + change.getShortDescription() + "\"},{\"title\":\"Justification:\",\"value\":\"" + change.getJustification() +"\"}]}]}],\"actions\":[{\"type\":\"Action.OpenUrl\",\"title\":\"Approve\",\"url\":\"http://localhost:8084/approve/" + change.getChangeRecord() + "\"},{\"type\":\"Action.OpenUrl\",\"title\":\"Reject\",\"url\":\"http://localhost:8084/reject/" + change.getChangeRecord() + "\"}]}";
		attachments.content = "{\"$schema\":\"http://adaptivecards.io/schemas/adaptive-card.json\",\"type\":\"AdaptiveCard\",\"version\":\"1.0\",\"body\":[{\"type\":\"Container\",\"items\":[{\"type\":\"TextBlock\",\"text\":\"[" + change.getChangeRecord() + "](https://dev82962.service-now.com/change_request.do?sys_id=" + change.getSysId() + ") Details\",\"weight\":\"bolder\",\"size\":\"large\"}]},{\"type\":\"Container\",\"items\":[{\"type\":\"TextBlock\",\"text\":\"Risk: " + change.getRisk() + "\",\"weight\":\"bolder\",\"color\":\"attention\",\"wrap\":true},{\"type\":\"FactSet\",\"facts\":[{\"title\":\"Description:\",\"value\":\"" + change.getDescription() + "\"},{\"title\":\"Assign Group:\",\"value\":\"" + change.getAssignGroup() + "\"},{\"title\":\"Planned Start Date:\",\"value\":\"" + change.getPlannedStartDate() + "\"},{\"title\":\"Planned End Date:\",\"value\":\"" + change.getPlannedEndDate() + "\"},{\"title\":\"State:\",\"value\":\"" + change.getState() + "\"},{\"title\":\"Justification:\",\"value\":\"" + change.getJustification() + "\"},{\"title\":\"Test Plan:\",\"value\":\"" + change.getTestPlan() + "\"},{\"title\":\"Implementation Plan:\",\"value\":\"" + change.getImplementationPlan() + "\"},{\"title\":\"Risk Impact Analysis:\",\"value\":\"" + change.getRiskImpactAnalysis() + "\"},{\"title\":\"Backout Plan:\",\"value\":\"" + change.getBackoutPlan() + "\"}]}]}],\"actions\":[{\"type\":\"Action.OpenUrl\",\"title\":\"Approve\",\"url\":\"http://localhost:8084/approve/" + change.getChangeRecord() + "\"},{\"type\":\"Action.OpenUrl\",\"title\":\"Reject\",\"url\":\"http://localhost:8084/reject/" + change.getChangeRecord() + "\"}]}";
		attachments.name = null;
		attachments.thumbnailUrl = null;
		attachmentsList.add(attachments);
		chatMessage.attachments = attachmentsList;
		
		return chatMessage;
	}
	
	public void sendDetailsMessage(ChangeRequest change, String chatId) {
		
	}
	
	public void sendChat(String userEmail, ChangeRequest change) {
		String chatId = getChat(userEmail);
		
		ChatMessage chatMessage = null;
		if(change.getFlag() == 0)
			chatMessage = getSimpleMessage(change);
		else if(change.getFlag() == 1)
			chatMessage = getUrgentMessage(change);
		else if(change.getFlag() == 3)
			chatMessage = getThankYouMessage(change.getChangeRecord());
		else if(change.getFlag() == 4)
			chatMessage = getApologyMessage(change.getChangeRecord());
		
		graphService.getGraphClient().chats(chatId).messages()
			.buildRequest()
			.post(chatMessage);
	}
	
	public List<String> getByteOptizer() {
		List<String> list = new ArrayList<String>();
		list.add("ashishurfravi@6sscnx.onmicrosoft.com");
		list.add("admin@6sscnx.onmicrosoft.com");
		
		return list;
	}
	
	public List<String> getBitOptizer() {
		List<String> list = new ArrayList<String>();
		list.add("vinitpatil@6sscnx.onmicrosoft.com");
		return list;
	}
	
	public void checkAndSendSimpleChat(boolean check) {
		List<String> list = null;
		List<ChangeRequest> changeRequest = (List<ChangeRequest>) changeRequestRepository.findAll();
		
		try {
			for(ChangeRequest change : changeRequest) {
				if(change.getAssignGroup().equals("Byte~Optimizer"))
					list = getByteOptizer();
				else 
					list = getBitOptizer();
				
				if((change.getFlag() == 0 || change.getFlag() == 1) && checkLastHalfHour(change.getPlannedStartDate())) {
					for(String l : list) {
						change.setFlag(1);
						sendChat(l, change);
						change.setFlag(2);
						changeRequestRepository.save(change);
					}
				} else if(change.getFlag() == 0 || (change.getFlag() == 1 && !check)) {
					for(String l : list) {
						String status = userService.getStatus(userService.getUserByEmail(l).id);
						if(status.equals("Available")) {
							change.setFlag(0);
							sendChat(l, change);
							change.setFlag(1);
							changeRequestRepository.save(change);
						}
					}
				} else if(change.getFlag() == 3) {
					for(String l : list) {
						String status = userService.getStatus(userService.getUserByEmail(l).id);
						if(status.equals("Available")) {
							sendChat(l, change);
							change.setFlag(5);
							changeRequestRepository.save(change);
						}
					}
				} else if(change.getFlag() == 4) {
					for(String l : list) {
						String status = userService.getStatus(userService.getUserByEmail(l).id);
						if(status.equals("Available")) {
							sendChat(l, change);
							change.setFlag(5);
							changeRequestRepository.save(change);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void checkAndSendRecurringChat() {
		try {
			checkAndSendSimpleChat(false);
		} catch(Exception ex) {
			
		}
	}
	
	public void sendDetailsMessage(String changeNo, String chatId) {
		ChangeRequest changeRequest = changeRequestRepository.findByChangeRecord(changeNo);
		
	}
}
