package persistencia;

import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Controlador de el lector de Aules i de pla d'estudis.
 * @author Olek
 */

public class ControladorPersistencia {

    public static ArrayList<Object> llegirCarpetaPlansJSON() {
        return Lector_Fitxers.llegirCarpetaPlansJSON();
    }

    public static ArrayList<Object> llegirCarpetaPlansSerialized() {
        return Lector_Fitxers.llegirCarpetaPlansSerialized();
    }

    public static Object llegirJSON(String path) throws IOException, ParseException {
        return Lector_Fitxers.llegirJSON(path);
    }

    public static ArrayList<Object> llegirCarpetaAulesJSON() {
        return Lector_Fitxers.llegirCarpetaAulesJSON();
    }

    public static ArrayList<Object> llegirCarpetaAulesSerialized() {
        return Lector_Fitxers.llegirCarpetaAulesSerialized();
    }


    public static Object carrega(String path) throws IOException, ClassNotFoundException {
        return Serialitzador.deserialize(path);
    }

    public static void guarda(Object object, String path) throws IOException {
        Serialitzador.serialize(object, path);
    }




    /**
     * Mostra per pantalla un horari donat a partir del seu fitxer.
     * @param file Ruta al fitxer
     */
    public String visualitzaHorari(String file) {
        file = "data/Horaris/" + file;
        String result = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            System.out.println("\n\n");
            String line;
            String missatge1 = "El path de l'escenari corresponent es : ";  //missatge si sí que te un path
            String missatge2 = "L'identificador del pla d'estudis es : ";   //missatge en el cas que no tingui un path
            while ((line = br.readLine()) != null) {
                if (line.contains(missatge1))
                    result = line.substring(missatge1.length());

                else if(line.contains(missatge2))
                    result = line.substring(missatge2.length());

                else
                    System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println("ERROR: we could not visualize the file");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Mostra per pantalla les diferents opcions de horaris que tenim.
     */
    public void mostraFitxersHoraris() {
        File dir = new File("data/Horaris");
        File[] horaris = dir.listFiles();
        for (File f : Objects.requireNonNull(horaris))
            System.out.print(f.getName() + " ");
        System.out.println();
    }

    public static String guardaHorariGUI(String horari, String path) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        String ret  = path;
        try {
            fw = new FileWriter(ret);
            bw = new BufferedWriter(fw);
            bw.write(horari);
        } catch (IOException e) {
            System.err.println("ERROR: we could not save Horari");
            e.printStackTrace();
            ret = null;
        } finally {
            try {
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {
                ret = null;
                ex.printStackTrace();
            }
        }
        return ret;
    }

}
