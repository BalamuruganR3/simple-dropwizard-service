# Design Patterns

This document describes the design patterns employed in the `simple-dropwizard-service`.

## Externalized Configuration Pattern

The application implements the **Externalized Configuration Pattern** to ensure that the same deployment artifact (the Fat JAR) remains environment-agnostic.

### Local Source of Truth: `env.properties` (as `local.properties`)
For local development, the `local.properties` file (acting as our `env.properties` source) serves as the local source of truth for configuration overrides (e.g., `${db.password}`). This file is loaded by the application at startup to populate configuration placeholders.

### Environment Injection & Orchestration
In production-like environments, configuration is injected externally to maintain security and portability:
- **Docker Compose**: The `env_file` directive is used to inject values from the properties file directly into the container's environment.
- **Kubernetes (k3d)**: Sensitive configuration is managed via **Secret** objects, which are then injected as environment variables into the pod spec.

The application uses a `SubstitutingSourceProvider` to resolve these variables within the `config.yml`.

## Secret Leakage Mitigation

'Secret Leakage'—the accidental exposure of sensitive credentials in source control—is a critical risk. We mitigate this through a strict exclusion strategy:
- **`.gitignore`**: Prevents `local.properties` and other sensitive files from being committed to the git repository.
- **`.dockerignore`**: Ensures that these local secret files are not included in the Docker build context, preventing them from being accidentally baked into the container image layers.

By utilizing these tools, secrets remain external to the codebase and image, residing only in secure local environments or orchestration-managed secret stores.
