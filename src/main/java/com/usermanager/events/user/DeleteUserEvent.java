package com.usermanager.events.user;

import com.usermanager.events.DeleteEvent;


public class DeleteUserEvent extends DeleteEvent {

  private String id;

  public DeleteUserEvent(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
