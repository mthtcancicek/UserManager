package com.usermanager.events.user;

import com.usermanager.events.CreatedEvent;

public class UserSavedEvent extends CreatedEvent {

  private final String id;
  private final UserDetails details;

  public UserSavedEvent(final String id, final UserDetails details) {
	  this.id = id;
	  this.details = details;
  }

  public UserDetails getUserDetails() {
    return details;
  }

  public String getId() {
    return id;
  }
}
