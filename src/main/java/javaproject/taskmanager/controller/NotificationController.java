package javaproject.taskmanager.controller;

import javaproject.taskmanager.model.Notification;
import javaproject.taskmanager.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getAll(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getAllByUser(userId));
    }

    @GetMapping("/{userId}/pending")
    public ResponseEntity<List<Notification>> getPending(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getPendingByUser(userId));
    }

    @PostMapping
    public ResponseEntity<Notification> create(@RequestBody Notification notification) {
        return ResponseEntity.status(201).body(notificationService.create(notification));
    }
	
}