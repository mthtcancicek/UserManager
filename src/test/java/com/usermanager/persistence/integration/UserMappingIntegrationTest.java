package com.usermanager.persistence.integration;

import com.usermanager.config.MongoConfiguration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.usermanager.persistence.domain.fixture.MongoAssertions.usingMongo;
import static com.usermanager.persistence.domain.fixture.PersistenceFixture.billyJoe;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoConfiguration.class })
public class UserMappingIntegrationTest {

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
	public void thatItemIsInsertedIntoCollectionHasCorrectIndexes() throws Exception {

		mongo.insert(billyJoe());

		assertEquals(1, mongo.getCollection("user").count());

		assertTrue(usingMongo(mongo).collection("user").hasIndexOn("_id"));
		//assertTrue(usingMongo(mongo).collection("user").hasIndexOn("userName"));//https://jira.spring.io/browse/DATAMONGO-345
	}
}
