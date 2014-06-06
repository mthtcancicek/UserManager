package com.usermanager.event.fixtures;

import com.usermanager.events.user.UserDetails;

public class UserDetailsFixture {

	public static UserDetails billyJoe() {
		String userName = "user1";
		String name = "Billy";
		String surname = "Joe";
		String id = "1"; //Mock id
		UserDetails userDetails = new UserDetails(id,userName,name,surname,"");
		return userDetails;
	}

	public static UserDetails adamWalker() {
		String userName = "user2";
		String name = "Adam";
		String surname = "Walker";
		String id = "2"; //Mock id
		UserDetails userDetails = new UserDetails(id,userName,name,surname,"");
		return userDetails;
	}
}
