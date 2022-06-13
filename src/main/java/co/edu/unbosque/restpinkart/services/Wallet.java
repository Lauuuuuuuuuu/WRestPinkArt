package co.edu.unbosque.restpinkart.services;

import co.edu.unbosque.restpinkart.dtos.Usuario;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class Wallet {

    @Context
    ServletContext context;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    // Database credentials
    static final String USER = "postgres";
    static final String PASS = "20031812";

    public Optional<Boolean> buy(String userBuyer, String Fcoins, String art_name){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Registering the JDBC driver
            Class.forName(JDBC_DRIVER);

            // Opening database connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            List<Usuario> users = new AgregarUsuario().getUsers();

            System.out.println(users.toString());

            Usuario user1 = users.stream().filter(user -> userBuyer.equals(user.getEmail())).findFirst().orElse(null);

            user1.toString();

            float numFcoins = Float.parseFloat(Fcoins);

            float numUser = getFcoins(userBuyer);
            System.out.println(numUser);

            float newFcoins = 0;
            String type = "compra";
            Date date = new Date();
            long mili = date.getTime();
            Timestamp time = new Timestamp(mili);

            float aux = numUser-numFcoins;
            if(aux<0){
                return Optional.of(false);
            }

            newFcoins =numFcoins*(-1);

            String sql = "INSERT INTO Wallet_table (email,type,fcoins,registeredat) VALUES (?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user1.getEmail());
            stmt.setString(2, type);
            stmt.setFloat(3,newFcoins);
            stmt.setTimestamp(4,time);
            stmt.executeUpdate();

            stmt.close();


//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return Optional.of(false);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.of(false);
        } catch (SQLException se) {
            se.printStackTrace();
            return Optional.of(false);
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
            return Optional.of(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Optional.of(true);
    }

    public void sale(String username, String Fcoins){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Registering the JDBC driver
            Class.forName(JDBC_DRIVER);

            // Opening database connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            List<Usuario> users = new AgregarUsuario().getUsers();
            Usuario user1 = users.stream().filter(user-> username.equals(user.getEmail())).findFirst().orElse(null);

            float numFcoins = Float.parseFloat(Fcoins);

            String type = "venta";
            Date date = new Date();
            long mili = date.getTime();
            Timestamp time = new Timestamp(mili);

            String sql = "INSERT INTO Wallet_table (email,type,fcoins,registeredat) VALUES (?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user1.getEmail());
            stmt.setString(2, type);
            stmt.setFloat(3,numFcoins);
            stmt.setTimestamp(4,time);

            stmt.executeUpdate();

            stmt.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public float getFcoins(String username){
        Connection conn = null;
        Statement stmt1 = null;
        float numUser = 0;
        try {
            // Registering the JDBC driver
            Class.forName(JDBC_DRIVER);

            // Opening database connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            List<Usuario> users = new AgregarUsuario().getUsers();

            Usuario user1 = users.stream().filter(user-> username.equals(user.getEmail())).findFirst().orElse(null);

            stmt1 = conn.createStatement();
            String query1 = "SELECT fcoins FROM Wallet_table x WHERE x.email = '"+user1.getEmail()+"'";
            ResultSet rs = stmt1.executeQuery(query1);

            while (rs.next()){
                numUser += Float.parseFloat(rs.getString("fcoins"));
            }

            rs.close();
            stmt1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }finally {
            // Cleaning-up environment
            try {
                if (stmt1 != null) stmt1.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return numUser;
    }

    public void modificarDueño(String newOner, int id_art ){
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            // Opening database connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql2 = "UPDATE ownership SET email = ? WHERE id_art = ?";
            prestmt = conn.prepareStatement(sql2);
            prestmt.setString(1,newOner);
            prestmt.setInt(2,id_art);
            prestmt.executeUpdate();
            prestmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            // Cleaning-up environment
            try {
                if (prestmt != null) prestmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
