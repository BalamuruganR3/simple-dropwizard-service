# API Contract

This document describes the REST endpoints available in the `simple-dropwizard-service`.

## Base URL
`http://localhost:8080`

## Endpoints

### Hello World
- **URL**: `/hello-world`
- **Method**: `GET`
- **Description**: Basic health/smoke test endpoint.
- **Success Response**:
    - **Code**: 200 OK
    - **Content**:
      ```json
      {
        "content": "Hello World"
      }
      ```

### Users

#### List All Users
- **URL**: `/users`
- **Method**: `GET`
- **Description**: Retrieves a list of all users.
- **Success Response**:
    - **Code**: 200 OK
    - **Content**:
      ```json
      [
        {
          "id": "uuid-string",
          "username": "john_doe",
          "email": "john@example.com",
          "version": 1,
          "createdAt": "2026-04-18T11:00:00Z",
          "updatedAt": "2026-04-18T11:00:00Z"
        }
      ]
      ```

#### Get User by ID
- **URL**: `/users/{id}`
- **Method**: `GET`
- **Path Params**: `id=[UUID]`
- **Description**: Retrieves a single user by their unique identifier.
- **Success Response**:
    - **Code**: 200 OK
    - **Content**: User object (see above).
- **Error Response**:
    - **Code**: 404 Not Found

### User Events

#### List All Events
- **URL**: `/users/events`
- **Method**: `GET`
- **Description**: Retrieves a list of all recorded user events across the system.
- **Success Response**:
    - **Code**: 200 OK
    - **Content**:
      ```json
      [
        {
          "id": "uuid-string",
          "userId": "uuid-string",
          "eventType": "LOGIN",
          "metadata": {
            "ip": "127.0.0.1",
            "agent": "Mozilla/5.0"
          },
          "createdAt": "2026-04-18T11:05:00Z"
        }
      ]
      ```

#### List Events for a Specific User
- **URL**: `/users/{id}/events`
- **Method**: `GET`
- **Path Params**: `id=[UUID]`
- **Description**: Retrieves all events associated with a specific user.
- **Success Response**:
    - **Code**: 200 OK
    - **Content**: List of Event objects (see above).

## Data Models

### User
| Field | Type | Description |
| :--- | :--- | :--- |
| `id` | UUID | Primary Key |
| `username` | String | Unique username |
| `email` | String | Unique email |
| `version` | Long | Optimistic locking version |
| `createdAt` | ISO-8601 | Timestamp of creation |
| `updatedAt` | ISO-8601 | Timestamp of last update |

### User Event
| Field | Type | Description |
| :--- | :--- | :--- |
| `id` | UUID | Primary Key |
| `userId` | UUID | Foreign Key to User |
| `eventType` | String | Type of event (e.g., LOGIN, SIGNUP) |
| `metadata` | Map | Flexible JSON metadata |
| `createdAt` | ISO-8601 | Timestamp of event |
