package com.usermanager.core.event.fixtures;

import com.usermanager.events.user.UserDetails;

public class UserDetailsFixture {

	public static UserDetails billyJoe() {
		String userName = "user1";
		String name = "Billy";
		String surname = "Joe";
		UserDetails userDetails = new UserDetails(userName,name,surname,"");
		return userDetails;
	}

	public static UserDetails adamWalker() {
		String userName = "user2";
		String name = "Adam";
		String surname = "Walker";
		UserDetails userDetails = new UserDetails(userName,name,surname,"");
		return userDetails;
	}
}
