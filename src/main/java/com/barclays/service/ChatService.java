package com.barclays.service;

import java.util.ArrayList;
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
		members.additionalDataManager().put("user@odata.bind", new JsonPrimitive("https://graph.microsoft.com/v1.0/users('admin@6sscnx.onmicrosoft.com')"));
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
		
		System.out.println(chats.getCurrentPage());
		
		String chatId;
		if(chats.getCurrentPage().isEmpty()) {
			System.out.println(true);
			chatId = createChat(userEmail);
		} else {
			System.out.println(false);
			chatId = chats.getCurrentPage().get(0).id;
		}
		System.out.println(chatId);
		return chatId;
	}
	
	public void sendChat(String userEmail) {
		String chatId = getChat(userEmail);
		
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
		attachments.contentType = "application/vnd.microsoft.card.thumbnail";
		attachments.contentUrl = null;
		attachments.content = "{\n  \"title\": \"<h3>CHG123456789 Approval</h3>\",\n  \"subtitle\": \"<h5>Hello All</h5>\",\n  \"text\": \"Please review CHG123456789.<br>If find good Please approve or reject.\",\n  \"buttons\": [\n    {\n      \"type\": \"messageBack\",\n      \"title\": \"Approve\",\n      \"text\": \"Approved\",\n      \"displayText\": \"Approved\",\n      \"value\": \"Approved\"\n    },\n    {\n      \"type\": \"messageBack\",\n      \"title\": \"Reject\",\n      \"text\": \"Rejected\",\n      \"displayText\": \"Rejected\",\n      \"value\": \"Rejected\"\n    }\n  ]\n}";
		attachments.name = null;
		attachments.thumbnailUrl = null;
		attachmentsList.add(attachments);
		chatMessage.attachments = attachmentsList;
		
		graphService.getGraphClient().chats(chatId).messages()
			.buildRequest()
			.post(chatMessage);
		
	}
	
	public List<String> getByteOptizer() {
		List<String> list = new ArrayList<String>();
		list.add("vinitpatil@6sscnx.onmicrosoft.com");
		list.add("ashishurfravi@6sscnx.onmicrosoft.com");
		
		return list;
	}
	
	public List<String> getBitOptizer() {
		List<String> list = new ArrayList<String>();
		list.add("mayurshah@6sscnx.onmicrosoft.com");
		
		return list;
	}
	
//	public void checkAndSendSimpleChat(boolean check) {
//		List<ChangeRequest> changeRequest = (List<ChangeRequest>) changeRequestRepository.findAll();
//		try {
//			for(ChangeRequest change : changeRequest) {
//				if(change.getState() == 0 || !check) {
//					if(change.getAssignGroup().equals("Byte~Optimizer")) {
//						List<String> list = getByteOptizer();
//						for(String l : list) {
//							String status = userService.getStatus(userService.getUserByEmail(l).id);
//							if(status.equals("Available")) {
//								sendChat(l);
//								change.setState(1);
//								changeRequestRepository.save(change);
//							}
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//	
//	public void checkAndSendRecurringChat() {
//		try {
//			checkAndSendSimpleChat(false);
//		} catch(Exception ex) {
//			
//		}
//	}
}
