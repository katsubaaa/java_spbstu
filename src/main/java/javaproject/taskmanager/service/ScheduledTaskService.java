package javaproject.taskmanager.service;

import javaproject.taskmanager.model.Notification;
import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.repository.NotificationRepository;
import javaproject.taskmanager.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ScheduledTaskService {

    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * Scheduled task that checks for overdue tasks every hour
     * and creates notifications for them
     */
    @Scheduled(fixedRate = 3600000) // Run every hour (3600000 ms)
    public void checkOverdueTasks() {
        log.info("Running scheduled check for overdue tasks");
        
        LocalDateTime now = LocalDateTime.now();
        
        // Find tasks with target dates in the past and not completed
        List<Task> overdueTasks = findOverdueTasks(now);
        log.info("Found {} overdue tasks", overdueTasks.size());
        
        // Process each overdue task asynchronously
        overdueTasks.forEach(this::processOverdueTask);
    }
    
    /**
     * Finds all tasks that are overdue (target date is in the past,
     * not completed, and not deleted)
     */
    private List<Task> findOverdueTasks(LocalDateTime now) {
        return taskRepository.findByCompletedFalseAndDeletedFalseAndTargetDateBefore(now);
    }
    
    /**
     * Process an overdue task asynchronously by creating a notification
     */
    @Async("taskExecutor")
    public void processOverdueTask(Task task) {
        log.info("Processing overdue task: {}", task.getId());
        
        // Create notification for the overdue task
        String message = String.format("Task '%s' is overdue (Due: %s)", 
                task.getTitle(), 
                task.getTargetDate());
        
        Notification notification = new Notification(task.getUserId(), message);
        notificationRepository.save(notification);
        
        log.info("Created notification for overdue task: {}", task.getId());
    }
} 