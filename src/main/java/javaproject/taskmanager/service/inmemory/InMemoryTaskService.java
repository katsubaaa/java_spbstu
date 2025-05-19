package javaproject.taskmanager.service.inmemory;

import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.repository.inmemory.InMemoryTaskRepository;
import javaproject.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryTaskService implements TaskService{
	
    @Autowired InMemoryTaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllUserTasks(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    public List<Task> getPendingUserTasks(Long userId) {
        return taskRepository.findPendingByUserId(userId);
    }

    public void deleteTask(Long taskId) {
        taskRepository.markDeleted(taskId);
    }
	
}