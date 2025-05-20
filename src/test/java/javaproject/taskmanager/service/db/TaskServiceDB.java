package javaproject.taskmanager.service.db;

import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.service.TaskService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("test")
public class TaskServiceDB implements TaskService {
    
    private final List<Task> tasks = new ArrayList<>();
    
    @Override
    public Task createTask(Task task) {
        // Simulate ID generation
        task.setId((long) (tasks.size() + 1));
        tasks.add(task);
        return task;
    }

    @Override
    public List<Task> getAllUserTasks(Long userId) {
        return tasks.stream()
            .filter(t -> t.getUserId().equals(userId) && !t.isDeleted())
            .sorted((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()))
            .toList();
    }

    @Override
    public List<Task> getPendingUserTasks(Long userId) {
        return tasks.stream()
            .filter(t -> t.getUserId().equals(userId) && !t.isCompleted() && !t.isDeleted())
            .sorted((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()))
            .toList();
    }

    @Override
    public void deleteTask(Long taskId) {
        tasks.stream()
            .filter(t -> t.getId().equals(taskId))
            .findFirst()
            .ifPresent(task -> task.setDeleted(true));
    }
} 