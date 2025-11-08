# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ShareParty is a Spring Boot application written in Kotlin for managing parties and shared expenses. It follows a clean architecture pattern with clear separation between presentation, domain, and data layers.

## Build and Development Commands

### Building the Project
```bash
# Build the project (excluding tests)
./gradlew build -x test

# Build with tests
./gradlew build

# Run the application locally
./gradlew bootRun

# Run tests
./gradlew test

# Run specific test class
./gradlew test --tests "com.partymakers.shareparty.domain.party.usecase.impl.CreatePartyRoomImplTest"
```

### Running the Application

**Docker Compose (Recommended for production/testing):**
```bash
./docker-start.sh
# or manually:
docker-compose up --build -d
```

**Local Development:**
```bash
./start.sh
# or manually:
./gradlew bootRun
```

Application runs on port 9000 with Swagger UI available at `http://localhost:9000/swagger-ui/index.html`

## Architecture

### Module Structure
- **Root**: Multi-module Gradle project with `common:dto` submodule
- **Main Application**: `src/main/kotlin/com/partymakers/shareparty/`
- **Friends Module**: `friends/` - Friend management functionality
- **Party Module**: `party/` - Party and expense management

### Clean Architecture Layers
Each module follows this structure:
- **presentation/**: API interfaces and controllers (e.g., `PartyApiV1`, `FriendApiV1`)
- **domain/**: Business logic, entities, and use cases
- **data/**: Repository implementations and data entities

### Key Architectural Patterns
- **Use Case Pattern**: Each business operation is implemented as a use case class
- **Repository Pattern**: Data access abstraction
- **DTO Pattern**: Separate presentation and domain models
- **Exception Handling**: Centralized via `SharePartyExceptionHandler`

## API Structure

### Party API (`/api/v1/party`)
- `POST /` - Create party
- `GET /parties` - List all parties
- `GET /{partyId}` - Get party details
- `POST /{partyId}/friend` - Invite friend
- `DELETE /{partyId}/friend` - Remove friend
- `POST /{partyId}/expense` - Add expense
- `DELETE /{partyId}/expense` - Remove expense

### Friends API (`/api/v1/friend`)
- `POST /friend` - Register new friend

## Testing

### Test Structure
- **Integration Tests**: In `src/test/kotlin/com/partymakers/shareparty/application/`
- **Unit Tests**: In `src/test/kotlin/com/partymakers/shareparty/domain/`
- **Mapper Tests**: In `src/test/kotlin/com/partymakers/shareparty/*/presentation/mapper/`

### Test Profiles
Tests use the `test` profile with H2 database configuration.

## Database and Migrations

- **Primary Database**: PostgreSQL 14
- **Test Database**: H2
- **Migrations**: Liquibase
- **JPA**: Spring Data JPA with Hibernate

## Dependencies

### Key Dependencies
- Spring Boot 3.4.4
- Kotlin 2.1.21
- Spring Data JPA
- SpringDoc OpenAPI 2.8.4 (Swagger)
- Liquibase
- PostgreSQL driver
- H2 (test)

### Common DTO Module
Shared DTOs are defined in the `common:dto` module:
- `Content<T>` - Generic response wrapper
- `DefaultResponseDto` - Standard API response
- `PageResponseDto` - Paginated response

## Development Guidelines

### Adding New Features
1. Define API interface in `presentation/`
2. Implement use case in `domain/usecase/`
3. Add repository method in `domain/repository/`
4. Implement repository in `data/repository/`
5. Add data entities and mappers
6. Write tests for all layers

### Code Style
- Kotlin-first development
- Clean architecture principles
- Use case classes for business logic
- DTOs for API communication
- Centralized exception handling

### Configuration
- Application properties in `src/main/resources/`
- Swagger configuration in `config/SwaggerConfig.kt`
- OpenAPI configuration in `config/OpenApiConfig.kt`

## Troubleshooting

### Swagger UI Issues
If Swagger UI shows "Fetch error /v3/api-docs" with 500 status:

**Common Causes and Solutions:**
1. **Java Version Mismatch**: Application requires Java 21
   ```bash
   export JAVA_HOME=/opt/homebrew/opt/openjdk@21
   ./gradlew bootRun
   ```

2. **Port Conflict**: Port 9000 is already in use
   ```bash
   lsof -ti:9000 | xargs kill -9
   ```

3. **SpringDoc Version Incompatibility**: Use SpringDoc 2.8.4+ with Spring Boot 3.4.4
   ```gradle
   implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.4")
   ```

4. **Exception Handler Interference**: Global exception handler conflicts with SpringDoc
   - Solution: Limit exception handler scope to specific packages
   ```kotlin
   @RestControllerAdvice(basePackages = ["com.partymakers.shareparty.party", "com.partymakers.shareparty.friends"])
   ```

### SpringDoc Configuration
Ensure proper SpringDoc configuration in `application.yml`:
```yaml
springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
    operations-sorter: method
    tags-sorter: alpha
```