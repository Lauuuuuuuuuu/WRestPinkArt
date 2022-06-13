package co.edu.unbosque.restpinkart.services;

import co.edu.unbosque.restpinkart.dtos.Likes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeService {

    private Connection conn;

    public LikeService(Connection conn) {
        this.conn = conn;
    }

    public int getIdArt(String imagepath) {

        PreparedStatement stmt = null;
        // Data structure to map results from database
        int id_art = 0;
        try {
            stmt = conn.prepareStatement("SELECT \n" +
                    "\tid_art\n" +
                    "\tFROM arts_table arte\n" +
                    "\t\twhere imagepath = ?;");

            String imagepathFull = "artes\\"+imagepath;

            System.out.println("getId = " + imagepathFull);

            stmt.setString(1, imagepathFull);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                id_art = rs.getInt("id_art");
            }


            // Closing resources
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return id_art;
    }


    // Obtiene el número de likes de un arte
    public int getLikesArt(String imagepath) {

        PreparedStatement stmt = null;
        // Data structure to map results from database
        int likes = 0;
        try {
            stmt = conn.prepareStatement("SELECT\n" +
                    "\tCOUNT (*) AS likes\n" +
                    "\tFROM arts_table arte \n" +
                    "\t\tJOIN likes_table likes\n" +
                    "\t\t\tON arte.id_art = likes.id_art\n" +
                    "\t\t\tAND arte.imagepath = ?\n" +
                    "\tGROUP BY imagepath;");

            stmt.setString(1, "artes\\"+imagepath);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                likes = rs.getInt("likes");
            }
            // Closing resources
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return likes;
    }

    // Obtiene si el usuario le dio like a un arte en especifico
    public boolean getUserLike(int id_art, String email) {


        PreparedStatement stmt = null;
        // Data structure to map results from database
        int likes = 0;
        try {
            stmt = conn.prepareStatement("SELECT \n" +
                    "COUNT (*) AS likes\n" +
                    "from likes_table\n" +
                    "\tWHERE email = ?\n" +
                    "\t\tAND id_art = ?;");

            System.out.println(email+" , "+ id_art);

            stmt.setString(1, email);
            stmt.setInt(2, id_art);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                likes = rs.getInt("likes");
                System.out.println("0 = false ... resultado = "+likes);
            }



            // Closing resources
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        if(likes == 0){
            return false;
        }
        return true;
    }

    public void generarLike(Likes likes){


        PreparedStatement stmt = null;
        try {
            // Executing a SQL query
            stmt = this.conn.prepareStatement("INSERT INTO likes_table (email, id_art)\n" +
                    "VALUES (?,?)");

            stmt.setString(1,likes.getEmail());
            stmt.setInt(2, Integer.parseInt(likes.getId_art()));

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    public void quitarLike(Likes likes){
        PreparedStatement stmt = null;
        try {
            // Executing a SQL query

            stmt = this.conn.prepareStatement("DELETE from likes_table\n" +
                    "\tWHERE email = ?" +
                    "\t\tAND id_art = ?;");

            stmt.setString(1,likes.getEmail());
            stmt.setInt(2, Integer.parseInt(likes.getId_art()));

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}
