package co.edu.unbosque.restpinkart.dtos;

import com.opencsv.bean.CsvBindByName;

public class ObrasArray {

    public ObrasArray(String collection, String title, String author, int price, int likes, String file, int id_art) {
        this.collection = collection;
        this.title = title;
        this.author = author;
        this.price = price;
        this.likes = likes;
        this.file = file;
        this.id_art = id_art;
    }

    @CsvBindByName
    private String collection;
    @CsvBindByName
    private String title;
    @CsvBindByName
    private String author;
    @CsvBindByName
    private int price;
    @CsvBindByName
    private int likes;
    private String file;
    private int id_art;

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getId_art() {
        return id_art;
    }

    public void setId_art(int id_art) {
        this.id_art = id_art;
    }
}
