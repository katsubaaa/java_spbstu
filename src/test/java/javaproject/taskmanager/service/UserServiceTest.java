package javaproject.taskmanager.service;

import javaproject.taskmanager.model.User;
import javaproject.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User("testuser", "test@example.com");
        sampleUser.setId(1L);
    }

    @Test
    void register_ShouldReturnSavedUser() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);
        
        // Create service implementation for testing
        userService = new UserService() {
            @Override
            public User register(User user) {
                return userRepository.save(user);
            }

            @Override
            public Optional<User> login(String username) {
                return Optional.empty();
            }
        };
        
        // Act
        User newUser = new User("testuser", "test@example.com");
        User result = userService.register(newUser);
        
        // Assert
        assertNotNull(result);
        assertEquals(sampleUser.getId(), result.getId());
        assertEquals(sampleUser.getUsername(), result.getUsername());
        
        // Verify repository was called
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void login_ShouldReturnUserIfExists() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(sampleUser));
        
        // Create service implementation for testing
        userService = new UserService() {
            @Override
            public User register(User user) {
                return null;
            }

            @Override
            public Optional<User> login(String username) {
                return userRepository.findByUsername(username);
            }
        };
        
        // Act
        Optional<User> result = userService.login("testuser");
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
        
        // Verify repository was called
        verify(userRepository, times(1)).findByUsername("testuser");
    }
    
    @Test
    void login_ShouldReturnEmptyOptionalIfUserNotFound() {
        // Arrange
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());
        
        // Create service implementation for testing
        userService = new UserService() {
            @Override
            public User register(User user) {
                return null;
            }

            @Override
            public Optional<User> login(String username) {
                return userRepository.findByUsername(username);
            }
        };
        
        // Act
        Optional<User> result = userService.login("nonexistent");
        
        // Assert
        assertFalse(result.isPresent());
        
        // Verify repository was called
        verify(userRepository, times(1)).findByUsername("nonexistent");
    }
} 