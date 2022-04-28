package co.edu.unbosque.restpinkart.resources;

import co.edu.unbosque.restpinkart.dtos.ObrasArte;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicioObras {

    private ArrayList<ObrasArte> ArrayObras = new ArrayList<ObrasArte>();

    private static String ruta = "Obras.csv";

    public static Optional<List<ObrasArte>> getObras() throws IOException {
        List<ObrasArte> obrasArtes;
        System.out.printf(ruta);
        try (InputStream is = new FileInputStream(ruta)) {

            MappingStrategy Column;
            HeaderColumnNameMappingStrategy<ObrasArte> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ObrasArte.class);
            Column = strategy;

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                CsvToBean<ObrasArte> csvToBean = new CsvToBeanBuilder<ObrasArte>(br)
                        .withType(ObrasArte.class)
                        .withMappingStrategy(Column)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                obrasArtes = csvToBean.parse();
            }
        }
        return Optional.of(obrasArtes);
    }

    public void crearObra(String titulo, int precio, File rutaImag, String path,boolean append) throws IOException {
        ObrasArte obra = new ObrasArte(titulo,precio,rutaImag);
        String newLine = "\n"+titulo+","+precio+","+rutaImag;
        ArrayObras.add(obra);
        FileOutputStream os = new FileOutputStream(path  + "WEB-INF/classes" + File.separator + "Obras.csv", append);
        os.write(newLine.getBytes());
        os.close();

        System.out.println(ArrayObras.toString()+"en servicio obras.");
    }

    public ArrayList<ObrasArte> getArrayObras() {
        return ArrayObras;
    }
}
