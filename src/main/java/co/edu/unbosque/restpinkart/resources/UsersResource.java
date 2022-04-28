package co.edu.unbosque.restpinkart.resources;



import co.edu.unbosque.restpinkart.dtos.ExceptionMessage;
import co.edu.unbosque.restpinkart.dtos.Usuario;
import co.edu.unbosque.restpinkart.services.AgregarUsuario;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Path("/users")
public class UsersResource {

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
    @Path("/form")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createForm(

            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("role") String role
    ) {

        String contextPath =context.getRealPath("") + File.separator;
        System.out.println(username);
        try {
            Usuario usuario = new AgregarUsuario().crearUsuario(username, password, role, "0",contextPath);

            return Response.created(UriBuilder.fromResource(UsersResource.class).path(username).build())
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
            System.out.println(username);
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