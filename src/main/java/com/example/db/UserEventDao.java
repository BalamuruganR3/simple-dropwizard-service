package com.example.db;

import com.example.core.UserEvent;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;
import java.util.UUID;

@RegisterRowMapper(UserEventMapper.class)
public interface UserEventDao {

    @SqlQuery("SELECT id, user_id, event_type, metadata, created_at FROM user_events")
    List<UserEvent> findAll();

    @SqlQuery("SELECT id, user_id, event_type, metadata, created_at FROM user_events WHERE user_id = :userId")
    List<UserEvent> findByUserId(@Bind("userId") UUID userId);
}
