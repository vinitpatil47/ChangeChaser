package com.barclays.service;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonPrimitive;
import com.microsoft.graph.models.AadUserConversationMember;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.Chat;
import com.microsoft.graph.models.ChatMessage;
import com.microsoft.graph.models.ChatMessageAttachment;
import com.microsoft.graph.models.ChatMessageImportance;
import com.microsoft.graph.models.ChatMessageType;
import com.microsoft.graph.models.ChatType;
import com.microsoft.graph.models.ConversationMember;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.requests.ConversationMemberCollectionPage;
import com.microsoft.graph.requests.ConversationMemberCollectionResponse;

@Service
public class GroupService {

	@Autowired
	private GraphService graphSerivice;
	
	public String createGroup(String groupName) {
		Chat chat = new Chat();
		chat.chatType = ChatType.GROUP;
		chat.topic = groupName;
		LinkedList<ConversationMember> membersList = new LinkedList<ConversationMember>();
		AadUserConversationMember members = new AadUserConversationMember();
		LinkedList<String> rolesList = new LinkedList<String>();
		rolesList.add("owner");
		members.additionalDataManager().put("@odata.type", new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		members.roles = rolesList;
		members.additionalDataManager().put("user@odata.bind", new JsonPrimitive("https://graph.microsoft.com/v1.0/users('admin@6sscnx.onmicrosoft.com')"));
		membersList.add(members);
		AadUserConversationMember members1 = new AadUserConversationMember();
		LinkedList<String> rolesList1 = new LinkedList<String>();
		rolesList1.add("owner");
		members1.additionalDataManager().put("@odata.type", new JsonPrimitive("#microsoft.graph.aadUserConversationMember"));
		members1.roles = rolesList1;
		members1.additionalDataManager().put("user@odata.bind", new JsonPrimitive("https://graph.microsoft.com/v1.0/users('vinitpatil@6sscnx.onmicrosoft.com')"));
		membersList.add(members1);
		
		ConversationMemberCollectionResponse conversationMemberCollectionResponse = new ConversationMemberCollectionResponse();
		conversationMemberCollectionResponse.value = membersList;
		ConversationMemberCollectionPage conversationMemberCollectionPage = new ConversationMemberCollectionPage(conversationMemberCollectionResponse, null);
		chat.members = conversationMemberCollectionPage;

		chat = graphSerivice.getGraphClient().chats()
			.buildRequest()
			.post(chat);
		return chat.id;
	}
	
	public void sendchat(String groupId) {
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
		
		graphSerivice.getGraphClient().chats(groupId).messages()
		.buildRequest()
		.post(chatMessage);
	}
}
