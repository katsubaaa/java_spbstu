package javaproject.taskmanager.service;

import javaproject.taskmanager.model.User;

import java.util.Optional;

public interface UserService {

    User register(User user);

    Optional<User> login(String username);
	
}