package com.usermanager.core.services;

import com.usermanager.events.user.*;
import com.usermanager.persistence.services.UserPersistenceService;

public class UserEventHandler implements UserService {

	private UserPersistenceService userPersistenceService;

	public UserEventHandler(UserPersistenceService userPersistenceService) {
		this.userPersistenceService = userPersistenceService;
	}

	@Override
	public AllUsersEvent requestAllUsers(RequestAllUsersEvent requestAllCurrentUsersEvent) {

		return userPersistenceService
				.requestAllUsers(requestAllCurrentUsersEvent);
	}

	@Override
	public UserSavedEvent saveUser(SaveUserEvent saveUserEvent) {
		return userPersistenceService.saveUser(saveUserEvent);
	}

	@Override
	public UserDeletedEvent deleteUser(DeleteUserEvent deleteUserEvent) {

		return userPersistenceService.deleteUser(deleteUserEvent);
	}

	@Override
	public UserUpdatedEvent updateUser(UpdateUserEvent updateUserEvent) {

		return userPersistenceService.updateUser(updateUserEvent);
	}
	
	@Override
	public UserEvent requestUser(RequestUserEvent requestUserEvent) {

		return userPersistenceService.requestUser(requestUserEvent);
	}	

}
