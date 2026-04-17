# Spring Boot Microservices - Movie Rating Application

A microservices-based movie rating application similar to IMDB, built with Spring Boot and Spring Cloud. The system allows users to view movie catalogs with ratings and movie information fetched from external APIs.

## Architecture

The application follows a microservices architecture with the following components:

- **Discovery Server**: Eureka server for service registration and discovery
- **Movie Catalog Service**: Aggregates movie data from multiple services
- **Movie Info Service**: Provides detailed movie information from TheMovieDB API
- **Ratings Data Service**: Manages user ratings stored in MySQL database
- **Trending Movies Service**: Provides trending movies data via gRPC

Services communicate through REST APIs and gRPC, with Eureka for service discovery and Hystrix for circuit breaking and fault tolerance.

![Architecture Diagram](https://user-images.githubusercontent.com/22833948/134519062-0013cbf9-8a5f-4a43-ba14-635ccdbab04b.png)

## Technologies

- **Spring Boot** - Framework for building microservices
- **Spring Cloud Netflix Eureka** - Service discovery
- **Spring Cloud Netflix Hystrix** - Circuit breaker and fault tolerance
- **Spring Data JPA** - Data access for relational databases
- **Spring Data MongoDB** - Data access for MongoDB
- **gRPC** - High-performance RPC framework
- **MySQL** - Relational database for ratings
- **MongoDB** - NoSQL database for movie data
- **Docker** - Containerization for databases

## Services Implementation Details

### Discovery Server
- **Technology**: Spring Cloud Netflix Eureka Server
- **Implementation**: 
  - Simple Eureka server configuration with `@EnableEurekaServer`
  - Configured as non-registering, non-fetching registry (standalone server)
  - Runs on port 8761
  - Provides service discovery for all microservices

### Movie Catalog Service
- **Port**: 8081
- **Technology**: Spring Boot with Hystrix, Eureka Client, gRPC client
- **Key Components**:
  - **Models**: `CatalogItem`, `Movie`, `Rating`, `UserRating`, `TrendingMovie`
  - **Services**: 
    - `MovieInfoService`: Calls movie-info-service with Hystrix circuit breaker, thread pool isolation, and fallback methods
    - `UserRatingService`: Calls ratings-data-service with Hystrix protection
  - **Controllers**: 
    - `MovieCatalogResource`: Main REST endpoint `/catalog/{userId}` that aggregates data from multiple services
    - `TrendingServiceClient`: gRPC client to call trending-movies-service
- **Features**:
  - Hystrix circuit breaker with custom thread pools and timeouts
  - Load-balanced REST calls using `@LoadBalanced` RestTemplate
  - Hystrix dashboard for monitoring
  - gRPC integration for trending movies endpoint `/catalog/trending`

### Movie Info Service
- **Port**: 8082
- **Technology**: Spring Boot with MongoDB, Eureka Client, Hystrix
- **Key Components**:
  - **Models**: `Movie` (MongoDB document), `MovieSummary` (for external API)
  - **Repository**: `MovieRepository` (MongoDB repository)
  - **Controller**: `MovieResource` with endpoint `/movies/{movieId}`
- **Features**:
  - MongoDB integration for movie data persistence
  - Caching mechanism (checks database first, saves new movies)
  - External API integration (TheMovieDB) - currently mocked with generated data
  - Hystrix circuit breaker protection

### Ratings Data Service
- **Port**: 8085
- **Technology**: Spring Boot with JPA, MySQL, Eureka Client, Hystrix
- **Key Components**:
  - **Models**: `Rating` (JPA entity), `UserRating` (DTO)
  - **Repository**: `RatingRepository` with custom queries for top-rated movies
  - **DTO**: `RatingDto` for aggregated rating data
  - **Controller**: `RatingsResource` with endpoints `/ratings/{userId}` and `/ratings/top`
- **Features**:
  - MySQL database with JPA/Hibernate
  - Custom JPQL query for calculating average ratings per movie
  - REST endpoints for user ratings and top movies
  - Hystrix circuit breaker protection

### Trending Movies Service
- **Port**: 8083
- **Technology**: Spring Boot with gRPC, Eureka Client, Hystrix
- **Key Components**:
  - **gRPC Service**: `TrendingServiceImpl` extending generated gRPC stub
  - **Protocol Buffers**: `trending.proto` defining gRPC service and messages
  - **Models**: Local `Rating` and `RatingsResponse` classes
- **Features**:
  - gRPC server implementation on port 50051
  - Calls ratings-data-service REST endpoint to get top movies
  - Protocol buffer serialization for high-performance communication
  - Hystrix circuit breaker protection

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- MySQL 8.0
- MongoDB 4.0+
- Docker (for running databases)

## Database Setup

### MySQL Setup
```bash
# Create database
CREATE DATABASE ratings_db;

# Update connection details in ratings-data-service/src/main/resources/application.properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### MongoDB Setup
Use Docker to run MongoDB:
```bash
cd movie-info-service
docker-compose up -d
```

## Running the Application

1. **Start Discovery Server**:
   ```bash
   cd discovery-server
   mvn spring-boot:run
   ```

2. **Start other services in any order**:
   ```bash
   # Movie Info Service
   cd movie-info-service
   mvn spring-boot:run

   # Ratings Data Service
   cd ratings-data-service
   mvn spring-boot:run

   # Trending Movies Service
   cd trending-movies-service
   mvn spring-boot:run

   # Movie Catalog Service (last)
   cd movie-catalog-service
   mvn spring-boot:run
   ```

## API Endpoints

- **Discovery Server Dashboard**: http://localhost:8761
- **Movie Catalog**: http://localhost:8081/catalog/{userId}
- **Movie Info**: http://localhost:8082/movies/{movieId}
- **Ratings Data**: http://localhost:8085/ratings/{userId}
- **Trending Movies**: gRPC on localhost:8083