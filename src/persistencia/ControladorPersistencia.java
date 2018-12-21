package persistencia;

import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Controlador de el lector de Aules i de pla d'estudis.
 * @author Olek
 */

public final class ControladorPersistencia {

    private ControladorPersistencia() {
    }

    /**
     * @return retorna una llista de tots els objectes llegits dels arxius .json de la carpeta "data/PlaEstudis"
     */
    public static ArrayList<Object> llegirCarpetaPlansJSON() {
        return Lector_Fitxers.llegirCarpetaPlansJSON();
    }

    /**
     * @return retorna una llista de tots els objectes llegits dels arxius .ser de la carpeta "data/PlaEstudis"
     */
    public static ArrayList<Object> llegirCarpetaPlansSerialized() {
        return Lector_Fitxers.llegirCarpetaPlansSerialized();
    }

    /**
     * @param path ruta de l'arxiu .json a llegir
     * @return objecte llegit de l'arxiu
     * @throws IOException el fitxer és un directori, no es pot crear o no es pot obrir
     * @throws ParseException el format de l'arxiu no és correcte
     */
    public static Object llegirJSON(String path) throws IOException, ParseException {
        return Lector_Fitxers.llegirJSON(path);
    }

    /**
     * @return retorna una llista de tots els objectes llegits dels arxius .json de la carpeta "data/Aules"
     */
    public static ArrayList<Object> llegirCarpetaAulesJSON() {
        return Lector_Fitxers.llegirCarpetaAulesJSON();
    }

    /**
     * @return retorna una llista de tots els objectes llegits dels arxius .ser de la carpeta "data/Aules"
     */
    public static ArrayList<Object> llegirCarpetaAulesSerialized() {
        return Lector_Fitxers.llegirCarpetaAulesSerialized();
    }


    /**
     * @param path ruta del fitxer que conte l'objecte serialitzat
     * @return objecte llegit deserialitzat
     * @throws IOException el fitxer és un directori, no es pot crear o no es pot obrir
     * @throws ClassNotFoundException la classe de lobjecte llegit és desconeguda
     */
    public static Object carrega(String path) throws IOException, ClassNotFoundException {
        return Serialitzador.deserialize(path);
    }

    /**
     * @param object objecte que es vol serialitzar
     * @param path ruta del fitxer on s'escriurà el fitxer serialitzat (es crearà o sobreescriurà si cal)
     * @throws IOException el fitxer és un directori, no es pot crear o no es pot obrir
     */
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
