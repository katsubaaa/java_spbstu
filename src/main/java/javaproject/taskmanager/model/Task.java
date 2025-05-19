package javaproject.taskmanager.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "target_date")
    private LocalDateTime targetDate;
    
    @Column(nullable = false)
    private boolean completed;
    
    @Column(nullable = false)
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