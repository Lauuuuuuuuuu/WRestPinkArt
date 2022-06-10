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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

            @FormParam("role") String rolparam,

            @FormParam("email") String emailparam

    ) {
        Response response;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("intento conectarme a la base de datos");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("=> creating user...");


            String sql = "INSERT INTO user_arts(name,email,rol,password) VALUES (?,?,?,?)";
            prestmt = conn.prepareStatement(sql);
            prestmt.setString(1,usernameparam);
            prestmt.setString(2,emailparam);
            prestmt.setString(3,rolparam);
            prestmt.setString(4,passwordparam);

            prestmt.executeUpdate();
            prestmt.close();
            conn.close();


            Usuario UserCreated = new Usuario(usernameparam,passwordparam,rolparam, emailparam,0);


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
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("email")  String email) {
        Response response = null;
        String emailrs;
        String namers ;
        String passwordrs ;
        int fcoinsrs;
        String rolrs ;

        try {

            Class.forName(JDBC_DRIVER);
            System.out.println("intento conectarme a la base de datos");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("=> consulting user..."+email);
            String sql = "SELECT * FROM user_arts u WHERE u.email = ?";
            prestmt = conn.prepareStatement(sql);
            prestmt.setString(1,email);
            ResultSet rs = prestmt.executeQuery();

            while (rs.next()) {
                emailrs = rs.getString("email");
                namers =rs.getString("name");
                passwordrs = rs.getString("password");
                fcoinsrs = rs.getInt("fcoins");
                rolrs = rs.getString("rol");
                emailrs = rs.getString("email");

                Usuario temp = new Usuario(namers,passwordrs,rolrs,emailrs,fcoinsrs);

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
            sq.printStackTrace();
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
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response actualizarfcoins(@FormParam("password") String passwordform,
                                     @FormParam("fcoins") int fcoinsform,
                                     @PathParam("email") String emailform
    ) {

        String emialencontrado="";
        String name = "";
        PreparedStatement prestmt2=null;
        PreparedStatement prestmt3=null;
        Response response = null;
        int fcoinsiniciales = 0;

        try {

            Class.forName(JDBC_DRIVER);
            System.out.println("intento conectarme a la base de datos");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("=> consulting user..."+emailform);
            String sql = "SELECT * FROM user_arts u WHERE u.email = ? AND u.password = ?";
            prestmt = conn.prepareStatement(sql);
            prestmt.setString(1,emailform);
            prestmt.setString(2,passwordform);
            ResultSet rs = prestmt.executeQuery();

            while (rs.next()) {
                emialencontrado=rs.getString("email");
                name = rs.getString("name");
                fcoinsiniciales = rs.getInt("fcoins") + fcoinsform;
                System.out.println(emialencontrado);
                System.out.println(fcoinsiniciales);


            }


            if (emialencontrado != "") {
                String sql2="UPDATE user_arts SET fcoins = ? WHERE email = ?";
                prestmt2 = conn.prepareStatement(sql2);
                prestmt2.setInt(1,fcoinsiniciales);
                prestmt2.setString(2,emialencontrado);
                prestmt2.executeUpdate();

                prestmt2.close();
                Usuario usuario = new Usuario(emailform,name,passwordform,"",fcoinsiniciales);
                String sql3 = "INSERT INTO Wallet_table (email,type,registeredat,fcoins)VALUES(?,?,?,?) ";
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                java.util.Date date = new Date();
                Timestamp timestamp = new Timestamp(date.getTime());
                prestmt3 = conn.prepareStatement(sql3);
                prestmt3.setString(1,emialencontrado);
                prestmt3.setString(2,"recarga");
                prestmt3.setTimestamp(3, timestamp);
                prestmt3.setInt(4,fcoinsform);
                prestmt3.executeUpdate();
                prestmt3.close();
                response= Response.ok()
                        .entity(usuario)
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
            response = Response.status(404)
                    .entity(new ExceptionMessage(404, "User not found"))
                    .build();
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
       /* String id_encontrado = "";

        PreparedStatement prestmt2=null;
        Response response = null;
        int fcoinsiniciales = 0;

        try {

            Class.forName(JDBC_DRIVER);
            System.out.println("intento conectarme a la base de datos");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            System.out.println("=> consulting user..."+emailform);
            String sql = "SELECT * FROM user_arts u WHERE u.email ='"+ emailform+ "' AND u.password='"+passwordform+"'";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                id_encontrado=rs.getString("name");
//                fcoinsiniciales = rs.getInt("fcoins") + fcoinsform;
                System.out.println(id_encontrado);
//                System.out.println(fcoinsiniciales);



            }



            if (!id_encontrado.isEmpty()) {
                String sql2="INSERT INTO Wallet_table (email,password,total) VALUES (?,?,?)";
                prestmt2 = conn.prepareStatement(sql2);
                prestmt2.setString(1,emailform);
                prestmt2.setInt(2, Integer.parseInt(passwordform));
                prestmt2.setInt(3,fcoinsform);
                prestmt2.executeUpdate();

                prestmt2.close();
//                Usuario ufcoins = new Usuario(usernameform, passwordform, "",fcoinsiniciales, id_encontrado);

                response= Response.ok()

                        .build();
            } else {
                response = Response.status(404)
                        .entity(new ExceptionMessage(404, "User not found"))
                        .build();
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch(ClassNotFoundException cn) {
            cn.printStackTrace();
            response = Response.serverError().build();
        }
        catch (SQLException sq){
            sq.printStackTrace();
            response = Response.serverError().build();
        }
        finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                if(prestmt2!=null) prestmt2.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return response;
    }*/



}