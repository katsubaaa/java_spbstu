package javaproject.taskmanager.repository.inmemory;

import javaproject.taskmanager.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryNotificationRepositoryTest {

    private InMemoryNotificationRepository notificationRepository;

    @BeforeEach
    void setUp() {
        notificationRepository = new InMemoryNotificationRepository();
    }

    @Test
    void saveNotification_ShouldAssignIdAndReturnSavedNotification() {
        Notification notification = new Notification(1L, "Test notification");
        
        Notification savedNotification = notificationRepository.save(notification);
        
        assertNotNull(savedNotification.getId());
        assertEquals(1L, savedNotification.getUserId());
        assertEquals("Test notification", savedNotification.getMessage());
        assertFalse(savedNotification.isRead());
        assertNotNull(savedNotification.getCreatedAt());
    }

    @Test
    void findAllByUserId_ShouldReturnOnlyUserNotifications() {
        Notification notification1 = new Notification(1L, "User 1 notification");
        Notification notification2 = new Notification(2L, "User 2 notification");
        Notification notification3 = new Notification(1L, "Another User 1 notification");
        
        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
        notificationRepository.save(notification3);
        
        List<Notification> userNotifications = notificationRepository.findAllByUserId(1L);
        
        assertEquals(2, userNotifications.size());
        assertTrue(userNotifications.stream().allMatch(notification -> notification.getUserId().equals(1L)));
    }

    @Test
    void findPendingByUserId_ShouldReturnOnlyUnreadUserNotifications() {
        Notification notification1 = new Notification(1L, "Unread notification");
        Notification notification2 = new Notification(1L, "Read notification");
        notification2.setRead(true);
        
        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
        
        List<Notification> pendingNotifications = notificationRepository.findPendingByUserId(1L);
        
        assertEquals(1, pendingNotifications.size());
        assertEquals("Unread notification", pendingNotifications.get(0).getMessage());
        assertFalse(pendingNotifications.get(0).isRead());
    }

} 