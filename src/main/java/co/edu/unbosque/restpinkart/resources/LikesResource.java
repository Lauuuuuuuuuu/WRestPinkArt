package co.edu.unbosque.restpinkart.resources;

import co.edu.unbosque.restpinkart.dtos.ObrasArray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LikesResource {

    Connection conn = null;
    private ArtsResource arts = new ArtsResource();

    private String UPLOAD_DIRECTORY = "OBRAS";
    PreparedStatement prestmt = null;
    Statement stmt = null;
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    // Database credentials
    static final String USER = "postgres";
    static final String PASS = "1007101050";
    List<ObrasArray> listaObras = new ArrayList<>();



}
