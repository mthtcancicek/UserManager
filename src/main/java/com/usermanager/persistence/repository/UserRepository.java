package com.usermanager.persistence.repository;

import com.usermanager.persistence.domain.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
	public User findByUserName(String userName);
}
