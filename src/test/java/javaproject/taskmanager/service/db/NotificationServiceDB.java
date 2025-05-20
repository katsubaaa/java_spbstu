package javaproject.taskmanager.service.db;

import javaproject.taskmanager.model.Notification;
import javaproject.taskmanager.service.NotificationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("test")
public class NotificationServiceDB implements NotificationService {
    
    private final List<Notification> notifications = new ArrayList<>();
    
    @Override
    public Notification create(Notification notification) {
        // Simulate ID generation
        notification.setId((long) (notifications.size() + 1));
        notifications.add(notification);
        return notification;
    }

    @Override
    public List<Notification> getAllByUser(Long userId) {
        return notifications.stream()
            .filter(n -> n.getUserId().equals(userId))
            .toList();
    }

    @Override
    public List<Notification> getPendingByUser(Long userId) {
        return notifications.stream()
            .filter(n -> n.getUserId().equals(userId) && !n.isRead())
            .toList();
    }
} 