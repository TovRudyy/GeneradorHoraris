package domain;

import persistencia.ControladorPersistencia;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ControladorAules {

    static ControladorPersistencia CtrlDades = new ControladorPersistencia();
    static Map<String, Aula> Aulari = new TreeMap<>();

    public ControladorAules(){
            Map<String, Aula> noves = CtrlDades.llegeixDadesAules();
            for (String key : noves.keySet())
                afegirAulaSiNoExisteix(key, noves.get(key));
    }

    public ArrayList<String> getInfoAulari(){
        ArrayList<String> ret = new ArrayList<String>();
        for (Aula aula : Aulari.values()) {
            ret.add(aula.toString());
        }
        return ret;
    }

    public void afegirNovaAula() {
        Scanner reader = new Scanner(System.in);
        String arg, id;
        int capacitat;
        Tipus_Aula tipus;
        System.out.print("GH: introdueix l'id de l'aula:");
        arg = reader.next();
        if (Aulari.containsKey(arg)) {
            System.err.println("ERROR: classroom " + arg +" already exists!\n");
            return;
        }
        id = arg;
        System.out.print("GH: introdueix capacitat i tipus separats per un espai: ");
        arg = reader.next();
        try {
            capacitat = Integer.parseInt(arg);
        }
        catch (NumberFormatException e) {
            System.err.println("ERROR: la capacitat ha de ser un enter" + e);
            return;
        }
        arg = reader.next();
        try {
            tipus = Tipus_Aula.string_to_Tipus_Aula(arg);
        }
        catch (Aula_Exception e) {
            System.err.println("ERROR: " + e);
            return;
        }
        Aula nova = new Aula(id, capacitat, tipus);
        Aulari.put(id, nova);
        System.err.println("DEBUG: s'ha afegit la nova aula " + id);
    }

    public void llegeixFitxerAula() {
        Scanner reader = new Scanner(System.in);
        String arg;
        System.out.println("GH: introdueix el PATH del fitxer\n");
        arg = reader.next();
        Map<String, Aula> noves = CtrlDades.llegeixFitxerAula(arg);
        for (String key : noves.keySet())
            afegirAulaSiNoExisteix(key, noves.get(key));
    }

    public void afegirAulaSiNoExisteix(String key, Aula value) {
            if (Aulari.putIfAbsent(key, value) != null)
                System.err.println("WARNING: classrom " + key + " already exists!");
            else
                System.err.println("DEBUG: s'ha afegit l'aula " + key);
    }

    public void eliminarAula(String id) {
        if (Aulari.remove(id) == null) {
            System.err.println("WARNING: classroom " + id + " does not exists");
            return;
        }
        System.err.println("DEBUG: s'ha eliminat l'aula " + id);

    }

    public static Map<String, Aula> getAules() {
        return Aulari;
    }

    public void resetData() {
        Aulari.clear();
        Map<String, Aula> noves = CtrlDades.llegeixDadesAules();
        for (String key : noves.keySet())
            afegirAulaSiNoExisteix(key, noves.get(key));
        System.err.println("DEBUG: s'han restaurat les dades de les Aules");
    }

    public void eliminarTotesAules() {
        Aulari.clear();
    }

}
