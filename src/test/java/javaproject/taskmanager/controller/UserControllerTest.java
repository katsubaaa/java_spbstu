package javaproject.taskmanager.controller;

import javaproject.taskmanager.model.User;
import javaproject.taskmanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        userController = new UserController(userService);
    }

    @Test
    void register_ShouldReturnCreatedUser() {
        User user = new User("testuser", "test@example.com");
        User savedUser = new User("testuser", "test@example.com");
        savedUser.setId(1L);
        
        when(userService.register(user)).thenReturn(savedUser);

        ResponseEntity<User> response = userController.register(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("testuser", response.getBody().getUsername());
        assertEquals("test@example.com", response.getBody().getEmail());
        verify(userService, times(1)).register(user);
    }

    @Test
    void login_ExistingUser_ShouldReturnUser() {
        String username = "testuser";
        User user = new User(username, "test@example.com");
        user.setId(1L);
        
        when(userService.login(username)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.login(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals(username, response.getBody().getUsername());
        assertEquals("test@example.com", response.getBody().getEmail());
        verify(userService, times(1)).login(username);
    }

    @Test
    void login_NonExistentUser_ShouldReturnNotFound() {
        String username = "nonexistent";
        
        when(userService.login(username)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.login(username);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).login(username);
    }

} 