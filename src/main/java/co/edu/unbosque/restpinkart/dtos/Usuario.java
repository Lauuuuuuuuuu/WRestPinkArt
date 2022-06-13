package co.edu.unbosque.restpinkart.dtos;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class Usuario {
    private String email;
    private String username;
    private String password;
    private String role;
    private int coins;

    public Usuario (){

    }
    public Usuario(String email, String username, String password, String role, int coins) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.coins = coins;
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


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "User{" + "username = " + username + ", password = " + password + ", role =" + role + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario user = (Usuario) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(role, user.role);
    }


}
