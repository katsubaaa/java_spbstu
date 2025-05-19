package javaproject.taskmanager.service.inmemory;

import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.repository.inmemory.InMemoryTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InMemoryTaskServiceTest {

    @Mock
    private InMemoryTaskRepository taskRepository;

    @InjectMocks
    private InMemoryTaskService taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        LocalDateTime targetDate = LocalDateTime.now().plusDays(1);
        task1 = new Task(1L, "Task 1", "Description 1", targetDate);
        task1.setId(1L);
        
        task2 = new Task(1L, "Task 2", "Description 2", targetDate);
        task2.setId(2L);
    }

    @Test
    void createTask_ShouldDelegateToRepositoryAndReturnResult() {
        Task newTask = new Task(1L, "New Task", "Description", LocalDateTime.now().plusDays(1));
        when(taskRepository.save(newTask)).thenReturn(newTask);

        Task createdTask = taskService.createTask(newTask);

        assertEquals(newTask, createdTask);
        verify(taskRepository, times(1)).save(newTask);
    }

    @Test
    void getAllUserTasks_ShouldDelegateToRepositoryAndReturnResult() {
        List<Task> expectedTasks = Arrays.asList(task1, task2);
        when(taskRepository.findAllByUserId(1L)).thenReturn(expectedTasks);

        List<Task> actualTasks = taskService.getAllUserTasks(1L);

        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void getPendingUserTasks_ShouldDelegateToRepositoryAndReturnResult() {
        List<Task> expectedTasks = Arrays.asList(task1);
        when(taskRepository.findPendingByUserId(1L)).thenReturn(expectedTasks);

        List<Task> actualTasks = taskService.getPendingUserTasks(1L);

        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository, times(1)).findPendingByUserId(1L);
    }

    @Test
    void deleteTask_ShouldDelegateToRepository() {
        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).markDeleted(1L);
    }

} 