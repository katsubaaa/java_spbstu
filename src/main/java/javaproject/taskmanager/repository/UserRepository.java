package javaproject.taskmanager.repository;

import javaproject.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
	
}