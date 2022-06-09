package co.edu.unbosque.restpinkart.services;
import co.edu.unbosque.restpinkart.dtos.ExceptionMessage;
import co.edu.unbosque.restpinkart.dtos.Usuario;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static co.edu.unbosque.restpinkart.services.Wallet.*;


public class AgregarUsuario {

    private static String ruta = "usuarios.csv";

    public List<Usuario> getUsers() throws IOException {

        List<Usuario> usuarios = new ArrayList<Usuario>() ;

        Connection conn = null;
        Statement stmt = null;


        String namers ;
        String passwordrs ;
        String rolrs ;
        String emailrs;
        try {

            Class.forName(JDBC_DRIVER);
            System.out.println("intento conectarme a la base de datos");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            String sql = "SELECT * FROM user_arts ";
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                namers =rs.getString("name");
                passwordrs = rs.getString("password");
                rolrs = rs.getString("rol");
                emailrs = rs.getString("email");

                Usuario temp = new Usuario(namers,passwordrs,rolrs,emailrs);
                usuarios.add(temp);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch(ClassNotFoundException cn) {
            cn.printStackTrace();
        }
        catch (SQLException sq){
            sq.printStackTrace();
        }
        finally {

            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return usuarios;
    }

//    public void crearUsuario(String username, String password, String role, String path, String s) throws IOException {
//        String newLine = username + "," + password + "," + role + "," + "0" + "\n";
//
//        System.out.println(path + File.separator + "resources" + File.separator + "users.csv" + "Create");
//        FileOutputStream os = new FileOutputStream(path + "resources" + File.separator + "users.csv", true);
//        os.write(newLine.getBytes());
//        os.close();
//    }


//    public Usuario crearUsuario(String username, String password, String role, int coins, String path, int id) throws IOException {
//        String newLine = "\n" + username + "," + password + "," + role + "," + coins ;
//
//        System.out.println(path + File.separator + "resources" + File.separator + "users.csv" + "Create");
//        FileOutputStream os = new FileOutputStream(path + "WEB-INF/classes" + File.separator + "usuarios.csv",true);
//        os.write(newLine.getBytes());
//        os.close();
////   return new Usuario(username,password,rol,email);
//    }


//    public void crearUsuario(String username, String password, String role, String path, boolean append) throws IOException {
//        String newLine = "\n"+username + "," + password + "," + role + "," + "0" + "\n";
//
//        System.out.println(path + File.separator + "resources" + File.separator + "users.csv" + "Create");
//        FileOutputStream os = new FileOutputStream(path  + "WEB-INF/classes" + File.separator + "usuarios.csv", append);
//        os.write(newLine.getBytes());
//        os.close();
//    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

}

