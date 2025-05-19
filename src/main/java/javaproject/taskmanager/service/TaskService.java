package javaproject.taskmanager.service;

import javaproject.taskmanager.model.Task;

import java.util.List;

public interface TaskService {
	
    Task createTask(Task task);
	
    List<Task> getAllUserTasks(Long userId);
	
    List<Task> getPendingUserTasks(Long userId);
	
    void deleteTask(Long taskId);
	
}