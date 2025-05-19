package javaproject.taskmanager.repository.inmemory;

import javaproject.taskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskRepositoryTest {

    private InMemoryTaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository = new InMemoryTaskRepository();
    }

    @Test
    void saveTask_ShouldAssignIdAndReturnSavedTask() {
        LocalDateTime targetDate = LocalDateTime.now().plusDays(1);
        Task task = new Task(1L, "Test Task", "Description", targetDate);
        
        Task savedTask = taskRepository.save(task);
        
        assertNotNull(savedTask.getId());
        assertEquals(1L, savedTask.getUserId());
        assertEquals("Test Task", savedTask.getTitle());
        assertEquals("Description", savedTask.getDescription());
        assertEquals(targetDate, savedTask.getTargetDate());
    }

    @Test
    void findAllByUserId_ShouldReturnOnlyUserTasks() {
        LocalDateTime targetDate = LocalDateTime.now().plusDays(1);
        Task task1 = new Task(1L, "User 1 Task", "Description", targetDate);
        Task task2 = new Task(2L, "User 2 Task", "Description", targetDate);
        Task task3 = new Task(1L, "Another User 1 Task", "Description", targetDate);
        
        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        
        List<Task> userTasks = taskRepository.findAllByUserId(1L);
        
        assertEquals(2, userTasks.size());
        assertTrue(userTasks.stream().allMatch(task -> task.getUserId().equals(1L)));
    }

    @Test
    void findPendingByUserId_ShouldReturnOnlyPendingUserTasks() {
        LocalDateTime targetDate = LocalDateTime.now().plusDays(1);
        Task task1 = new Task(1L, "Pending Task", "Description", targetDate);
        Task task2 = new Task(1L, "Completed Task", "Description", targetDate);
        task2.setCompleted(true);
        Task task3 = new Task(1L, "Deleted Task", "Description", targetDate);
        task3.setDeleted(true);
        
        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        
        List<Task> pendingTasks = taskRepository.findPendingByUserId(1L);
        
        assertEquals(1, pendingTasks.size());
        assertEquals("Pending Task", pendingTasks.get(0).getTitle());
    }

    @Test
    void markDeleted_ShouldSetTaskAsDeleted() {
        LocalDateTime targetDate = LocalDateTime.now().plusDays(1);
        Task task = new Task(1L, "Test Task", "Description", targetDate);
        Task savedTask = taskRepository.save(task);
        
        taskRepository.markDeleted(savedTask.getId());
        
        List<Task> tasks = taskRepository.findAllByUserId(1L);
        assertTrue(tasks.isEmpty());
    }
    
} 