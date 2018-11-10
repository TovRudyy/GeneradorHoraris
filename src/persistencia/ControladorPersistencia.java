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

public class ControladorPersistencia {

    public ArrayList<PlaEstudis> llegeixDadesPE() {
        ArrayList<PlaEstudis> ret = new ArrayList<>();
        try {
            ret = Lector_Pla_JSON.llegirCarpetaPlans();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public PlaEstudis llegeixPE(String file) {
        PlaEstudis ret = null;
        try {
            ret = Lector_Pla_JSON.llegirPlaEstudis(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Map<String, Aula> llegeixDadesAules() {
        Map<String, Aula> ret = new TreeMap<>();
        try {
            ret = Lector_Aules_JSON.llegirCarpetaAules();
        } catch (Aula_Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Map<String, Aula> llegeixFitxerAula(String path) {
        Map<String, Aula> noves = new TreeMap<>();
        try {
            noves = Lector_Aules_JSON.llegirAules(path);
        } catch (ParseException | IOException | Aula_Exception e) {
            e.printStackTrace();
        }
        return noves;
    }

    public ArrayList<assignatura> llegeixAssignatura(String path) {
        ArrayList<assignatura> noves = new ArrayList<>();
        try {
            noves = Lector_Pla_JSON.llegeixAssignatura(path);
            return noves;
        } catch (Exception e) {
            e.printStackTrace();
            return noves;
        }
    }

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

    public void visualitzaHorari(String file) {
        file = "data/Horaris/" + file;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println("ERROR: we could not visualize the file");
            e.printStackTrace();
        }
    }

    public void mostraFitxersHoraris() {
        File dir = new File("data/Horaris");
        File[] horaris = dir.listFiles();
        for (File f : horaris)
            System.out.print(f.getName() + " ");
        System.out.println();
    }

}
