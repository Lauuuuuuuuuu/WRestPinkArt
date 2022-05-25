package co.edu.unbosque.restpinkart.resources;

import co.edu.unbosque.restpinkart.dtos.ExceptionMessage;
import co.edu.unbosque.restpinkart.dtos.Obras;

import javax.ws.rs.*;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MultivaluedMap;


@Path("/users/{username}/arts")

public class ArtsResource {
    @Context
    ServletContext context;

    private final String UPLOAD_DIRECTORY= File.separator;


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(MultipartFormDataInput input,
                               @PathParam("username") String username
    ) {

        String fileName = "";
        Obras obra = null;
        Response response = null;
        System.out.println("entro");
        try{

            Map<String, List<InputPart>> formParts = input.getFormDataMap();
            String title = formParts.get("title").get(0).getBodyAsString();
            String price = formParts.get("price").get(0).getBodyAsString();
            String currentCollection = formParts.get("coleccion").get(0).getBodyAsString();
            List<InputPart> inputParts = formParts.get("image");

            String theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"0123456789"+"abcdefghijklmnopqrstuvwxyz";
            StringBuilder builder;

            builder = new StringBuilder(16);

            for (int m = 0; m < 16; m++) {
                int myindex = (int)(theAlphaNumericS.length() * Math.random());

                builder.append(theAlphaNumericS.charAt(myindex));
            }
            String newFileName = builder.toString();

            for (InputPart inputPart : inputParts) {
                if (fileName.equals("") || fileName == null) {
                    // Retrieving headers and reading the Content-Disposition header to file name
                    MultivaluedMap<String, String> headers= (MultivaluedMap<String, String>)
                            inputPart.getHeaders();

                    fileName = parseFileName(headers);
                }

                String format = fileName.split("\\.")[1];

                newFileName += "." + format;
            }

            obra = new Obras(currentCollection,title,username,price,0) ;
            for (InputPart inputPart : inputParts){
                InputStream inputStream = inputPart.getBody(InputStream.class,null);

                saveFile(inputStream,newFileName,currentCollection,context);
            }

            if (obra != null){
                response = Response.ok().entity(obra).build();

            }


        }catch (IOException e) {
            e.printStackTrace();
            response = Response.status(404)
                    .entity(new ExceptionMessage(404, "Error creando obra"))
                    .build();
        }
        System.out.println(response.getStatus());
        return response;
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

    private void saveFile(InputStream uploadedInputStream, String fileName, String currentCollection, ServletContext context) {
        int read = 0;
        byte[] bytes = new byte[1024];

        try {
            // Complementing servlet path with the relative path on the server
            String uploadPath = context.getRealPath("") + UPLOAD_DIRECTORY+currentCollection;

            // Creating the upload folder, if not exist
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) uploadDir.mkdir();

            // Persisting the file by output stream
            OutputStream outpuStream = new FileOutputStream(uploadPath + File.separator+fileName);
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }

            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
