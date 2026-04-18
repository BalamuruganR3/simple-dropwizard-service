package com.example.db;

import com.example.core.UserEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public class UserEventMapper implements RowMapper<UserEvent> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public UserEvent map(ResultSet rs, StatementContext ctx) throws SQLException {
        UserEvent event = new UserEvent();
        event.setId(rs.getObject("id", UUID.class));
        event.setUserId(rs.getObject("user_id", UUID.class));
        event.setEventType(rs.getString("event_type"));
        
        String metadataJson = rs.getString("metadata");
        if (metadataJson != null) {
            try {
                event.setMetadata(MAPPER.readValue(metadataJson, new TypeReference<Map<String, Object>>() {}));
            } catch (IOException e) {
                throw new SQLException("Failed to parse metadata JSON", e);
            }
        }

        event.setCreatedAt(rs.getObject("created_at", OffsetDateTime.class).toInstant());
        
        return event;
    }
}
