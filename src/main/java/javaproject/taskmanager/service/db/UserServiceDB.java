package javaproject.taskmanager.service.db;

import javaproject.taskmanager.model.User;
import javaproject.taskmanager.repository.UserRepository;
import javaproject.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Profile("db")
public class UserServiceDB implements UserService {
	
    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> login(String username) {
        return userRepository.findByUsername(username);
    }
	
} 