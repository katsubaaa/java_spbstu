package javaproject.taskmanager.service.inmemory;

import javaproject.taskmanager.model.User;
import javaproject.taskmanager.repository.inmemory.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryUserServiceTest {

    @Mock
    private InMemoryUserRepository userRepository;
    
    @InjectMocks
    private InMemoryUserService userService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void register_ShouldSaveUserAndReturnResult() {
        User user = new User("testuser", "test@example.com");
        User savedUser = new User("testuser", "test@example.com");
        savedUser.setId(1L);
        
        when(userRepository.save(user)).thenReturn(savedUser);
        
        User result = userService.register(user);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
    }
    
    @Test
    void login_ExistingUser_ShouldReturnUser() {
        String username = "testuser";
        User user = new User(username, "test@example.com");
        user.setId(1L);
        
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        
        Optional<User> result = userService.login(username);
        
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals(username, result.get().getUsername());
        assertEquals("test@example.com", result.get().getEmail());
        verify(userRepository, times(1)).findByUsername(username);
    }
    
    @Test
    void login_NonExistentUser_ShouldReturnEmpty() {
        String username = "nonexistent";
        
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        
        Optional<User> result = userService.login(username);
        
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByUsername(username);
    }

} 