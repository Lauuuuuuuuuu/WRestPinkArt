package co.edu.unbosque.wrestpinkart.resources;

import co.edu.unbosque.wrestpinkart.dtos.ExceptionMessage;
import co.edu.unbosque.wrestpinkart.dtos.Usuario;
import co.edu.unbosque.wrestpinkart.services.AgregarUsuario;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Path("/users/{username}/collections")
public class CollectionsResource {

    @Context
    ServletContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        try {
            List<Usuario> usuarios = new AgregarUsuario().getUsers();

            return Response.ok()
                    .entity(usuarios)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createForm(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("role") String role
    ) {
        String contextPath =context.getRealPath("") + File.separator;

        try {
            Usuario usuario = new AgregarUsuario().crearUsuario(username, password, role, "0",contextPath);

            return Response.created(UriBuilder.fromResource(CollectionsResource.class).path(username).build())
                    .entity(usuario)
                    .build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("username") String username) {
        try {
            List<Usuario> users = new AgregarUsuario().getUsers();

            Usuario usuario = users.stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);

            if (usuario != null) {
                return Response.ok()
                        .entity(usuario)
                        .build();
            } else {
                return Response.status(404)
                        .entity(new ExceptionMessage(404, "User not found"))
                        .build();
            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }


}