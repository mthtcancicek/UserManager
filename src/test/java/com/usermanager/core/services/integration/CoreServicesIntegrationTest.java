package com.usermanager.core.services.integration;




import com.usermanager.config.CoreConfig;
import com.usermanager.config.MongoConfiguration;
import com.usermanager.config.PersistenceConfig;
import com.usermanager.core.services.UserService;
import com.usermanager.events.user.DeleteUserEvent;
import com.usermanager.events.user.RequestAllUsersEvent;
import com.usermanager.events.user.SaveUserEvent;
import com.usermanager.events.user.UpdateUserEvent;
import com.usermanager.events.user.UserDetails;
import com.usermanager.events.user.UserSavedEvent;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.usermanager.event.fixtures.UserDetailsFixture.adamWalker;
import static com.usermanager.event.fixtures.UserDetailsFixture.billyJoe;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CoreConfig.class, PersistenceConfig.class, MongoConfiguration.class})
public class CoreServicesIntegrationTest {

  private static final String Joe = "Joe";

  @Autowired
  MongoOperations mongo;
  
  @Autowired
  UserService userService;

  @Before
  public void setup() throws Exception {
    mongo.dropCollection("user");
  }

  @After
  public void teardown() {
    mongo.dropCollection("user");
  }

  @Test
  public void thatItemIsInsertedIntoRepoWorks() throws Exception {

    assertEquals(0, userService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails().size());

    userService.saveUser(new SaveUserEvent(adamWalker()));

    assertEquals(1, userService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails().size());
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void thatItemUpdatedInRepoWorks() throws Exception {
	  
	  assertEquals(0, userService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails().size());
	  
	  /*
	   * Save Adam and Billy
	   */
	  UserSavedEvent userSavedEvent = userService.saveUser(new SaveUserEvent(adamWalker()));
	  
	  userService.saveUser(new SaveUserEvent(billyJoe()));
			  
	  
	  /*
	   * Update Adam to Joe
	   */
	  UserDetails adamWalker = userSavedEvent.getUserDetails();
	  adamWalker.setName(Joe);
	  
	  userService.updateUser(new UpdateUserEvent(adamWalker));
	  
	  /*
	   * Assert Adam is now Joe and Billy is still Billy
	   */
	  assertThat(
			  userService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails()
			  , 
			  hasItems(
					  Matchers.<UserDetails>hasProperty("name", equalTo(Joe)),
					  Matchers.<UserDetails>hasProperty("name", equalTo(billyJoe().getName()))
					  )
			  );
	  
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void thatItemDeletedFromRepoWorks() throws Exception {
	  
	  assertEquals(0, userService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails().size());
	  
	  /*
	   * Save Adam and Billy
	   */
	  UserSavedEvent userSavedEvent = userService.saveUser(new SaveUserEvent(adamWalker()));
	  
	  userService.saveUser(new SaveUserEvent(billyJoe()));
			  
	  
	  /*
	   * Delete Adam
	   */	  
	  userService.deleteUser(new DeleteUserEvent(userSavedEvent.getId()));
	  
	  /*
	   * Assert Only Billy Exists
	   */
	  assertThat(
			  userService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails()
			  , 
			  hasItems(
					  Matchers.<UserDetails>hasProperty("name", equalTo(billyJoe().getName())),
					  Matchers.<UserDetails>hasProperty("name", not(equalTo(adamWalker().getName())))
					  )
			  );
	  
  }    
}
