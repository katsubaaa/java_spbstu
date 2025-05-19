package javaproject.taskmanager.service;

import javaproject.taskmanager.model.Notification;

import java.util.List;

public interface NotificationService {
	
    Notification create(Notification notification);

    List<Notification> getAllByUser(Long userId);

    List<Notification> getPendingByUser(Long userId);
	
}