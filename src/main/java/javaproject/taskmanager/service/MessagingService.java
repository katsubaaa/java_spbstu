package javaproject.taskmanager.service;

import javaproject.taskmanager.dto.TaskMessage;
import javaproject.taskmanager.model.Task;

public interface MessagingService {
    void sendTaskCreatedMessage(Task task);
} 