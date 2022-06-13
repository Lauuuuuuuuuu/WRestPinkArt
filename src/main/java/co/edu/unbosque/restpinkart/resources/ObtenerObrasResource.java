
package co.edu.unbosque.restpinkart.resources;

import co.edu.unbosque.restpinkart.dtos.ExceptionMessage;
import co.edu.unbosque.restpinkart.dtos.Obras;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/users/obtenerobras")

public class ObtenerObrasResource {

    Connection conn = null;
    private ArtsResource arts = new ArtsResource();

    PreparedStatement prestmt = null;
    Statement stmt = null;
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/prueba1";

    // Database credentials
    static final String USER = "postgres";
    static final String PASS = "Zeref29714526?";
    List<Obras> listaObras = new ArrayList<>();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArts(){

        System.out.println("entro a obtener obras");
        String autor = "";
        String collection_name = "";
        Response response = null;
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("intento conectarme a la base de datos");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("=> consulting arts...");
            stmt = conn.createStatement();
            String sql = "SELECT * FROM arts_table";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // Extracting row values by column name
                String art_name = rs.getString("name");
                int price = rs.getInt("price");
                String file = rs.getString("imagepath");
                boolean forsale = rs.getBoolean("forsale");
                int id_collection = rs.getInt("id_collection");
                System.out.println(art_name);
                int id_art = rs.getInt("id_art");

                String sql2 = "SELECT * FROM collection_table u WHERE u.id_collection =?";
                prestmt = conn.prepareStatement(sql2);
                prestmt.setInt(1,id_collection);
                ResultSet rs2 = prestmt.executeQuery();
                while(rs2.next()){
                    autor = rs2.getString("email");
                    collection_name = rs2.getString("name");
                }
                System.out.println("lei autor");
                // Creating a new UserApp class instance and adding it to the array list
                rs2.close();
                prestmt.close();
                Obras agregarObra = new Obras(collection_name,art_name,autor,price,0,file,id_art);
                listaObras.add(agregarObra);
                System.out.println(agregarObra.getTitle());
            }
            rs.close();
            stmt.close();
            conn.close();
            if (listaObras != null){
                response = Response.ok().entity(listaObras).build();
            }
            else {
                response = Response.status(404)
                        .entity(new ExceptionMessage(404, "Not found"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response = Response.serverError().build();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response = Response.serverError().build();
        }
        finally {
            try {
                if (prestmt != null) prestmt.close();
                if (conn != null) conn.close();
                if(stmt!=null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return response;
    }

}

