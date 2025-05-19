package javaproject.taskmanager.repository;

import javaproject.taskmanager.model.Notification;

import java.util.List;

public interface NotificationRepository {
	
    Notification save(Notification notification);
	
    List<Notification> findAllByUserId(Long userId);
	
    List<Notification> findPendingByUserId(Long userId);
	
}