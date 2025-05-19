package javaproject.taskmanager.repository;

import javaproject.taskmanager.model.Task;

import java.util.List;

public interface TaskRepository {
	
    Task save(Task task);
	
    List<Task> findAllByUserId(Long userId);
	
    List<Task> findPendingByUserId(Long userId);
	
    void markDeleted(Long taskId);
	
}