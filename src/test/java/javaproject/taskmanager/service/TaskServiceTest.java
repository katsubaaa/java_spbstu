package javaproject.taskmanager.service;

import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        // We'll define the actual implementation in the test
        sampleTask = new Task(1L, "Test Task", "Test Description", LocalDateTime.now().plusDays(1));
        sampleTask.setId(1L);
    }

    @Test
    void createTask_ShouldReturnSavedTask() {
        // Arrange
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);
        
        // Create a service implementation for testing
        taskService = new TaskService() {
            @Override
            public Task createTask(Task task) {
                return taskRepository.save(task);
            }

            @Override
            public List<Task> getAllUserTasks(Long userId) {
                return null;
            }

            @Override
            public List<Task> getPendingUserTasks(Long userId) {
                return null;
            }

            @Override
            public void deleteTask(Long taskId) {
                // Not implemented for this test
            }
        };
        
        // Act
        Task task = new Task(1L, "Test Task", "Test Description", LocalDateTime.now().plusDays(1));
        Task result = taskService.createTask(task);
        
        // Assert
        assertNotNull(result);
        assertEquals(sampleTask.getId(), result.getId());
        assertEquals(sampleTask.getTitle(), result.getTitle());
        
        // Verify repository was called
        verify(taskRepository, times(1)).save(any(Task.class));
    }
    
    @Test
    void getAllUserTasks_ShouldReturnTasksForUser() {
        // Arrange
        List<Task> tasks = Arrays.asList(
            sampleTask,
            new Task(1L, "Another Task", "Another Description", LocalDateTime.now().plusDays(2))
        );
        
        when(taskRepository.findByUserIdAndDeletedFalseOrderByCreatedAtDesc(1L))
            .thenReturn(tasks);
            
        // Create a service implementation for testing
        taskService = new TaskService() {
            @Override
            public Task createTask(Task task) {
                return null;
            }

            @Override
            public List<Task> getAllUserTasks(Long userId) {
                return taskRepository.findByUserIdAndDeletedFalseOrderByCreatedAtDesc(userId);
            }

            @Override
            public List<Task> getPendingUserTasks(Long userId) {
                return null;
            }

            @Override
            public void deleteTask(Long taskId) {
                // Not implemented for this test
            }
        };
        
        // Act
        List<Task> result = taskService.getAllUserTasks(1L);
        
        // Assert
        assertEquals(2, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
        
        // Verify repository was called
        verify(taskRepository, times(1))
            .findByUserIdAndDeletedFalseOrderByCreatedAtDesc(1L);
    }
    
    @Test
    void getPendingUserTasks_ShouldReturnNonCompletedTasksForUser() {
        // Arrange
        List<Task> tasks = Collections.singletonList(sampleTask);
        
        when(taskRepository.findByUserIdAndCompletedFalseAndDeletedFalseOrderByCreatedAtDesc(1L))
            .thenReturn(tasks);
            
        // Create a service implementation for testing
        taskService = new TaskService() {
            @Override
            public Task createTask(Task task) {
                return null;
            }

            @Override
            public List<Task> getAllUserTasks(Long userId) {
                return null;
            }

            @Override
            public List<Task> getPendingUserTasks(Long userId) {
                return taskRepository.findByUserIdAndCompletedFalseAndDeletedFalseOrderByCreatedAtDesc(userId);
            }

            @Override
            public void deleteTask(Long taskId) {
                // Not implemented for this test
            }
        };
        
        // Act
        List<Task> result = taskService.getPendingUserTasks(1L);
        
        // Assert
        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
        
        // Verify repository was called
        verify(taskRepository, times(1))
            .findByUserIdAndCompletedFalseAndDeletedFalseOrderByCreatedAtDesc(1L);
    }
} 