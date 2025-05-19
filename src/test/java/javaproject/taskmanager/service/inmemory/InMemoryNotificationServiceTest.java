package javaproject.taskmanager.service.inmemory;

import javaproject.taskmanager.model.Notification;
import javaproject.taskmanager.repository.inmemory.InMemoryNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InMemoryNotificationServiceTest {

    @Mock
    private InMemoryNotificationRepository notificationRepository;

    @InjectMocks
    private InMemoryNotificationService notificationService;

    private Notification notification1;
    private Notification notification2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        notification1 = new Notification(1L, "Notification 1");
        notification1.setId(1L);
        notification1.setCreatedAt(LocalDateTime.now());
        
        notification2 = new Notification(1L, "Notification 2");
        notification2.setId(2L);
        notification2.setCreatedAt(LocalDateTime.now());
        notification2.setRead(true);
    }

    @Test
    void create_ShouldDelegateToRepositoryAndReturnResult() {
        Notification newNotification = new Notification(1L, "New Notification");
        when(notificationRepository.save(newNotification)).thenReturn(newNotification);

        Notification createdNotification = notificationService.create(newNotification);

        assertEquals(newNotification, createdNotification);
        verify(notificationRepository, times(1)).save(newNotification);
    }

    @Test
    void getAllByUser_ShouldDelegateToRepositoryAndReturnResult() {
        List<Notification> expectedNotifications = Arrays.asList(notification1, notification2);
        when(notificationRepository.findAllByUserId(1L)).thenReturn(expectedNotifications);

        List<Notification> actualNotifications = notificationService.getAllByUser(1L);

        assertEquals(expectedNotifications, actualNotifications);
        verify(notificationRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void getPendingByUser_ShouldDelegateToRepositoryAndReturnResult() {
        List<Notification> expectedNotifications = Arrays.asList(notification1);
        when(notificationRepository.findPendingByUserId(1L)).thenReturn(expectedNotifications);

        List<Notification> actualNotifications = notificationService.getPendingByUser(1L);

        assertEquals(expectedNotifications, actualNotifications);
        verify(notificationRepository, times(1)).findPendingByUserId(1L);
    }

} 