package javaproject.taskmanager.repository.inmemory;

import javaproject.taskmanager.model.Task;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@Profile("inmemory")
public class InMemoryTaskRepository {

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idCounter.getAndIncrement());
        }
        tasks.put(task.getId(), task);
        return task;
    }

    public List<Task> findAllByUserId(Long userId) {
        return tasks.values().stream()
                .filter(task -> task.getUserId().equals(userId))
                .filter(task -> !task.isDeleted())
                .collect(Collectors.toList());
    }

    public List<Task> findPendingByUserId(Long userId) {
        return tasks.values().stream()
                .filter(task -> task.getUserId().equals(userId))
                .filter(task -> !task.isDeleted())
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }

    public void markDeleted(Long taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.setDeleted(true);
            tasks.put(taskId, task);
        }
    }
    
    /**
     * Find a task by its ID
     * @param id the ID of the task to find
     * @return an Optional containing the task if found, or empty if not found
     */
    public Optional<Task> findById(Long id) {
        Task task = tasks.get(id);
        return Optional.ofNullable(task);
    }
} 