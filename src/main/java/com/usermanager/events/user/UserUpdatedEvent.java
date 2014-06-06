package com.usermanager.events.user;

import com.usermanager.events.UpdatedEvent;

public class UserUpdatedEvent extends UpdatedEvent {

  private String id;
  private UserDetails userDetails;

  public UserUpdatedEvent(String id, UserDetails userDetails) {
    this.id = id;
    this.userDetails = userDetails;
  }

  public UserUpdatedEvent(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public UserDetails getUserDetails() {
    return userDetails;
  }

  public static UserUpdatedEvent notFound(String id) {
    UserUpdatedEvent ev = new UserUpdatedEvent(id);
    ev.entityFound=false;
    return ev;
  }
}
