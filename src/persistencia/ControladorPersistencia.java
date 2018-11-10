package persistencia;

import domain.Aula;
import domain.Aula_Exception;
import domain.PlaEstudis;
import domain.assignatura;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public boolean guardaHorari(String horari, String file) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        boolean ret = false;
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(horari);
            ret = true;
        } catch (IOException e) {
            System.err.println("ERROR: we could not save the Horari");
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return ret;
    }

}
