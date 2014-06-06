package com.usermanager.config;

import com.usermanager.core.services.UserEventHandler;
import com.usermanager.core.services.UserService;
import com.usermanager.persistence.services.UserPersistenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {
	@Bean
	public UserService userService(UserPersistenceService userPersistenceService) {
		return new UserEventHandler(userPersistenceService);
	}
}
