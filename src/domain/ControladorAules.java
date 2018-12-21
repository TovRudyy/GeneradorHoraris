package domain;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


/**
 * Controlador del aulari permetent tant llegir com modificar el aulari.
 * @author Olek
 */

public class ControladorAules {

    static Map<String, Aula> Aulari = new TreeMap<>();
    static final String EscenaAules="/Aulari.json";

    /**
     * Crea un nou ControladorAules amb les dades que llegeix.
     */
    public ControladorAules(){
            Map<String, Aula> noves = ControladorDades.llegeixDadesAules();
            for (String key : noves.keySet())
                afegirAulaSiNoExisteix(key, noves.get(key));
    }

    /**
     * @return Una arrayList amb la informació de tots els aularis.
     */
    public ArrayList<String> getInfoAulari(){
        ArrayList<String> ret = new ArrayList<String>();
        for (Aula aula : Aulari.values()) {
            ret.add(aula.toString());
        }
        return ret;
    }

    /**
     * Llegeix una nova aula i l'afegeix a l'aulari.
     */
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
            if(capacitat < 0) throw new NullPointerException();
        }
        catch (NumberFormatException e) {
            System.err.println("ERROR: la capacitat ha de ser un natural" + e);
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
        System.err.print("DEBUG: s'ha afegit la nova aula " + id);
    }


    /**
     * Llegeix un fitxer i processa les dades amb totes les aules d'un aulari.
     */
    public void llegeixFitxerAula() {
        Scanner reader = new Scanner(System.in);
        String arg;
        System.out.println("GH: introdueix el PATH del fitxer d'aules:");
        arg = reader.next();
        Map<String, Aula> noves = llegeixFitxer(arg);
        for (String key : noves.keySet()) {
            afegirAulaSiNoExisteix(key, noves.get(key));
        }
    }

    /**
     * @param file Ruta per arribar al fitxer.
     * @return Un map amb la informacio del fitxer que ha llegit
     */
    private Map<String, Aula> llegeixFitxer(String file) {
        Map<String, Aula> aules = new TreeMap<>();
        try {
            aules =  ControladorDades.llegeixAules(file);
        }catch (IOException | ClassNotFoundException | ParseException | Aula_Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return aules;
    }


    /**
     * Comprova si una aula ja la tenim guardada en el nostre aulari, i en cas negatiu, l'afegeix.
     * @param key Identificador de l'aula
     * @param value Instància de la classe Aula.
     */
    public void afegirAulaSiNoExisteix(String key, Aula value) {
            if (Aulari.putIfAbsent(key, value) != null)
                System.err.print("WARNING: classrom " + key + " already exists!");
            //else
            //    System.err.print("DEBUG: s'ha afegit l'aula " + key + "\n");
    }


    /**
     * Comprova si l'aula amb aquest identificador existeix a l'aulari, i en cas positiu, l'elimina.
     * @param id Identificador de l'aula
     */
    public void eliminarAula(String id) {
        if (Aulari.remove(id) == null) {
            System.err.println("WARNING: classroom " + id + " does not exists");
            return;
        }
        System.err.println("DEBUG: s'ha eliminat l'aula " + id);

    }

    /**
     * @return Retorna el map amb totes les aules de l'aulari.
     */
    public static Map<String, Aula> getAules() {
        return Aulari;
    }


    /**
     * Restaura el aulari al estat original, llegint directament un altre cop el fitxer original.
     */
    public void resetData() {
        Aulari.clear();
        Map<String, Aula> noves = ControladorDades.llegeixDadesAules();
        for (String key : noves.keySet())
            afegirAulaSiNoExisteix(key, noves.get(key));
        System.err.println("DEBUG: s'han restaurat les dades de les Aules");
    }

    /**
     * Elimina totes les aules del aulari guardat.
     */
    public void eliminarTotesAules() {
        Aulari.clear();
    }


    /**
     * Carrega el aulari i el pla d'estudis de la escena que hem carregat.
     * @param dir Ruta d'acces a la carpeta de la escena
     */
    public void carregaEscena(String dir) {
        Aulari.clear();
        String file = dir + EscenaAules;
        Aulari = llegeixFitxer(file);
    }

    /**
     * Modifiquem la capacitat de l'aula
     * @param id Identificador
     * @param nou Nova capacitat.
     */
    public void setCapacitatAula(String id, int nou) {
        Aulari.get(id).setCapacitat(nou);
    }

    /**
     * Modifiquem el identificador de l'aula
     * @param id Identificador
     * @param newValue Nou id
     * @return un bolea indicant si hem pogut modificarlo o false si no, ja que ja existeix el identificador indicat.
     */
    public boolean setIdentificadorAula(String id, String newValue) {
        if (Aulari.containsKey(newValue))
            return false;
        Aula aux = Aulari.get(id);
        Aulari.remove(id);
        aux.setId(newValue);
        Aulari.put(aux.getId(), aux);
        return true;
    }

    /**
     * Modifiquem el tipus de una aula
     * @param id Identificador
     * @param tipus Tipus
     */
    public void setTipusAula(String id, String tipus) {
        Aulari.get(id).setTipus(tipus);
    }

    /**
     * Afegim una aula
     * @param id Identificador
     * @param capacitat Capacitat
     * @param tipus Tipus
     * @return True si l'hem pogut afegir o false altrament.
     */
    public boolean afegirAula(String id, int capacitat, String tipus) {
        if (Aulari.containsKey(id))
            return false;
        Aula nova = null;
        try {
            nova = new Aula(id, capacitat, Tipus_Aula.string_to_Tipus_Aula(tipus));
        } catch (Aula_Exception e) {
            e.printStackTrace();
            return false;
        }
        Aulari.put(id, nova);
        return true;
    }

    /**
     * Eliminem l'aula amb identificador id.
     * @param id Identificador.
     */
    public void removeAula(String id) {
        Aulari.remove(id);
    }

    /**
     * Esborrem tot el aulari.
     */
    public void borrarAulari() {
        Aulari.clear();
    }

    /**
     * Carreguem tot el fitxer d'aules a partir de un path
     * @param absolutePath Path al fitxer.
     */
    public void carregarFitxerAules(String absolutePath) {
        Map<String, Aula> noves = llegeixFitxer(absolutePath);
        for (String key : noves.keySet()) {
            afegirAulaSiNoExisteix(key, noves.get(key));
        }
    }

    /**
     * Guardem un aulari a un path
     * @param path Path per guardar el aulari.
     */
    public void guardaAulari(String path) {
        try {
            ControladorDades.guarda(this.Aulari, path);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: error al guardar aulari!");
        }
    }
}
