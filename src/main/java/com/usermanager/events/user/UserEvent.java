package com.usermanager.events.user;

import com.usermanager.events.ReadEvent;

public class UserEvent extends ReadEvent {

  private final UserDetails user;

  public UserEvent(UserDetails user) {
    this.user = user;
  }

  public UserDetails getUser() {
    return this.user;
  }
}
