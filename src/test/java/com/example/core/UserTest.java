package com.example.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    void serializesToJson() throws Exception {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();
        final User user = new User(id, "testuser", "test@example.com", 1L, now, now);

        final String expected = MAPPER.writeValueAsString(user);
        
        User deserialized = MAPPER.readValue(expected, User.class);
        assertThat(deserialized).isEqualTo(user);
    }
}
