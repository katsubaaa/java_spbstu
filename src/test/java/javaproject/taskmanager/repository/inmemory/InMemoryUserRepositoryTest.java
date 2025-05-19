package javaproject.taskmanager.repository.inmemory;

import javaproject.taskmanager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    void saveUser_ShouldAssignIdAndReturnSavedUser() {
        User user = new User("testuser", "test@example.com");
        
        User savedUser = userRepository.save(user);
        
        assertNotNull(savedUser.getId());
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("test@example.com", savedUser.getEmail());
    }

    @Test
    void findByUsername_ExistingUser_ShouldReturnUser() {
        User user = new User("testuser", "test@example.com");
        userRepository.save(user);
        
        Optional<User> foundUser = userRepository.findByUsername("testuser");
        
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }
    
    @Test
    void findByUsername_NonExistentUser_ShouldReturnEmpty() {
        Optional<User> foundUser = userRepository.findByUsername("nonexistentuser");
        
        assertFalse(foundUser.isPresent());
    }

} 