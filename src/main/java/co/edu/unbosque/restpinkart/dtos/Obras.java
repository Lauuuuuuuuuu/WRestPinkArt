package co.edu.unbosque.restpinkart.dtos;

import com.opencsv.bean.CsvBindByName;

public class Obras {

    public Obras(String collection, String title, String author, String price, int likes) {
        this.collection = collection;
        this.title = title;
        this.author = author;
        this.price = price;
        this.likes = likes;
    }

    @CsvBindByName
    private String collection;
    @CsvBindByName
    private String title;
    @CsvBindByName
    private String author;
    @CsvBindByName
    private String price;
    @CsvBindByName
    private int likes;

    //Obtiene el valor correspondiente a la variable collection
    public String getCollection() {return collection;}

    //Asigna un valor a la variable collection
    public void setCollection(String collection) {this.collection = collection;}

    //Obtiene el valor correspondiente a la variable title
    public String getTitle() {return title;}

    //Asigna un valor a la variable title
    public void setTitle(String title) {this.title = title;}

    //Obtiene el valor correspondiente a la variable author
    public String getAuthor() {return author;}

    //Asigna un valor a la variable author
    public void setAuthor(String author) {this.author = author;}

    //Obtiene el valor correspondiente a la variable price
    public String getPrice() {return price;}

    //Asigna un valor a la variable price
    public void setPrice(String price) {this.price = price;}

    //Obtiene el valor correspondiente a la variable likes
    public int getLikes() {
        return likes;
    }

    //Asigna un valor a la variable likes
    public void setLikes(int likes) {
        this.likes = likes;
    }

}
