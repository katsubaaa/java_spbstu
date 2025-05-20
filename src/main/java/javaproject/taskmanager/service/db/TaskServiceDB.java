package javaproject.taskmanager.service.db;

import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.repository.TaskRepository;
import javaproject.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("db")
public class TaskServiceDB implements TaskService {
	
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllUserTasks(Long userId) {
        return taskRepository.findByUserIdAndDeletedFalseOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Task> getPendingUserTasks(Long userId) {
        return taskRepository.findByUserIdAndCompletedFalseAndDeletedFalseOrderByCreatedAtDesc(userId);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.markDeleted(taskId);
    }
	
} 