package com.usermanager.events.user;

import com.usermanager.events.ReadEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AllUsersEvent extends ReadEvent {

  private final List<UserDetails> users;

  public AllUsersEvent(List<UserDetails> users) {
    this.users = Collections.unmodifiableList(users);
  }

  public Collection<UserDetails> getUserDetails() {
    return this.users;
  }
}
