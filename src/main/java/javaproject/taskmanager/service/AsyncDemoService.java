package javaproject.taskmanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AsyncDemoService {

    /**
     * Asynchronously processes a task with a simulated delay
     * @param taskName The name of the task to process
     * @return A CompletableFuture containing the result message
     */
    @Async("taskExecutor")
    public CompletableFuture<String> processTaskAsync(String taskName) {
        log.info("Starting async processing of task: {}", taskName);
        
        try {
            // Simulate some long-running task
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(e);
        }
        
        String result = "Task '" + taskName + "' processed successfully!";
        log.info(result);
        
        return CompletableFuture.completedFuture(result);
    }
} 