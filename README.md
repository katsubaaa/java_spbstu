# Task Manager Application - Step 1

## Basic REST API with In-Memory Storage

This is the first step of the Task Manager application development, focusing on creating a basic REST API using Spring Boot with in-memory storage.

### Features Implemented

- **TaskController** with endpoints:
  - GET all user's tasks
  - GET pending tasks
  - POST to create a new task
  - DELETE to mark tasks as deleted (not actually removed)

- **UserController** with endpoints:
  - GET for login-like functionality
  - POST for user registration

- **NotificationController** with endpoints:
  - GET pending notifications for a user
  - GET all notifications for a user

### Technical Details

- Tasks include creation date and target date
- Separate classes for controllers and services
- In-memory storage using Lists/Maps
- JSON responses with appropriate HTTP status codes

### Project Structure
- MVC architecture with separate controllers and services
- Maven for dependency management

### Next Steps
Proceed to branch `step1` for Step 2 (Unit Testing) 
