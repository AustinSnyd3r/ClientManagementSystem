## Client Management System

### What is it?
    Backend for a client management system used
    for paid sports coaching. Ex: Powerlifting, Olympic Lifting, 
    Basketball, Golf.

### Stack:
    - Java 
    - Spring Boot
    - PostgreSQL
    - Docker
    - Maven

### Why so many folders?

    I chose to try out a microservices architecture,
    separating core functionality from auth and an API
    gateway. 

    This means that each of my microservices is held within its
    own submodule.

    - Client Service: This holds most of the business domain logic. 
    - Auth Service: This handles JWT authentication for access to API endpoints. 
    - API Gateway: This routes requests to allow a single external entrypoint for users. Additionally, it ensures authorization by routing requests to the auth service before hitting other endpoints.