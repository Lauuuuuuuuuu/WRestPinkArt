package co.edu.unbosque.restpinkart.resources;

import co.edu.unbosque.restpinkart.dtos.Collection;
import co.edu.unbosque.restpinkart.dtos.ObrasArte;
import co.edu.unbosque.restpinkart.services.ServicioObras;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.servlet.http.Part;

import java.io.*;
import java.util.List;
import java.util.Map;


@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 10024 * 10024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@Path("users/{username}/collections")
public class ObrasResourses {

    @Context
    ServletContext context;
    private final String UPLOAD_DIRECTORY = "/Imagenes/";

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.MULTIPART_FORM_DATA)

    public Response createObra(
        @FormParam("title") String title,
        @FormParam("price") String price,
        @FormParam("Collection") String collection,
        MultipartFormDataInput input
        ){
        String contextPath = context.getRealPath("")+File.separator;
        File uploadDir = new File( context.getRealPath("") + UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) uploadDir.mkdir();
        Map<String, List<InputPart>> formParts = input.getFormDataMap();
        List<InputPart> inputParts = formParts.get("file");
        for (InputPart inputPart : inputParts) {
            try {
                // Retrieving headers and reading the Content-Disposition header to file name
                MultivaluedMap<String, String> headers = inputPart.getHeaders();

                String fileName = parseFileName(headers);

                // Handling the body of the part with an InputStream
                InputStream istream = inputPart.getBody(InputStream.class,null);

                saveFile(istream, fileName, context);
            } catch (IOException e) {
                return Response.serverError().build();
            }
        }
        try{

            ObrasArte obra = new ServicioObras().crearObra(title,Integer.parseInt(price),uploadDir,contextPath, true);
            Collection collection1 = new Collection(collection,obra);

            return Response.created(UriBuilder.fromResource(ObrasResourses.class).path(collection).build())
                    .entity(collection1)
                    .build();
        }
        catch (Exception e){
            return Response.serverError().build();
        }
    }
    private void saveFile(InputStream uploadedInputStream, String fileName, ServletContext context) {
        int read = 0;
        byte[] bytes = new byte[1024];

        try {
            // Complementing servlet path with the relative path on the server
            String uploadPath = context.getRealPath("") + UPLOAD_DIRECTORY;

            // Creating the upload folder, if not exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            // Persisting the file by output stream
            OutputStream outpuStream = new FileOutputStream(uploadPath + fileName);
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }

            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String parseFileName(MultivaluedMap<String, String> headers) {
        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

        for (String name : contentDispositionHeader) {
            if ((name.trim().startsWith("filename"))) {
                String[] tmp = name.split("=");
                String fileName = tmp[1].trim().replaceAll("\"","");
                return fileName;
            }
        }

        return "unknown";
    }

}
