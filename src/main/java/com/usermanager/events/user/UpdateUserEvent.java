package com.usermanager.events.user;

import com.usermanager.events.UpdateEvent;

public class UpdateUserEvent extends UpdateEvent {
  private UserDetails details;

  public UpdateUserEvent(UserDetails details) {
	  this.details = details;
  }

  public UserDetails getDetails() {
    return details;
  }
}
