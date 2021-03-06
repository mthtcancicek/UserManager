package com.usermanager.config;

import static junit.framework.TestCase.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.usermanager.core.services.UserService;
import com.usermanager.event.fixtures.UserDetailsFixture;
import com.usermanager.events.user.AllUsersEvent;
import com.usermanager.events.user.RequestAllUsersEvent;
import com.usermanager.events.user.SaveUserEvent;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CoreConfig.class, PersistenceConfig.class, MongoConfiguration.class})
public class CoreDomainIntegrationTest {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MongoOperations mongo;

	@Before
	public void setup() throws Exception {
		mongo.dropCollection("user");
	}

	@After
	public void teardown() {
		mongo.dropCollection("user");
	}
			
	@Test
	public void thatAllUsersReturned() {
		
	SaveUserEvent saveUserEvent = new SaveUserEvent(UserDetailsFixture.adamWalker());
	userService.saveUser(saveUserEvent);
	
	AllUsersEvent allUsers  = userService.requestAllUsers(new RequestAllUsersEvent());
	
	assertEquals(1, allUsers.getUserDetails().size());
			
	}	

}
