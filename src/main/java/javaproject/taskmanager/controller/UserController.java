package javaproject.taskmanager.controller;

import javaproject.taskmanager.model.User;
import javaproject.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
	
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.register(user));
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username) {
        return userService.login(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }
	
}