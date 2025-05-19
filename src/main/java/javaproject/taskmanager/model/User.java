package javaproject.taskmanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
    private Long id;
    
    private String username;
    
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getDisplayName() {
        return String.format("%s <%s>", username, email);
    }
    
}