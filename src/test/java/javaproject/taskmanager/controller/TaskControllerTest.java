package javaproject.taskmanager.controller;

import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    private TaskController taskController;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        taskController = new TaskController();
        ReflectionTestUtils.setField(taskController, "taskService", taskService);
        
        LocalDateTime targetDate = LocalDateTime.now().plusDays(1);
        task1 = new Task(1L, "Task 1", "Description 1", targetDate);
        task1.setId(1L);
        
        task2 = new Task(1L, "Task 2", "Description 2", targetDate);
        task2.setId(2L);
        task2.setCompleted(true);
    }

    @Test
    void getUserTasks_ShouldReturnTasksFromService() {
        List<Task> expectedTasks = Arrays.asList(task1, task2);
        when(taskService.getAllUserTasks(1L)).thenReturn(expectedTasks);

        ResponseEntity<List<Task>> response = taskController.getUserTasks(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTasks, response.getBody());
        verify(taskService, times(1)).getAllUserTasks(1L);
    }

    @Test
    void getPendingUserTasks_ShouldReturnPendingTasksFromService() {
        List<Task> expectedTasks = Arrays.asList(task1);
        when(taskService.getPendingUserTasks(1L)).thenReturn(expectedTasks);

        ResponseEntity<List<Task>> response = taskController.getPendingUserTasks(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTasks, response.getBody());
        verify(taskService, times(1)).getPendingUserTasks(1L);
    }

    @Test
    void createTask_ShouldReturnCreatedTask() {
        Task newTask = new Task(1L, "New Task", "Description", LocalDateTime.now().plusDays(1));
        when(taskService.createTask(newTask)).thenReturn(newTask);

        ResponseEntity<Task> response = taskController.createTask(newTask);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newTask, response.getBody());
        verify(taskService, times(1)).createTask(newTask);
    }

    @Test
    void deleteTask_ShouldReturnNoContent() {
        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(1L);
    }

} 