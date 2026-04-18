package com.example.db;

import com.example.core.User;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserMapperTest {
    @Test
    void mapsResultSetToUser() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        StatementContext ctx = mock(StatementContext.class);
        UUID id = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        when(rs.getObject("id", UUID.class)).thenReturn(id);
        when(rs.getString("username")).thenReturn("john");
        when(rs.getString("email")).thenReturn("john@example.com");
        when(rs.getLong("version")).thenReturn(1L);
        when(rs.getObject("created_at", OffsetDateTime.class)).thenReturn(now);
        when(rs.getObject("updated_at", OffsetDateTime.class)).thenReturn(now);

        UserMapper mapper = new UserMapper();
        User user = mapper.map(rs, ctx);

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getUsername()).isEqualTo("john");
        assertThat(user.getEmail()).isEqualTo("john@example.com");
        assertThat(user.getVersion()).isEqualTo(1L);
        assertThat(user.getCreatedAt()).isEqualTo(now.toInstant());
    }
}
