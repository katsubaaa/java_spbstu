# Task Manager Application - Step 4

## Docker Support

This step adds containerization to the application for consistent deployment across environments.

### Implemented Features

- Dockerfile for building the application image
- Docker Compose configuration for orchestrating services
- Container-based deployment setup

### Docker Configuration

- Multi-stage build in Dockerfile for efficiency
- JRE-based runtime container for smaller footprint
- Environment variable support for configuration
- Docker Compose for running the application with its dependencies

### Running the Application

1. Build the application: `./mvnw clean package`
2. Build and start containers: `docker-compose up -d`
3. Access the application at http://localhost:8080
4. Stop containers: `docker-compose down`

### Containerization Benefits

- Consistent environment across development and production
- Isolated dependencies
- Simplified deployment process
- Easy horizontal scaling

### Next Steps

Proceed to branch `step4` for Step 5 (PostgreSQL Database) 
