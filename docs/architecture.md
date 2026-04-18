# Architecture Documentation

This document outlines the key architectural choices and patterns used in the `simple-dropwizard-service`.

## Core Technologies
- **Java 21**: Leveraging the latest LTS features for performance and modern syntax.
- **Dropwizard 4.0.7**: A production-ready Java framework that bundles:
    - **Jetty** for the HTTP server.
    - **Jersey** for RESTful web services.
    - **Jackson** for JSON processing.
    - **Jakarta Validation** for configuration and request validation.

## Build and Deployment
- **Maven**: Used for dependency management and build lifecycle.
- **Fat JAR (Uber-JAR)**: The `maven-shade-plugin` is used to package the application and its dependencies into a single executable JAR file for simple deployment.

## Database & Persistence
- **PostgreSQL**: Selected as the primary relational database.
- **JDBI3**: Used for database interaction, providing a more idiomatic and less boilerplate-intensive alternative to JDBC or full ORMs.
- **Flyway**: Managed migrations are executed during the application's `run` phase to ensure the schema is always up to date with the code.

## Configuration Management
- **YAML-based Configuration**: The application is configured via `config.yml`.
- **Dynamic Variable Substitution**: Uses a `SubstitutingSourceProvider` with `StringSubstitutor` to allow property/environment variable injection (e.g., `${db.password}`) into the YAML configuration at runtime.
- **Property File Support**: Specifically looks for a `config.properties` file for local overrides.

## Project Structure
The project follows a standard Maven structure with specialized packages:
- `com.example.api`: Contains Data Transfer Objects (DTOs) for the REST layer (e.g., `HelloWorldResponse`).
- `com.example.core`: Contains domain models and business logic (e.g., `User`).
- `com.example.db`: Contains database access logic, including JDBI DAOs (`UserDao`) and mappers (`UserMapper`).
- `com.example.resources`: Houses Jersey REST resources defining the API endpoints.
- `com.example`: Main application entry point (`HelloWorldApplication`) and configuration mapping (`HelloWorldConfiguration`).

## Persistence Strategy
- **DAO Pattern**: JDBI SQL Objects (interfaces) are used for data access.
- **Explicit Mapping**: `RowMapper` implementations are used to explicitly map database columns to Java types, ensuring type safety and clarity for temporal types (`TIMESTAMPTZ` to `Instant`).
- **Optimistic Locking**: Managed via a `version` column in the database and checked during updates.

## API Standards
- **RESTful Design**: Jersey is used to build REST endpoints.
- **JSON**: Default media type for requests and responses.
