package javaproject.taskmanager.service.db;

import javaproject.taskmanager.model.Notification;
import javaproject.taskmanager.repository.NotificationRepository;
import javaproject.taskmanager.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("db")
public class NotificationServiceDB implements NotificationService {
	
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification create(Notification notification) {
        // Method kept for API compatibility, but notifications should now
        // be created via the message broker listener
        throw new UnsupportedOperationException(
            "Direct notification creation is disabled. Notifications are created via message broker only."
        );
    }

    @Override
    public List<Notification> getAllByUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getPendingByUser(Long userId) {
        return notificationRepository.findByUserIdAndReadFalse(userId);
    }
	
} 