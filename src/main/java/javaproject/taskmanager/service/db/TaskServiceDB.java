package javaproject.taskmanager.service.db;

import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.repository.TaskRepository;
import javaproject.taskmanager.service.MessagingService;
import javaproject.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("db")
public class TaskServiceDB implements TaskService {
	
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private MessagingService messagingService;

    @Override
    @CacheEvict(value = "tasks", key = "'user_' + #task.userId")
    public Task createTask(Task task) {
        Task savedTask = taskRepository.save(task);
        messagingService.sendTaskCreatedMessage(savedTask);
        return savedTask;
    }

    @Override
    @Cacheable(value = "tasks", key = "'user_' + #userId")
    public List<Task> getAllUserTasks(Long userId) {
        return taskRepository.findByUserIdAndDeletedFalseOrderByCreatedAtDesc(userId);
    }

    @Override
    @Cacheable(value = "tasks", key = "'pending_user_' + #userId")
    public List<Task> getPendingUserTasks(Long userId) {
        return taskRepository.findByUserIdAndCompletedFalseAndDeletedFalseOrderByCreatedAtDesc(userId);
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public void deleteTask(Long taskId) {
        taskRepository.markDeleted(taskId);
    }
	
} 