package javaproject.taskmanager.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
	
    private Long id;
    
    private Long userId;
    
    private String title;
    
    private String description;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime targetDate;
    
    private boolean completed;
    
    private boolean deleted;

    public Task(Long userId, String title, String description, LocalDateTime targetDate) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.targetDate = targetDate;
        this.completed = false;
        this.deleted = false;
    }

    public String getSummary() {
        return String.format("Task #%d [%s] - %s | Due: %s | Status: %s", 
            id != null ? id : 0,
            title,
            description,
            targetDate,
            completed ? "Completed" : "Pending"
        );
    }

    public void setDone(boolean done) {
        this.completed = done;
    }
    
}