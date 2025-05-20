package javaproject.taskmanager.service.inmemory;

import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.repository.inmemory.InMemoryTaskRepository;
import javaproject.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Profile("inmemory")
public class InMemoryTaskService implements TaskService{
	
    @Autowired InMemoryTaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllUserTasks(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    public List<Task> getPendingUserTasks(Long userId) {
        return taskRepository.findPendingByUserId(userId);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.markDeleted(taskId);
    }
    
    @Override
    @Async("taskExecutor")
    public CompletableFuture<Task> completeTaskAsync(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found: " + taskId));
        
        task.setCompleted(true);
        Task updatedTask = taskRepository.save(task);
        
        return CompletableFuture.completedFuture(updatedTask);
    }
	
}