package org.acme.controller;

import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Uni;
import org.acme.dao.CreateUserRequest;
import org.acme.dao.User;
import org.acme.service.impl.UsersService;

@Path("/user")
@ApplicationScoped
public class UserEndpoing {
    @Inject
    UsersService users;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> findAll() {
        return users.list();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User create(CreateUserRequest request) {
        return users.create(User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName()).admin(request.isAdmin())
                .hashedPassword(request.getHashedPassword())
                .build());
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findByUsername(@PathParam("username") String username) {
        return users.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found! username=" + username));
    }
}