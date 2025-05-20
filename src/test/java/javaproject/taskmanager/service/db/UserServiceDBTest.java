package javaproject.taskmanager.service.db;

import javaproject.taskmanager.model.User;
import javaproject.taskmanager.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Profile("test")
public class UserServiceDBTest implements UserService {
    
    private final List<User> users = new ArrayList<>();
    
    @Override
    public User register(User user) {
        // Simulate ID generation
        user.setId((long) (users.size() + 1));
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> login(String username) {
        return users.stream()
            .filter(u -> u.getUsername().equals(username))
            .findFirst();
    }
} 