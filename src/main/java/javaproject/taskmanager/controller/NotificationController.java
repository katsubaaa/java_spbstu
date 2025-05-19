package javaproject.taskmanager.controller;

import javaproject.taskmanager.model.Notification;
import javaproject.taskmanager.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.create(notification));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getAllUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getAllByUser(userId));
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getPendingByUser(userId));
    }
}