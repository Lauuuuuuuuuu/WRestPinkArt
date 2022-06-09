package co.edu.unbosque.restpinkart.services;
import co.edu.unbosque.restpinkart.dtos.Collection;
import co.edu.unbosque.restpinkart.dtos.Usuario;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Operaciones {

    private static String ruta = "usuarios.csv";

    public List<Usuario> getUsers() throws IOException {

        List<Usuario> usuarios;

        try (InputStream is = Operaciones.class.getClassLoader()
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



//    public Usuario crearUsuario(String username, String password, String role, int coins, String path, int id) throws IOException {
//        String newLine = username + "," + password + "," + role + "," + coins + "\n";
//
//        System.out.println(path + File.separator + "resources" + File.separator + "users.csv" + "Create");
//        FileOutputStream os = new FileOutputStream(path + "WEB-INF/classes" + File.separator + "usuarios.csv",true);
//        os.write(newLine.getBytes());
//        os.close();
////   return new Usuario(username,password,role,coins,id);
////    }


    public List<Collection> getUltimasColecciones() throws IOException {

        List<Collection> collectionList;
        List<Collection> respuesta = new ArrayList<>();


        try (InputStream is = Operaciones.class.getClassLoader()
                .getResourceAsStream("Colecciones.csv")) {

            HeaderColumnNameMappingStrategy<Collection> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Collection.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<Collection> csvToBean = new CsvToBeanBuilder<Collection>(br)
                        .withType(Collection.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                collectionList = csvToBean.parse();
                Collections.reverse(collectionList);

                int numero=6;
                if(collectionList.size()<numero){
                    numero=collectionList.size();
                }
                for(int x=0;x<numero;x++){
                    respuesta.add(collectionList.get(x));
                }

            }
        }

        return respuesta;
    }

    public List<Collection> getColeccionesPorArtista(String username) throws IOException {

        List<Collection> collectionList;

        try (InputStream is = Operaciones.class.getClassLoader()
                .getResourceAsStream("Colecciones.csv")) {

            HeaderColumnNameMappingStrategy<Collection> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Collection.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<Collection> csvToBean = new CsvToBeanBuilder<Collection>(br)
                        .withType(Collection.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                collectionList = csvToBean.parse().stream().filter(collection -> collection.getUsername().equals(username)).collect(Collectors.toList());
            }
        }

        return collectionList;
    }

    public List<Collection> getColecciones() throws IOException {

        List<Collection> collectionList;

        try (InputStream is = Operaciones.class.getClassLoader()
                .getResourceAsStream("Colecciones.csv")) {

            HeaderColumnNameMappingStrategy<Collection> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Collection.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<Collection> csvToBean = new CsvToBeanBuilder<Collection>(br)
                        .withType(Collection.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                collectionList = csvToBean.parse();
            }
        }

        return collectionList;
    }


    public void crearColeccion(String username,String collectionName,String obra) throws IOException {
        String newLine = username + "," + collectionName + "," + obra+"\n";
        String is = Operaciones.class.getClassLoader().getResource("Colecciones.csv").getPath();
        String ruta= is.replace("/WRestPinkArt-1.0-SNAPSHOT/WEB-INF","");
        FileOutputStream os = new FileOutputStream(ruta, true);
        os.write(newLine.getBytes());
        os.close();
    }



    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

}

