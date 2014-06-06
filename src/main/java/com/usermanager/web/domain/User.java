package com.usermanager.web.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import com.usermanager.events.user.UserDetails;
import com.usermanager.web.validation.group.Save;
import com.usermanager.web.validation.group.Update;

public class User  {

	private String id;
	
	@NotBlank(groups = { Save.class, Update.class })
	private String userName;

	@NotBlank(groups = { Save.class, Update.class })
	private String name;

	@NotBlank(groups = { Save.class, Update.class })
	private String surname;
	
	//@Pattern(regexp="(^$|[0-9]{10})",message="PhoneNumber length must be 10 and all numeric!")
	//@NumberFormat(pattern="(000)-000-00-00")
	//TODO
	private String phoneNumber;	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public static User fromUserDetails(UserDetails userDetails) {
		User user = new User();
		BeanUtils.copyProperties(userDetails, user);
		return user;
	}

	public UserDetails toUserDetails() {
		return new UserDetails(id, userName, name, surname, phoneNumber);
	}
	
}
