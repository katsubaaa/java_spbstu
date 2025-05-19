package javaproject.taskmanager.service.inmemory;

import javaproject.taskmanager.model.Notification;
import javaproject.taskmanager.repository.inmemory.InMemoryNotificationRepository;
import javaproject.taskmanager.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryNotificationService implements NotificationService{
	
    @Autowired InMemoryNotificationRepository notificationRepository;

    public Notification create(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllByUser(Long userId) {
        return notificationRepository.findAllByUserId(userId);
    }

    public List<Notification> getPendingByUser(Long userId) {
        return notificationRepository.findPendingByUserId(userId);
    }
	
}