package com.example.resources;

import com.example.core.User;
import com.example.core.UserEvent;
import com.example.db.UserDao;
import com.example.db.UserEventDao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDao userDao;
    private final UserEventDao userEventDao;

    public UserResource(UserDao userDao, UserEventDao userEventDao) {
        this.userDao = userDao;
        this.userEventDao = userEventDao;
    }

    @GET
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") UUID id) {
        return userDao.findById(id)
                .map(user -> Response.ok(user).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/events")
    public List<UserEvent> getAllEvents() {
        return userEventDao.findAll();
    }

    @GET
    @Path("/{id}/events")
    public List<UserEvent> getEventsByUserId(@PathParam("id") UUID id) {
        return userEventDao.findByUserId(id);
    }
}
