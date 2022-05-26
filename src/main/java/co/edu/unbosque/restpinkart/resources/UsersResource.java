package co.edu.unbosque.restpinkart.resources;
import co.edu.unbosque.restpinkart.dtos.ExceptionMessage;
import co.edu.unbosque.restpinkart.dtos.Usuario;
import co.edu.unbosque.restpinkart.services.AgregarUsuario;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.*;
import java.util.List;

@Path("/users")
public class UsersResource {
    // Objects for handling connection

    @Context
    ServletContext context;
    Connection conn = null;
    PreparedStatement prestmt = null;
    Statement stmt = null;
    Usuario user_consulted = null;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    // Database credentials
    static final String USER = "postgres";
    static final String PASS = "20031812";


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

            @FormParam("username") String usernameparam,
            @FormParam("password") String passwordparam,
            @FormParam("role") String roleparam

    ) {
        Response response;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("intento conectarme a la base de datos");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("=> creating user...");

            String sql = "INSERT INTO user_arts(name,password,role,fcoins) VALUES (?,?,?,?)";
            prestmt = conn.prepareStatement(sql);
            prestmt.setString(1,usernameparam);
            prestmt.setString(2,passwordparam);
            prestmt.setString(3,roleparam);
            prestmt.setInt(4,0);
            prestmt.executeUpdate();
            prestmt.close();
            conn.close();

            Usuario UserCreated = new Usuario(usernameparam,passwordparam,roleparam,0,0);

            if (UserCreated != null) {
                response= Response.ok()
                        .entity(UserCreated)
                        .build();
            } else {
                response = Response.status(404)
                        .entity(new ExceptionMessage(404, "Error"))
                        .build();
            }

        } catch (SQLException se) {
            se.printStackTrace();
            response = Response.serverError().build();

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
            response = Response.serverError().build();

        }finally {
            try {
                if (prestmt != null) prestmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return response;
    }






    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("username")  String username) {
        Response response = null;
        String namers ;
        String passwordrs ;
        String rolers ;
        int fcoinsrs ;
        int idrs =0;
        try {

            Class.forName(JDBC_DRIVER);
            System.out.println("intento conectarme a la base de datos");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("=> consulting user..."+username);
            String sql = "SELECT * FROM user_arts u WHERE u.name = ?";
            prestmt = conn.prepareStatement(sql);
            prestmt.setString(1,username);
            ResultSet rs = prestmt.executeQuery();

            while (rs.next()) {
                namers =rs.getString("name");
                passwordrs = rs.getString("password");
                rolers = rs.getString("role");
                fcoinsrs = rs.getInt("fcoins");
                idrs =  rs.getInt("id_user") ;
                Usuario temp = new Usuario(namers,passwordrs,rolers,fcoinsrs,idrs);
                user_consulted= temp;
                System.out.println(temp);

            }

            if (user_consulted != null) {
                response= Response.ok()
                        .entity(user_consulted)
                        .build();
            } else {
                response = Response.status(404)
                        .entity(new ExceptionMessage(404, "User not found"))
                        .build();
            }
            rs.close();
            prestmt.close();
            conn.close();

        } catch(ClassNotFoundException cn) {
            System.out.println("Class not found");
            response = Response.serverError().build();
        }
        catch (SQLException sq){
            response = Response.serverError().build();
        }
        finally {
            try {
                if (prestmt != null) prestmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return response;
    }

    @PUT
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response actualizarfcoins(@FormParam("username") String usernameform,
                                     @FormParam("password") String passwordform,
                                     @FormParam("fcoins") int fcoinsform){
        int id_encontrado = -1;
        PreparedStatement prestmt2=null;
        Response response = null;
        int fcoinsiniciales = 0;

        try {

            Class.forName(JDBC_DRIVER);
            System.out.println("intento conectarme a la base de datos");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("=> consulting user..."+usernameform);
            String sql = "SELECT * FROM user_arts u WHERE u.name = ? AND u.password = ?";
            prestmt = conn.prepareStatement(sql);
            prestmt.setString(1,usernameform);
            prestmt.setString(2,passwordform);
            ResultSet rs = prestmt.executeQuery();

            while (rs.next()) {
                id_encontrado=rs.getInt("id_user");
                fcoinsiniciales = rs.getInt("fcoins") + fcoinsform;
                System.out.println(id_encontrado);
                System.out.println(fcoinsiniciales);


            }


            if (id_encontrado != -1) {
                String sql2="UPDATE user_arts SET fcoins = ? WHERE id_user = ?";
                prestmt2 = conn.prepareStatement(sql2);
                prestmt2.setInt(1,fcoinsiniciales);
                prestmt2.setInt(2,id_encontrado);
                prestmt2.executeUpdate();

                prestmt2.close();
                Usuario ufcoins = new Usuario(usernameform, passwordform, "",fcoinsiniciales, id_encontrado);

                response= Response.ok()
                        .entity(ufcoins)
                        .build();
            } else {
                response = Response.status(404)
                        .entity(new ExceptionMessage(404, "User not found"))
                        .build();
            }
            rs.close();
            prestmt.close();
            conn.close();

        } catch(ClassNotFoundException cn) {
            response = Response.serverError().build();
        }
        catch (SQLException sq){
            response = Response.serverError().build();
        }
        finally {
            try {
                if (prestmt != null) prestmt.close();
                if (conn != null) conn.close();
                if(prestmt2!=null) prestmt2.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return response;
    }



}