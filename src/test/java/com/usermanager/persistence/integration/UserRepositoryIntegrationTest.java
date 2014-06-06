package com.usermanager.persistence.integration;


import com.usermanager.config.MongoConfiguration;
import com.usermanager.persistence.domain.User;
import com.usermanager.persistence.repository.UserRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.usermanager.persistence.domain.fixture.PersistenceFixture.billyJoe;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoConfiguration.class})
public class UserRepositoryIntegrationTest {

  private static final String SUSAN = "Susan";

@Autowired
  UserRepository userRepository;

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
  public void thatItemIsInsertedIntoRepoWorks() throws Exception {

    assertEquals(0, mongo.getCollection("user").count());

    userRepository.save(billyJoe());

    assertEquals(1, mongo.getCollection("user").count());
  }
  
  @Test
  public void thatItemUpdatedInRepoWorks() throws Exception {
	  /*
	   * Save
	   */
	  User user = userRepository.save(billyJoe());
	  
	  /*
	   * Update
	   */
	  user.setName(SUSAN);
	  userRepository.save(user);
	  
	  assertEquals(SUSAN, userRepository.findOne(user.getId()).getName());
	  
  }

  @Test
  public void thatItemDeletedFromRepoWorks() throws Exception {
	  /*
	   * Save
	   */
	  User user = userRepository.save(billyJoe());
	  
	  /*
	   * Delete
	   */
	  userRepository.delete(user);
	  
	  
	  assertEquals(0, mongo.getCollection("user").count());
	  
  }  
}
