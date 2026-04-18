package com.example.resources;

import com.example.core.User;
import com.example.core.UserEvent;
import com.example.db.UserDao;
import com.example.db.UserEventDao;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class UserResourceTest {
    private static final UserDao USER_DAO = mock(UserDao.class);
    private static final UserEventDao USER_EVENT_DAO = mock(UserEventDao.class);
    
    public static final ResourceExtension RESOURCES = ResourceExtension.builder()
            .addResource(new UserResource(USER_DAO, USER_EVENT_DAO))
            .build();

    @AfterEach
    void tearDown() {
        reset(USER_DAO);
        reset(USER_EVENT_DAO);
    }

    @Test
    void getAllUsers() {
        User user = new User(UUID.randomUUID(), "john", "john@example.com", 1L, Instant.now(), Instant.now());
        when(USER_DAO.findAll()).thenReturn(List.of(user));

        List<User> response = RESOURCES.target("/users").request().get(new jakarta.ws.rs.core.GenericType<List<User>>() {});
        
        assertThat(response).containsExactly(user);
        verify(USER_DAO).findAll();
    }

    @Test
    void getUserByIdFound() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "john", "john@example.com", 1L, Instant.now(), Instant.now());
        when(USER_DAO.findById(id)).thenReturn(Optional.of(user));

        User response = RESOURCES.target("/users/" + id).request().get(User.class);
        
        assertThat(response).isEqualTo(user);
    }

    @Test
    void getUserByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(USER_DAO.findById(id)).thenReturn(Optional.empty());

        jakarta.ws.rs.core.Response response = RESOURCES.target("/users/" + id).request().get();
        
        assertThat(response.getStatus()).isEqualTo(404);
    }
}
