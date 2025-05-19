package javaproject.taskmanager.service.h2;

import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.repository.TaskRepository;
import javaproject.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("db")
public class TaskServiceH2 implements TaskService {
	
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllUserTasks(Long userId) {
        return taskRepository.findByUserIdAndDeletedFalse(userId);
    }

    @Override
    public List<Task> getPendingUserTasks(Long userId) {
        return taskRepository.findByUserIdAndDeletedFalseAndCompletedFalse(userId);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.markDeleted(taskId);
    }
	
} 