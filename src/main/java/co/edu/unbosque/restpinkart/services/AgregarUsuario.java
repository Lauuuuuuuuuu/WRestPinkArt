package co.edu.unbosque.restpinkart.services;
import co.edu.unbosque.restpinkart.dtos.Usuario;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class AgregarUsuario {

    private static String ruta = "usuarios.csv";

    public List<Usuario> getUsers() throws IOException {

        List<Usuario> usuarios;

        try (InputStream is = AgregarUsuario.class.getClassLoader()
                .getResourceAsStream("Usuarios.csv")) {

            HeaderColumnNameMappingStrategy<Usuario> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Usuario.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<Usuario> csvToBean = new CsvToBeanBuilder<Usuario>(br)
                        .withType(Usuario.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                usuarios = csvToBean.parse();
            }
        }

        return usuarios;
    }

//    public void crearUsuario(String username, String password, String role, String path, String s) throws IOException {
//        String newLine = username + "," + password + "," + role + "," + "0" + "\n";
//
//        System.out.println(path + File.separator + "resources" + File.separator + "users.csv" + "Create");
//        FileOutputStream os = new FileOutputStream(path + "resources" + File.separator + "users.csv", true);
//        os.write(newLine.getBytes());
//        os.close();
//    }

    public Usuario crearUsuario(String username, String password, String role, String coins, String path) throws IOException {
        String newLine = "\n" + username + "," + password + "," + role + "," + coins ;

        System.out.println(path + File.separator + "resources" + File.separator + "users.csv" + "Create");
        FileOutputStream os = new FileOutputStream(path + "WEB-INF/classes" + File.separator + "usuarios.csv",true);
        os.write(newLine.getBytes());
        os.close();
   return new Usuario(username,password,role,coins);
    }

//    public void crearUsuario(String username, String password, String role, String path, boolean append) throws IOException {
//        String newLine = "\n"+username + "," + password + "," + role + "," + "0" + "\n";
//
//        System.out.println(path + File.separator + "resources" + File.separator + "users.csv" + "Create");
//        FileOutputStream os = new FileOutputStream(path  + "WEB-INF/classes" + File.separator + "usuarios.csv", append);
//        os.write(newLine.getBytes());
//        os.close();
//    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

}

