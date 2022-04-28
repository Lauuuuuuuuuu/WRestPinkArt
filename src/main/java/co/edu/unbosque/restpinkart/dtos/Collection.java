package co.edu.unbosque.restpinkart.dtos;

import com.opencsv.bean.CsvBindByName;

public class Collection {


    @CsvBindByName
    private String username;
    @CsvBindByName
    private String collection;
    @CsvBindByName
    private ObrasArte obra;

    public Collection(String colecction, ObrasArte obra){
        this.obra=obra;
        this.collection = colecction;
    }

    public String getUsername() {return username;}

    public String getCollection() {return collection;}


}