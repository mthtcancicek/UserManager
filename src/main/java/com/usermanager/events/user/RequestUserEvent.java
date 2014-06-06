package com.usermanager.events.user;

import com.usermanager.events.RequestReadEvent;

public class RequestUserEvent extends RequestReadEvent {
	String userName;

	public RequestUserEvent(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
