package javaproject.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
@Slf4j
public class TaskmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}
	
    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        log.info("Task Manager started with Scheduling and Async Tasks enabled");
        log.info("Scheduler will check for overdue tasks every hour");
    }
}
