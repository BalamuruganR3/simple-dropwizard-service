package com.example.core;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class UserEvent {
    private UUID id;
    private UUID userId;
    private String eventType;
    private Map<String, Object> metadata;
    private Instant createdAt;

    public UserEvent() {
    }

    public UserEvent(UUID id, UUID userId, String eventType, Map<String, Object> metadata, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.eventType = eventType;
        this.metadata = metadata;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEvent userEvent = (UserEvent) o;
        return Objects.equals(id, userEvent.id) && Objects.equals(userId, userEvent.userId) && Objects.equals(eventType, userEvent.eventType) && Objects.equals(metadata, userEvent.metadata) && Objects.equals(createdAt, userEvent.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, eventType, metadata, createdAt);
    }
}
