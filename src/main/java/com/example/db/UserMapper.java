package com.example.db;

import com.example.core.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UserMapper implements RowMapper<User> {
    @Override
    public User map(ResultSet rs, StatementContext ctx) throws SQLException {
        User user = new User();
        user.setId(rs.getObject("id", UUID.class));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setVersion(rs.getLong("version"));
        
        // Explicitly handling TIMESTAMPTZ to Instant
        user.setCreatedAt(rs.getObject("created_at", OffsetDateTime.class).toInstant());
        user.setUpdatedAt(rs.getObject("updated_at", OffsetDateTime.class).toInstant());
        
        return user;
    }
}
