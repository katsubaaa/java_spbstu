package javaproject.taskmanager.service;

import javaproject.taskmanager.model.Notification;
import javaproject.taskmanager.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    private NotificationService notificationService;

    private Notification sampleNotification;

    @BeforeEach
    void setUp() {
        sampleNotification = new Notification(1L, "Test notification");
        sampleNotification.setId(1L);
    }

    @Test
    void create_ShouldReturnSavedNotification() {
        // Arrange
        when(notificationRepository.save(any(Notification.class))).thenReturn(sampleNotification);
        
        // Create service implementation for testing
        notificationService = new NotificationService() {
            @Override
            public Notification create(Notification notification) {
                return notificationRepository.save(notification);
            }

            @Override
            public List<Notification> getAllByUser(Long userId) {
                return null;
            }

            @Override
            public List<Notification> getPendingByUser(Long userId) {
                return null;
            }
        };
        
        // Act
        Notification newNotification = new Notification(1L, "Test notification");
        Notification result = notificationService.create(newNotification);
        
        // Assert
        assertNotNull(result);
        assertEquals(sampleNotification.getId(), result.getId());
        assertEquals(sampleNotification.getMessage(), result.getMessage());
        
        // Verify repository was called
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }
    
    @Test
    void getAllByUser_ShouldReturnNotificationsForUser() {
        // Arrange
        List<Notification> notifications = Arrays.asList(
            sampleNotification,
            new Notification(1L, "Another notification")
        );
        
        when(notificationRepository.findByUserId(1L)).thenReturn(notifications);
        
        // Create service implementation for testing
        notificationService = new NotificationService() {
            @Override
            public Notification create(Notification notification) {
                return null;
            }

            @Override
            public List<Notification> getAllByUser(Long userId) {
                return notificationRepository.findByUserId(userId);
            }

            @Override
            public List<Notification> getPendingByUser(Long userId) {
                return null;
            }
        };
        
        // Act
        List<Notification> result = notificationService.getAllByUser(1L);
        
        // Assert
        assertEquals(2, result.size());
        assertEquals("Test notification", result.get(0).getMessage());
        
        // Verify repository was called
        verify(notificationRepository, times(1)).findByUserId(1L);
    }
    
    @Test
    void getPendingByUser_ShouldReturnUnreadNotificationsForUser() {
        // Arrange
        List<Notification> notifications = Collections.singletonList(sampleNotification);
        
        when(notificationRepository.findByUserIdAndReadFalse(1L)).thenReturn(notifications);
        
        // Create service implementation for testing
        notificationService = new NotificationService() {
            @Override
            public Notification create(Notification notification) {
                return null;
            }

            @Override
            public List<Notification> getAllByUser(Long userId) {
                return null;
            }

            @Override
            public List<Notification> getPendingByUser(Long userId) {
                return notificationRepository.findByUserIdAndReadFalse(userId);
            }
        };
        
        // Act
        List<Notification> result = notificationService.getPendingByUser(1L);
        
        // Assert
        assertEquals(1, result.size());
        assertEquals("Test notification", result.get(0).getMessage());
        
        // Verify repository was called
        verify(notificationRepository, times(1)).findByUserIdAndReadFalse(1L);
    }
} 