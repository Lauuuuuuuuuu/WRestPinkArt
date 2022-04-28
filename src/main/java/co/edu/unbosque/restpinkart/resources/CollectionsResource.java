package co.edu.unbosque.restpinkart.resources;



import co.edu.unbosque.restpinkart.dtos.Collection;
import co.edu.unbosque.restpinkart.services.Operaciones;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/users/{username}/collections")
public class CollectionsResource {

    @Context
    ServletContext context;

    @POST
    @Path("/form")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postCollection(@PathParam("username") String username, @FormParam("collectionName") String collection, @FormParam("obra") String obra) throws IOException {
        Operaciones operaciones = new Operaciones();
        operaciones.crearColeccion(username,collection,obra);
        return Response.ok().entity(null).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCollections(@PathParam("username") String username) throws IOException {
        Operaciones operaciones = new Operaciones();
        List<Collection> col= operaciones.getColeccionesPorArtista(username);
        return Response.ok().entity(col).build();
    }


}