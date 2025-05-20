package javaproject.taskmanager.config;

import javaproject.taskmanager.service.NotificationService;
import javaproject.taskmanager.service.TaskService;
import javaproject.taskmanager.service.UserService;
import javaproject.taskmanager.service.db.NotificationServiceDB;
import javaproject.taskmanager.service.db.TaskServiceDB;
import javaproject.taskmanager.service.db.UserServiceDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public TaskService taskService() {
        return new TaskServiceDB();
    }

    @Bean
    public UserService userService() {
        return new UserServiceDB();
    }

    @Bean
    public NotificationService notificationService() {
        return new NotificationServiceDB();
    }
} 