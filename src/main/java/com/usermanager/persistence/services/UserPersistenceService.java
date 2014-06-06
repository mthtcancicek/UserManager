package com.usermanager.persistence.services;

import com.usermanager.events.user.*;

public interface UserPersistenceService {

  AllUsersEvent requestAllUsers(RequestAllUsersEvent requestAllUsersEvent);
  UserSavedEvent saveUser(SaveUserEvent createUserEvent);
  UserDeletedEvent deleteUser(DeleteUserEvent deleteUserEvent);
  UserUpdatedEvent updateUser(UpdateUserEvent updateUserEvent);
  UserEvent requestUser(RequestUserEvent requestUserEvent);

}

