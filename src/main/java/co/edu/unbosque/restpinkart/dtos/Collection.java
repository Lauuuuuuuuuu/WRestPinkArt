package co.edu.unbosque.restpinkart.dtos;

public class Collection {

    private String Username;
    private ObrasArte obra;

    public Collection(String username, ObrasArte obra){
        this.obra = obra;
        this.Username = username;

    }
}
