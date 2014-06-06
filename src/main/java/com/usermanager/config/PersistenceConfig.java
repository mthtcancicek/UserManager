package com.usermanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.usermanager.persistence.repository.UserRepository;
import com.usermanager.persistence.services.UserPersistenceEventHandler;
import com.usermanager.persistence.services.UserPersistenceService;

@Configuration
public class PersistenceConfig {
	  
	@Autowired
	 UserRepository userRepository;

	@Bean
	public UserRepository userRepository() {
		return userRepository;
	}

	@Bean
	public UserPersistenceService userPersistenceService(UserRepository userRepository) {
		return new UserPersistenceEventHandler(userRepository);
	}

}
