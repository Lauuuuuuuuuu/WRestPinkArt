package co.edu.unbosque.restpinkart.dtos;

import com.opencsv.bean.CsvBindByName;

public class Collection {


    @CsvBindByName
    private String username;
    @CsvBindByName
    private String collection;
    @CsvBindByName
    private String obra;

    public String getUsername() {return username;}

    public String getCollection() {return collection;}

    public String getObra() {return obra;}
}
