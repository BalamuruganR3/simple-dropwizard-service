package com.example.resources;

import com.example.api.HelloWorldResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

    @GET
    public HelloWorldResponse sayHello() {
        return new HelloWorldResponse("Hello World");
    }
}
