package co.edu.unbosque.restpinkart.dtos;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class Usuario {
    @CsvBindByName
    private String username;
    @CsvBindByName
    private String password;
    @CsvBindByName
    private String rol;
    private String email;




    public Usuario(String username, String password, String rol,  String email) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.email = email;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "username = " + username + ", password = " + password + ", role =" + rol + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario user = (Usuario) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(rol, user.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, rol);
    }
}
