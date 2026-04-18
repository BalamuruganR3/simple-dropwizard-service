package com.example.resources;

import com.example.api.HelloWorldResponse;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DropwizardExtensionsSupport.class)
class HelloWorldResourceTest {
    public static final ResourceExtension RESOURCES = ResourceExtension.builder()
            .addResource(new HelloWorldResource())
            .build();

    @Test
    void sayHello() {
        HelloWorldResponse response = RESOURCES.target("/hello-world").request().get(HelloWorldResponse.class);
        assertThat(response.getContent()).isEqualTo("Hello World");
    }
}
