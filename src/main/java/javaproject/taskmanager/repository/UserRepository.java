package javaproject.taskmanager.repository;

import javaproject.taskmanager.model.User;

import java.util.Optional;

public interface UserRepository {
	
    User save(User user);
	
    Optional<User> findByUsername(String username);
	
}