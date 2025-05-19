package javaproject.taskmanager.service.inmemory;

import javaproject.taskmanager.model.User;
import javaproject.taskmanager.repository.inmemory.InMemoryUserRepository;
import javaproject.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InMemoryUserService implements UserService {
	
    @Autowired InMemoryUserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }

    public Optional<User> login(String username) {
        return userRepository.findByUsername(username);
    }
	
}