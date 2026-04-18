# Simple Dropwizard Service

A minimalist Dropwizard-based service using JDBI and Flyway for PostgreSQL database management.

## Prerequisites

- Java 21+
- Maven 3.x
- PostgreSQL

## Configuration

The database credentials should be managed outside of version control. 

1. Create a `config.properties` file in the root directory:
   ```properties
   db.password=your_database_password
   ```
   This file is listed in `.gitignore` to prevent accidental commits of sensitive information.

2. Database connection details are configured in `config.yml`.

## Domain Model and Data Access

The service uses a standard DAO pattern for data access:

- **Entity**: `User` in `com.example.core`.
- **DAO**: `UserDao` in `com.example.db` using JDBI SQL Objects.
- **Mapping**: Explicitly handled in `UserMapper` to map PostgreSQL types (like `TIMESTAMPTZ`) to Java types (`Instant`).

Optimistic locking is implemented on the `users` table via a `version` column.

## Database Migrations

Database migrations are managed by Flyway. 
Migrations should be placed in `src/main/resources/db/migration`. 
They run automatically when the application starts.

## Build and Run

### Build the project:

```bash
mvn clean package
```

### Run the application:

```bash
java -jar target/simple-service-1.0-SNAPSHOT.jar server config.yml
```

The application will be available at `http://localhost:8080`.
The admin interface is available at `http://localhost:8081`.
