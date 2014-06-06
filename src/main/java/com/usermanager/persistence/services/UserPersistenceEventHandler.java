package com.usermanager.persistence.services;

import java.util.ArrayList;
import java.util.List;

import com.usermanager.events.user.AllUsersEvent;
import com.usermanager.events.user.DeleteUserEvent;
import com.usermanager.events.user.RequestAllUsersEvent;
import com.usermanager.events.user.RequestUserEvent;
import com.usermanager.events.user.SaveUserEvent;
import com.usermanager.events.user.UpdateUserEvent;
import com.usermanager.events.user.UserDeletedEvent;
import com.usermanager.events.user.UserDetails;
import com.usermanager.events.user.UserEvent;
import com.usermanager.events.user.UserSavedEvent;
import com.usermanager.events.user.UserUpdatedEvent;
import com.usermanager.persistence.domain.User;
import com.usermanager.persistence.repository.UserRepository;

public class UserPersistenceEventHandler implements UserPersistenceService {

  private UserRepository userRepository;

  public UserPersistenceEventHandler(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public AllUsersEvent requestAllUsers(RequestAllUsersEvent requestAllUsersEvent) {
    Iterable<User> users = userRepository.findAll();
    
    List<UserDetails> details = new ArrayList<UserDetails>();

    for(User user : users) {
    	details.add(user.toUserDetails());
    }

    return new AllUsersEvent(details);
  }

	@Override
	public UserSavedEvent saveUser(SaveUserEvent saveUserEvent) {
	    User user = User.fromUserDetails(saveUserEvent.getDetails());

	    user = userRepository.save(user);

	    return new UserSavedEvent(user.getId(), user.toUserDetails());
	}
	
	@Override
	public UserDeletedEvent deleteUser(DeleteUserEvent deleteUserEvent) {
	    User user = userRepository.findOne(deleteUserEvent.getId());

	    //TODO Handle this response
	    if (user == null) {
	      return UserDeletedEvent.notFound(deleteUserEvent.getId());
	    }

	    userRepository.delete(user.getId());

	    return new UserDeletedEvent(deleteUserEvent.getId(), user.toUserDetails());
	}
	
	@Override
	public UserUpdatedEvent updateUser(UpdateUserEvent updateUserEvent) {
	    User user = userRepository.findOne(updateUserEvent.getDetails().getId());

	    if (user == null) {
	      return UserUpdatedEvent.notFound(updateUserEvent.getDetails().getId());
	    }

	    userRepository.save(User.fromUserDetails(updateUserEvent.getDetails()));

	    return new UserUpdatedEvent(updateUserEvent.getDetails().getId(), user.toUserDetails());
	}
	
	@Override
	public UserEvent requestUser(RequestUserEvent requestUserEvent) {
		User user = userRepository.findByUserName(requestUserEvent.getUserName());
		return new UserEvent(user.toUserDetails());
	}

}
