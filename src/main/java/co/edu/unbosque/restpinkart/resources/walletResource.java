package co.edu.unbosque.restpinkart.resources;

import co.edu.unbosque.restpinkart.dtos.Usuario;
import co.edu.unbosque.restpinkart.services.AgregarUsuario;
import co.edu.unbosque.restpinkart.services.Wallet;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Path("/wallet")
public class walletResource {
    @Context
    ServletContext context;


    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    // Database credentials
    static final String USER = "postgres";
    static final String PASS = "20031812";
    //metodo de compra

    @PUT
    @Path("/buy")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyArt(@FormParam("userBuyer") String userBuyer,
                           @FormParam("price") String price,
                           @FormParam("userSeller") String userSeller,
                           @FormParam("name_art") String name_art,
                           @FormParam("artId") int artId){

        //cambiar metodo
        boolean buy = new Wallet().buy(userBuyer, price, name_art).get();

        Connection conn = null;

        if (buy) {
            new Wallet().modificarDueño(userBuyer,artId);
            new Wallet().sale(userSeller, price);

            List<Usuario> users = null;
            try {
                Class.forName(JDBC_DRIVER);

                // Opening database connection
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                users = new AgregarUsuario().getUsers();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                // Cleaning-up environment
                try {
                    if (conn != null) conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }

            Usuario userfound = users.stream().filter(user -> userBuyer.equals(user.getEmail())).findFirst().orElse(null);
            if (userfound != null) {
                return Response.ok().entity(userfound).build();
            }

        }
        return Response.serverError().build();
    }
}