package javaproject.taskmanager.repository.inmemory;

import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idCounter.getAndIncrement());
        }
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        return tasks.values().stream()
                .filter(task -> task.getUserId().equals(userId))
                .filter(task -> !task.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findPendingByUserId(Long userId) {
        return tasks.values().stream()
                .filter(task -> task.getUserId().equals(userId))
                .filter(task -> !task.isDeleted())
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }

    @Override
    public void markDeleted(Long taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.setDeleted(true);
            tasks.put(taskId, task);
        }
    }
    
} 