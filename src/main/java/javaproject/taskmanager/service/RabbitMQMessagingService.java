package javaproject.taskmanager.service;

import javaproject.taskmanager.config.RabbitMQConfig;
import javaproject.taskmanager.dto.TaskMessage;
import javaproject.taskmanager.model.Task;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQMessagingService implements MessagingService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQMessagingService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendTaskCreatedMessage(Task task) {
        TaskMessage message = new TaskMessage(
            task.getId(),
            task.getUserId(),
            task.getTitle(),
            task.getDescription(),
            task.getCreatedAt(),
            task.getTargetDate(),
            "CREATED"
        );
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.TASK_EXCHANGE,
            RabbitMQConfig.TASK_ROUTING_KEY,
            message
        );
    }
} 