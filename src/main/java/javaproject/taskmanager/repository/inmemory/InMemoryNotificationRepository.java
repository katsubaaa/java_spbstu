package javaproject.taskmanager.repository.inmemory;

import javaproject.taskmanager.model.Notification;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@Profile("inmemory")
public class InMemoryNotificationRepository {

    private final Map<Long, Notification> notifications = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Notification save(Notification notification) {
        if (notification.getId() == null) {
            notification.setId(idCounter.getAndIncrement());
        }
        notifications.put(notification.getId(), notification);
        return notification;
    }

    public List<Notification> findAllByUserId(Long userId) {
        return notifications.values().stream()
                .filter(notification -> notification.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Notification> findPendingByUserId(Long userId) {
        return notifications.values().stream()
                .filter(notification -> notification.getUserId().equals(userId))
                .filter(notification -> !notification.isRead())
                .collect(Collectors.toList());
    }
    
}