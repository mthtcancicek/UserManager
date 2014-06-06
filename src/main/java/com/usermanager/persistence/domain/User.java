package com.usermanager.persistence.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.usermanager.events.user.UserDetails;

@Document(collection = "user")
public class User {
	
	@Id
	private String id;

	@Indexed
	private String userName;
	
	private String name;
	
	private String surname;
	
	private String phoneNumber;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public UserDetails toUserDetails() {
		return new UserDetails(id, userName, name, surname, phoneNumber);
	}

	public static User fromUserDetails(UserDetails details) {
		User user = new User();
		
		user.id = details.getId();
		user.name = details.getName();
		user.surname = details.getSurname();
		user.userName = details.getUserName();
		user.phoneNumber = details.getPhoneNumber();
		
		return user;
	}
}

