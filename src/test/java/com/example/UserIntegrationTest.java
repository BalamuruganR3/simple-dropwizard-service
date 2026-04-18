package com.example;

import com.example.core.User;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DropwizardExtensionsSupport.class)
@Disabled("Requires a running PostgreSQL database")
class UserIntegrationTest {

    // Disabled by default as it requires a running PostgreSQL database
    // In a real CI environment, this would run against a containerized DB
    public static final DropwizardAppExtension<HelloWorldConfiguration> APP = new DropwizardAppExtension<>(
            HelloWorldApplication.class,
            "config.yml"
    );

    @Test
    @Disabled("Requires a running PostgreSQL database")
    void testGetAllUsers() {
        Response response = APP.client().target(
                String.format("http://localhost:%d/users", APP.getLocalPort()))
                .request()
                .get();

        assertThat(response.getStatus()).isEqualTo(200);
    }
}
