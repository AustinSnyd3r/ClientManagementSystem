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

# Running the Microservices in IntelliJ

This setup assumes each service will be run through IntelliJ using Dockerfile run configurations.

## General Instructions

1. Make sure Docker Desktop is running.
2. In IntelliJ, create a Dockerfile run configuration for each service:  
   Edit Configurations -> Add (+) -> Dockerfile.
3. Select the correct Dockerfile for each service and set the required environment variables and run options.
4. Start the database containers first. Then start the services in any order.

---

## API Gateway

**Dockerfile**  
api-gateway/Dockerfile

**Environment Variables**
- AUTH_SERVICE_URL=http://auth-service:4005

**Run Options**
- --network coach

---

## Client Service

**Dockerfile**  
client-service/Dockerfile

**Environment Variables**
- AUTH_SERVICE_URL=http://auth-service:4005
- SPRING_DATASOURCE_PASSWORD=password
- SPRING_DATASOURCE_URL=jdbc:postgresql://client-service-db:5432/db
- SPRING_DATASOURCE_USERNAME=admin_user
- SPRING_JPA_HIBERNATE_DDL_AUTO=update
- SPRING_SQL_INIT_MODE=always

**Run Options**
- --network coach

---

## Auth Service

**Dockerfile**  
auth-service/Dockerfile

**Environment Variables**
- JWT_SECRET={YOUR_JWT_SECRET_HERE}
- SPRING_DATASOURCE_PASSWORD=password
- SPRING_DATASOURCE_URL=jdbc:postgresql://auth-service-db:5432/db
- SPRING_DATASOURCE_USERNAME=admin_user
- SPRING_JPA_HIBERNATE_DDL_AUTO=update
- SPRING_SQL_INIT_MODE=always

**Run Options**
- --network coach

---

## Billing Service (WIP)

**Dockerfile**  
billing-service/Dockerfile

**Environment Variables**
- STRIPE_SECRET_KEY={YOUR_STRIPE_SECRET_KEY}

**Run Options**
- --network coach

---

## Auth Service Database

**Base Image**  
postgres:latest

**Port Bindings**
- 5002:5432

**Bind Mounts**  
.../db_volumes/auth-service-db → /var/lib/postgresql/

**Run Options**
- --network coach

---

## Client Service Database

**Base Image**  
postgres:latest

**Port Bindings**
- 5001:5432

**Bind Mounts**  
.../db_volumes/client-service-db → /var/lib/postgresql/

**Run Options**
- --network coach

---

## Running Everything

1. Start the database containers:  
   client-service-db and auth-service-db.
2. Start the application services:  
   auth-service, client-service, api-gateway, and billing-service.
3. Use IntelliJ run configurations or the Docker CLI.