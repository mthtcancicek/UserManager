package com.usermanager.persistence.domain.fixture;

import com.usermanager.persistence.domain.User;

public class PersistenceFixture {

  public static User billyJoe() {
    User user= new User();
    user.setName("Billy");
    user.setUserName("user1");
    user.setSurname("Joe");
    return user;
  }

  public static User adamWalker() {
	    User user= new User();
	    user.setName("Adam");
	    user.setUserName("user2");
	    user.setSurname("Walker");
	    return user;
	  }
}
