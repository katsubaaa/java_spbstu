package javaproject.taskmanager.config;

import javaproject.taskmanager.model.User;
import javaproject.taskmanager.model.Task;
import javaproject.taskmanager.model.Notification;
import javaproject.taskmanager.repository.UserRepository;
import javaproject.taskmanager.repository.TaskRepository;
import javaproject.taskmanager.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Configuration
@Profile("db")
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            @Autowired UserRepository userRepository,
            @Autowired TaskRepository taskRepository,
            @Autowired NotificationRepository notificationRepository) {
        
        return args -> {
            // Create sample users
            User user1 = userRepository.save(User.builder()
                    .username("user1")
                    .email("user1@example.com")
                    .build());
            
            User user2 = userRepository.save(User.builder()
                    .username("user2")
                    .email("user2@example.com")
                    .build());
            
            // Create sample tasks
            Task task1 = taskRepository.save(Task.builder()
                    .userId(user1.getId())
                    .title("Complete Java Project")
                    .description("Finish the task manager application")
                    .createdAt(LocalDateTime.now())
                    .targetDate(LocalDateTime.now().plusDays(7))
                    .completed(false)
                    .deleted(false)
                    .build());
            
            Task task2 = taskRepository.save(Task.builder()
                    .userId(user1.getId())
                    .title("Write Documentation")
                    .description("Document the application features and API")
                    .createdAt(LocalDateTime.now())
                    .targetDate(LocalDateTime.now().plusDays(3))
                    .completed(false)
                    .deleted(false)
                    .build());
            
            Task task3 = taskRepository.save(Task.builder()
                    .userId(user2.getId())
                    .title("Learn Spring Boot")
                    .description("Study Spring Boot features and best practices")
                    .createdAt(LocalDateTime.now())
                    .targetDate(LocalDateTime.now().plusDays(14))
                    .completed(false)
                    .deleted(false)
                    .build());
            
            // Create sample notifications
            notificationRepository.save(Notification.builder()
                    .userId(user1.getId())
                    .message("Welcome to Task Manager!")
                    .createdAt(LocalDateTime.now())
                    .read(false)
                    .build());
            
            notificationRepository.save(Notification.builder()
                    .userId(user1.getId())
                    .message("You have a task due in 3 days")
                    .createdAt(LocalDateTime.now())
                    .read(false)
                    .build());
            
            notificationRepository.save(Notification.builder()
                    .userId(user2.getId())
                    .message("Welcome to Task Manager!")
                    .createdAt(LocalDateTime.now())
                    .read(false)
                    .build());
            
            System.out.println("Sample data initialized!");
        };
    }
} 