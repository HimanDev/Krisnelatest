package com.krisnela.test.mvp.dto;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Message extends AbstractDto{
	

	private String message;
	@DBRef
	private MessageThread messageThread;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageThread getMessageThread() {
		return messageThread;
	}
	public void setMessageThread(MessageThread messageThread) {
		this.messageThread = messageThread;
	}

}
