# Inventory Management System

A production-inspired **Inventory Management System** built with **Spring Boot 3** following clean architecture principles and modern backend development practices. This project demonstrates secure REST APIs, scalable design, resilience patterns, caching, asynchronous event processing, and production-ready configurations.

The primary goal of this project is to showcase backend engineering skills expected from a **Senior Software Engineer** interview.

---

# Features

### Product Management

* Create Product
* Update Product
* Delete Product
* Get Product by ID
* Get All Products
* Search Products
* Pagination & Sorting

### Security

* JWT Authentication
* Spring Security 6
* Role-Based Authorization
* Password Encryption using BCrypt

### Validation & Exception Handling

* Bean Validation
* Global Exception Handling
* Custom Exceptions
* Standard Error Response

### Database

* PostgreSQL
* Spring Data JPA
* Hibernate
* Flyway Database Migrations

### Performance

* Redis Caching
* Cache Eviction
* Cache Updates

### Resilience

* Retry
* Circuit Breaker
* Rate Limiter
* Bulkhead

### Event Driven

* Spring Application Events
* Asynchronous Event Listeners

### API Documentation

* OpenAPI / Swagger UI

### Monitoring

* Spring Boot Actuator
* Health Endpoints
* Metrics

### Logging

* SLF4J
* Structured Logging

---

# Tech Stack

| Category          | Technology                  |
| ----------------- | --------------------------- |
| Language          | Java 21                     |
| Framework         | Spring Boot 3               |
| Security          | Spring Security + JWT       |
| Database          | PostgreSQL                  |
| ORM               | Hibernate / Spring Data JPA |
| Migration         | Flyway                      |
| Cache             | Redis                       |
| Resilience        | Resilience4j                |
| Mapping           | MapStruct                   |
| Build Tool        | Maven                       |
| API Documentation | OpenAPI (Swagger)           |
| Testing           | JUnit 5, Mockito            |
| Containerization  | Docker                      |

---

# Architecture

The application follows a layered architecture to maintain separation of concerns.

```text
                Client
                   │
                   ▼
             REST Controller
                   │
                   ▼
          Business Service Layer
                   │
        ┌──────────┴──────────┐
        ▼                     ▼
 Repository (JPA)      Event Publisher
        │                     │
        ▼                     ▼
 PostgreSQL           Async Event Listeners
        │
        ▼
      Redis Cache
```

---

# Project Structure

```text
src
├── config
├── controller
├── dto
├── entity
├── event
├── exception
├── mapper
├── repository
├── security
├── service
├── util
└── resources
```

---

# API Endpoints

| Method | Endpoint             | Description      |
| ------ | -------------------- | ---------------- |
| POST   | /api/products        | Create Product   |
| GET    | /api/products        | Get All Products |
| GET    | /api/products/{id}   | Get Product      |
| PUT    | /api/products/{id}   | Update Product   |
| DELETE | /api/products/{id}   | Delete Product   |
| GET    | /api/products/search | Search Products  |

---

# Key Design Decisions

* Layered architecture for maintainability.
* DTO pattern to isolate API contracts from persistence models.
* MapStruct for efficient object mapping.
* Constructor-based dependency injection.
* Global exception handling for consistent error responses.
* Flyway for version-controlled database migrations.
* Redis caching to reduce database load.
* Asynchronous event publishing for decoupled processing.
* Resilience4j for fault tolerance.
* Pagination and sorting to support scalable APIs.

---

# Security

* JWT-based stateless authentication
* Spring Security Filter Chain
* BCrypt password hashing
* Public and secured endpoints
* Role-based authorization

---

# Caching

Redis is used to improve read performance.

* Cache Product by ID
* Cache Product List
* Cache Eviction on Update/Delete
* Cache Synchronization

---

# Resilience

The application demonstrates production-ready resilience patterns.

* Retry
* Circuit Breaker
* Rate Limiter
* Bulkhead

---

# Database

* PostgreSQL
* Flyway migrations
* Entity relationships
* Optimistic locking using `@Version`

---

# Getting Started

## Prerequisites

* Java 21
* Maven 3.9+
* Docker
* PostgreSQL
* Redis

## Clone Repository

```bash
git clone <repository-url>
cd inventory-management-system
```

## Start Dependencies

```bash
docker compose up -d
```

## Build

```bash
mvn clean install
```

## Run

```bash
mvn spring-boot:run
```

---

# API Documentation

Once the application starts:

* Swagger UI:

  ```
  http://localhost:8080/swagger-ui.html
  ```

* OpenAPI:

  ```
  http://localhost:8080/v3/api-docs
  ```

---

# Future Enhancements

* Docker Compose for complete local environment
* Kubernetes deployment manifests
* CI/CD using GitHub Actions
* Prometheus & Grafana integration
* Distributed tracing with OpenTelemetry
* Kafka-based event streaming
* Multi-module architecture
* Multi-tenancy support

---

# Learning Outcomes

This project demonstrates practical experience with:

* Spring Boot 3
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate
* PostgreSQL
* Flyway
* Redis
* Resilience4j
* MapStruct
* Bean Validation
* Global Exception Handling
* Event-Driven Architecture
* REST API Design
* Clean Architecture
* Production-ready backend development

---

# License

This project is intended for learning, interview preparation, and demonstrating backend engineering best practices.


## Running Locally

Start dependencies:

```bash
Run postgres

docker run --name postgres-db \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=inventory_db \
-p 5432:5432 \
-d postgres:17

validate postgres container is running
docker ps
docker rm <container-id>

mvn clean compile
mvn spring-boot:run

swagger UI
http://localhost:8080/swagger-ui/index.html

POST /api/v1/products
{
"sku":"IPHONE16",
"name":"iPhone 16",
"description":"128GB",
"category":"Mobile",
"price":90000,
"availableQuantity":100
}

PUT /api/v1/products/1
{
"name":"iPhone 16 Pro",
"description":"256GB Black",
"category":"Mobile",
"price":99999,
"availableQuantity":50
}


Verify database
docker exec -it postgres-db psql -U postgres -d inventory_db

select * from product;


Redis
docker compose up -d
docker ps

docker compose down

//delete named volumes of database
docker compose down -v

mvn spring-boot:run

verify redis
docker exec -it inventory-redis redis-cli
 KEYS *
 TYPE products::1
 GET products::1
 
 docker stop redis
 docker start redis
PING
```

Check Circuit breaker details
GET http://localhost:8080/actuator
GET http://localhost:8080/actuator/circuitbreakerevents
GET http://localhost:8080/actuator/circuitbreakers
GET /actuator/metrics
