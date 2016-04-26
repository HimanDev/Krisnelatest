package com.krisnela.test.mvp.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;


public class MessageThread extends AbstractDto {

	@DBRef
	private User sender;
	@DBRef
	private User receiver;
	
	@DBRef
	private List<Message>  messages;

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public MessageThread(){}
	public MessageThread(String objectId){
		this.objectId=objectId;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}