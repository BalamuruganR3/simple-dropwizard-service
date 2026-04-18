package com.example.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserEventTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    void serializesToJson() throws Exception {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Instant now = Instant.now();
        Map<String, Object> metadata = Map.of("key", "value");
        final UserEvent event = new UserEvent(id, userId, "LOGIN", metadata, now);

        final String expected = MAPPER.writeValueAsString(event);
        
        UserEvent deserialized = MAPPER.readValue(expected, UserEvent.class);
        assertThat(deserialized).isEqualTo(event);
    }
}
