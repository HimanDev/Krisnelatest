package com.krisnela.test.mvp.dto;

import org.springframework.data.annotation.Id;

public class Greeting {
	@Id
	private String objectId;
	private String senderId;
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	 private String content;
	 public Greeting(String content,String senderId) {
	        this.content = content;
	        this.senderId = senderId;
	    }

	    public String getContent() {
	        return content;
	    }
		public String getSenderId() {
			return senderId;
		}

}
