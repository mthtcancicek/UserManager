package com.usermanager.persistence.services.integration;



import com.usermanager.config.MongoConfiguration;
import com.usermanager.config.PersistenceConfig;
import com.usermanager.events.user.DeleteUserEvent;
import com.usermanager.events.user.RequestAllUsersEvent;
import com.usermanager.events.user.SaveUserEvent;
import com.usermanager.events.user.UpdateUserEvent;
import com.usermanager.events.user.UserDetails;
import com.usermanager.events.user.UserSavedEvent;
import com.usermanager.persistence.services.UserPersistenceService;

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
@ContextConfiguration(classes = {PersistenceConfig.class, MongoConfiguration.class})
public class PersistenceServicesIntegrationTest {

  private static final String Joe = "Joe";

  @Autowired
  MongoOperations mongo;
  
  @Autowired
  UserPersistenceService userPersistentService;

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

    assertEquals(0, userPersistentService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails().size());

    userPersistentService.saveUser(new SaveUserEvent(adamWalker()));

    assertEquals(1, userPersistentService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails().size());
  }
  
  @SuppressWarnings("unchecked")
@Test
  public void thatItemUpdatedInRepoWorks() throws Exception {
	  
	  assertEquals(0, userPersistentService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails().size());
	  
	  /*
	   * Save Adam and Billy
	   */
	  UserSavedEvent userSavedEvent = userPersistentService.saveUser(new SaveUserEvent(adamWalker()));
	  
	  userPersistentService.saveUser(new SaveUserEvent(billyJoe()));
			  
	  
	  /*
	   * Update Adam to Joe
	   */
	  UserDetails adamWalker = userSavedEvent.getUserDetails();
	  adamWalker.setName(Joe);
	  
	  userPersistentService.updateUser(new UpdateUserEvent(adamWalker));
	  
	  /*
	   * Assert Adam is now Joe and Billy is still Billy
	   */
	  assertThat(
			  userPersistentService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails()
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
	  
	  assertEquals(0, userPersistentService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails().size());
	  
	  /*
	   * Save Adam and Billy
	   */
	  UserSavedEvent userSavedEvent = userPersistentService.saveUser(new SaveUserEvent(adamWalker()));
	  
	  userPersistentService.saveUser(new SaveUserEvent(billyJoe()));
			  
	  
	  /*
	   * Delete Adam
	   */	  
	  userPersistentService.deleteUser(new DeleteUserEvent(userSavedEvent.getId()));
	  
	  /*
	   * Assert Only Billy Exists
	   */
	  assertThat(
			  userPersistentService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails()
			  ,
			  hasItems(Matchers.<UserDetails>hasProperty("name", equalTo(billyJoe().getName())))
			  );
	  
	  assertThat(
			  userPersistentService.requestAllUsers(new RequestAllUsersEvent()).getUserDetails()
			  ,
			  not(hasItems(Matchers.<UserDetails>hasProperty("name", equalTo(adamWalker().getName()))))
			  );
	  
	  
  }   
}
