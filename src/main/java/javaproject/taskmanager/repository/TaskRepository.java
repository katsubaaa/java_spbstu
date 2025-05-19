package javaproject.taskmanager.repository;

import javaproject.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
    List<Task> findByUserIdAndDeletedFalse(Long userId);
	
    List<Task> findByUserIdAndDeletedFalseAndCompletedFalse(Long userId);
	
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.deleted = true WHERE t.id = :taskId")
    void markDeleted(Long taskId);
	
}