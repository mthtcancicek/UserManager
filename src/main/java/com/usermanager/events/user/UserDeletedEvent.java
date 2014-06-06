package com.usermanager.events.user;

import com.usermanager.events.DeletedEvent;

public class UserDeletedEvent extends DeletedEvent {

  private UserDetails details;
  private boolean deletionCompleted;
  private String id;

  private UserDeletedEvent(String id) {
    this.id = id;
  }

  public UserDeletedEvent(String id, UserDetails details) {
    this.id = id;
    this.details = details;
    this.deletionCompleted = true;
  }

  public String getId() {
    return id;
  }

  public UserDetails getDetails() {
    return details;
  }

  public boolean isDeletionCompleted() {
    return deletionCompleted;
  }

  public static UserDeletedEvent deletionForbidden(String id, UserDetails details) {
    UserDeletedEvent ev = new UserDeletedEvent(id, details);
    ev.entityFound=true;
    ev.deletionCompleted=false;
    return ev;
  }

  public static UserDeletedEvent notFound(String id) {
    UserDeletedEvent ev = new UserDeletedEvent(id);
    ev.entityFound=false;
    return ev;
  }
}
