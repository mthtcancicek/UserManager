package com.usermanager.events.user;

public class UserDetails {

	private String id;
	private String userName;
	private String name;
	private String surname;
	private String phoneNumber;
	
	public UserDetails(String id, String userName, String name, String surname, String phoneNumber) {
		this.id = id;
		this.userName = userName;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
	}
	
	public UserDetails(String userName, String name, String surname, String phoneNumber) {
		this.userName = userName;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
	}	
	
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

}
