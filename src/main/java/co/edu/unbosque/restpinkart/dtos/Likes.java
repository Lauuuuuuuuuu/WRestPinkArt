package co.edu.unbosque.restpinkart.dtos;

import com.opencsv.bean.CsvBindByName;

public class Likes {

    @CsvBindByName
    private String email;
    @CsvBindByName
    private String id_art;


    public Likes() {
    }

    public Likes(String email, String id_art) {
        this.email = email;
        this.id_art = id_art;
    }

    public String getEmail() {return email;}

    public String getId_art() {return id_art;}

}
