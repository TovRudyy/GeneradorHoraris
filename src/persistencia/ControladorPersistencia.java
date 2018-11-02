package persistencia;

import domain.Aula;
import domain.Aula_Exception;
import domain.PlaEstudis;
import domain.assignatura;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ControladorPersistencia {

    Lector_Aules_JSON   dades_aules = new Lector_Aules_JSON();
    Lector_Pla_JSON     dades_plans = new Lector_Pla_JSON();

    public ArrayList<PlaEstudis> llegeixDadesPE() {
        ArrayList<PlaEstudis> ret = new ArrayList<>();
        try {
            ret = dades_plans.llegirCarpetaPlans();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Map<String, Aula> llegeixDadesAules() {
        Map<String, Aula> ret = new TreeMap();
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
            noves = dades_aules.llegirAules(path);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Aula_Exception e) {
            e.printStackTrace();
        }
        return noves;
    }

    public assignatura llegeixAssignatura(String path) {
        return dades_plans.llegeixAssignatura(path);
    }

}
