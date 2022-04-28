package co.edu.unbosque.restpinkart.resources;
import co.edu.unbosque.restpinkart.dtos.Collection;
import co.edu.unbosque.restpinkart.services.Operaciones;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("collections/")
public class Collections {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUltimasCollections() throws IOException {
        Operaciones operaciones = new Operaciones();
        List<Collection> collections = operaciones.getUltimasColecciones();
        return Response.ok().entity(collections).build();
    }

}
