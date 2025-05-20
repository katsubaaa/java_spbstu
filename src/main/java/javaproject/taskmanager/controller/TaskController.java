package javaproject.taskmanager.controller;

import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	
    @Autowired
    private TaskService taskService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getUserTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getAllUserTasks(userId));
    }

    @GetMapping("/user/{userId}/pending")
    public ResponseEntity<List<Task>> getPendingUserTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getPendingUserTasks(userId));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Void> completeTaskAsync(@PathVariable Long taskId) {
        taskService.completeTaskAsync(taskId);
        // Return 202 Accepted since this is an async operation
        return ResponseEntity.accepted().build();
    }
}