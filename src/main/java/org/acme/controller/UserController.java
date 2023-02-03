package org.acme.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.acme.dao.CreateUserRequest;
import org.acme.dao.User;
import org.acme.service.UsersService;

@Path("/user")
@ApplicationScoped
public class UserController {

    //Instancia clase logger para registrar en consola
    static Logger logger = Logger.getLogger(UserController.class.getName());

    //Dependence injection.
    @Inject
    UsersService usersService;

    /**
     * Metodo para exponer la consulta de usuarios en tabla de base de datos
     * @return lista de todos los usuarios existentes en la tabla
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> findAll() {

        logger.log(Level.INFO, "usuarios : "+usersService.list());
        return usersService.list();
    }

    /*@GET
    @Path("/json/reactive")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<User>> findAllJsonReactively() {


        return "en proceso";
        //logger.log(Level.FINER, "Respuesta reactiva");
        //return User.Uni.createFrom().item(usersService.list());
    }*/

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User create(CreateUserRequest request) {
        return usersService.create(User.builder()
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
        return usersService.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found! username=" + username));
    }
}