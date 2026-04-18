package com.example.db;

import com.example.core.User;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RegisterRowMapper(UserMapper.class)
public interface UserDao {

    @SqlQuery("SELECT id, username, email, version, created_at, updated_at FROM users")
    List<User> findAll();

    @SqlQuery("SELECT id, username, email, version, created_at, updated_at FROM users WHERE id = :id")
    Optional<User> findById(@Bind("id") UUID id);

    @SqlUpdate("INSERT INTO users (username, email) VALUES (:username, :email)")
    @GetGeneratedKeys
    User insert(@BindBean User user);

    @SqlUpdate("UPDATE users SET username = :username, email = :email, version = version + 1 WHERE id = :id AND version = :version")
    int update(@BindBean User user);

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    void deleteById(@Bind("id") UUID id);
}
