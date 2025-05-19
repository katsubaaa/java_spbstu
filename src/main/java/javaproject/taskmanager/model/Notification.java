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
public class Notification {
	
    private Long id;
    
    private Long userId;
    
    private String message;
    
    private boolean read;
    
    private LocalDateTime createdAt;

    public Notification(Long userId, String message) {
        this.userId = userId;
        this.message = message;
        this.createdAt = LocalDateTime.now();
        this.read = false;
    }

    public String getMessageText() {
        return String.format("[%s] %s", 
            createdAt != null ? createdAt.toString() : "Unknown time",
            message
        );
    }
    
}