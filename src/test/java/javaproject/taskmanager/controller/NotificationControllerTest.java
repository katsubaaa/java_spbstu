package javaproject.taskmanager.controller;

import javaproject.taskmanager.model.Notification;
import javaproject.taskmanager.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    private NotificationController notificationController;

    private Notification notification1;
    private Notification notification2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        notificationController = new NotificationController(notificationService);
        
        notification1 = new Notification(1L, "Notification 1");
        notification1.setId(1L);
        notification1.setCreatedAt(LocalDateTime.now());
        
        notification2 = new Notification(1L, "Notification 2");
        notification2.setId(2L);
        notification2.setCreatedAt(LocalDateTime.now());
        notification2.setRead(true);
    }

    @Test
    void getAll_ShouldReturnAllNotifications() {
        List<Notification> expectedNotifications = Arrays.asList(notification1, notification2);
        when(notificationService.getAllByUser(1L)).thenReturn(expectedNotifications);

        ResponseEntity<List<Notification>> response = notificationController.getAll(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedNotifications, response.getBody());
        verify(notificationService, times(1)).getAllByUser(1L);
    }

    @Test
    void getPending_ShouldReturnPendingNotifications() {
        List<Notification> expectedNotifications = Arrays.asList(notification1);
        when(notificationService.getPendingByUser(1L)).thenReturn(expectedNotifications);

        ResponseEntity<List<Notification>> response = notificationController.getPending(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedNotifications, response.getBody());
        verify(notificationService, times(1)).getPendingByUser(1L);
    }

    @Test
    void create_ShouldReturnCreatedNotification() {
        Notification newNotification = new Notification(1L, "New Notification");
        when(notificationService.create(newNotification)).thenReturn(newNotification);

        ResponseEntity<Notification> response = notificationController.create(newNotification);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newNotification, response.getBody());
        verify(notificationService, times(1)).create(newNotification);
    }

} 