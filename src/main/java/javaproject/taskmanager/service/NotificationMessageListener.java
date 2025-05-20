package javaproject.taskmanager.service;

import javaproject.taskmanager.config.RabbitMQConfig;
import javaproject.taskmanager.dto.TaskMessage;
import javaproject.taskmanager.model.Notification;
import javaproject.taskmanager.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationMessageListener {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationMessageListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.TASK_QUEUE)
    public void handleTaskCreatedMessage(TaskMessage message) {
        if ("CREATED".equals(message.getAction())) {
            Notification notification = Notification.builder()
                .userId(message.getUserId())
                .message("New task created: " + message.getTitle())
                .createdAt(LocalDateTime.now())
                .read(false)
                .build();
                
            notificationRepository.save(notification);
        }
    }
} 