package com.usermanager.config;

import com.mongodb.Mongo;
import com.usermanager.persistence.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories(basePackages = "com.usermanager.persistence.repository",
      includeFilters = @ComponentScan.Filter(value = {UserRepository.class}, type = FilterType.ASSIGNABLE_TYPE))
public class MongoConfiguration {

  public @Bean
  MongoTemplate mongoTemplate(Mongo mongo) throws UnknownHostException {
    return new MongoTemplate(mongo, "userManager");
  }

  public @Bean Mongo mongo() throws UnknownHostException {
    return new com.mongodb.MongoClient("localhost");
  }
}
