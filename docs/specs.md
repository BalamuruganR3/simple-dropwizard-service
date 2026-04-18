# Specifications

This document outlines specific implementation details and constraints for the `simple-dropwizard-service`.

## Database Specifications

### Reserved Keywords: The `user` Table
In PostgreSQL, `user` is a reserved keyword. The `simple-dropwizard-service` handles this potential conflict through the following mechanisms:
- **JDBC Connection String**: The connection URL can be configured (e.g., via `stringtype=unspecified` or other driver parameters) to help handle identifier issues.
- **Quoted JDBI Fluents**: JDBI SQL objects utilize explicit quoting (e.g., `@SqlQuery("SELECT * FROM \"user\" WHERE id = :id")`) to ensure compatibility.

Crucially, these specific configurations—including the JDBC URL and the credentials required for access—are fed into the application via **environment variables** (orchestrated via `local.properties`, Compose `env_file`, or k3d Secrets), ensuring that the application logic remains decoupled from the specific database environment.
