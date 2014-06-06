package com.usermanager.events.user;

import com.usermanager.events.CreateEvent;

public class SaveUserEvent extends CreateEvent {
  private UserDetails details;

  public SaveUserEvent(UserDetails details) {
    this.details = details;    
    this.details.setId(null);//TODO
  }

  public UserDetails getDetails() {
    return details;
  }
}
