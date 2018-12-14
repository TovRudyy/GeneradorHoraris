package persistencia;

import domain.Aula;
import domain.Aula_Exception;
import domain.PlaEstudis;
import domain.assignatura;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Controlador de el lector de Aules i de pla d'estudis.
 * @author Olek
 */

public class ControladorPersistencia {

    /**
     * Llegeix de un fitxer diversos plans d'estudis.
     * @return Retorna una array amb tots els plans d'estudis llegits.
     */
    public ArrayList<PlaEstudis> llegeixDadesPE() {
        ArrayList<PlaEstudis> ret = new ArrayList<PlaEstudis>();
        try {
            ret = Lector_Pla_JSON.llegirCarpetaPlans();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Llegeix de un fitxer un pla d'estudis concret.
     * @param file Ruta del fitxer.
     * @return La instancia del pla d'estudis creat.
     */
    public PlaEstudis llegeixPE(String file) {
        PlaEstudis ret = null;
        try {
            ret = Lector_Pla_JSON.llegirPlaEstudis(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Llegeix de un fitxer un conjunt d'aules.
     * @return Un map amb la informacio de les diferents aules.
     */
    public Map<String, Aula> llegeixDadesAules() {
        System.out.println("Llegim la carpeta per defecte");
        Map<String, Aula> ret = new TreeMap<>();
        try {
            ret = Lector_Aules_JSON.llegirCarpetaAules();
        } catch (Aula_Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Llegeix de un fitxer un conjunt d'aules.
     * @param path Ruta al fitxer.
     * @return Les instancies de aules creades.
     */
    public Map<String, Aula> llegeixFitxerAula(String path) {
        Map<String, Aula> noves = new TreeMap<>();
        try {
            noves = Lector_Aules_JSON.llegirAules(path);
        } catch (ParseException | IOException | Aula_Exception e) {
            e.printStackTrace();
        }
        return noves;
    }

    /**
     * Llegeix de un fitxer un conjunt d'assignatures.
     * @param path Ruta al fitxer.
     * @return Les instancies d'assignatura creades.
     */
    public ArrayList<assignatura> llegeixAssignatura(String path) {
        ArrayList<assignatura> noves = new ArrayList<assignatura>();
        try {
            noves = Lector_Pla_JSON.llegeixAssignatura(path);
            return noves;
        } catch (Exception e) {
            e.printStackTrace();
            return noves;
        }
    }

    /**
     * Guarda a una ruta donada un horari en format txt.
     * @param horari El horari en format String
     * @param file La ruta on voleu guardar-ho
     * @return Una string amb el horari
     */
    public String guardaHorari(String horari, String file) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        String ret  = "data/Horaris/" + file;
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

    /**
     * Mostra per pantalla un horari donat a partir del seu fitxer.
     * @param file Ruta al fitxer
     */
    public String visualitzaHorari(String file) {
        file = "data/Horaris/" + file;
        String result = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            System.out.println("\n\n");
            String line = null;
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
        for (File f : horaris)
            System.out.print(f.getName() + " ");
        System.out.println();
    }

    public String guardaHorariGUI(String horari, String path) {
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
