package javaproject.taskmanager.service;

import javaproject.taskmanager.model.Task;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TaskService {
	
    Task createTask(Task task);
	
    List<Task> getAllUserTasks(Long userId);
	
    List<Task> getPendingUserTasks(Long userId);
	
    void deleteTask(Long taskId);
    
    /**
     * Asynchronously marks a task as completed
     * @param taskId the ID of the task to mark as completed
     * @return a CompletableFuture that will contain the updated task
     */
    CompletableFuture<Task> completeTaskAsync(Long taskId);
	
}