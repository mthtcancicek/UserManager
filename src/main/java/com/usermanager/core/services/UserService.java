package com.usermanager.core.services;

import com.usermanager.events.user.*;

public interface UserService {

  AllUsersEvent requestAllUsers(RequestAllUsersEvent requestAllCurrentUsersEvent);

  UserSavedEvent saveUser(SaveUserEvent saveUserEvent);

  UserDeletedEvent deleteUser(DeleteUserEvent deleteUserEvent);

  UserUpdatedEvent updateUser(UpdateUserEvent updateUserEvent);

  UserEvent requestUser(RequestUserEvent requestUserEvent);
}
