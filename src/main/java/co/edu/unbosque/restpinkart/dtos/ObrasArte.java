package co.edu.unbosque.restpinkart.dtos;

import java.io.File;

public class ObrasArte {

    private String titulo;
    private int precio;
    private File imagen;

    public ObrasArte(String t, int p, File i){
        this.imagen = i;
        this.precio = p;
        this.titulo=t;
    }

    public File getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setImagen(File imagen) {
        this.imagen = imagen;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "DatosObrasArte{" +
                "titulo='" + titulo + '\'' +
                ", precio=" + precio +
                ", imagen=" + imagen +
                '}';
    }
}
