package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Rudyy, Oleksandr
 * Date: 06/10/2018
 */

public class PlaEstudis {
    private String id;  //Acrònim del Pla d'Estudis
    private HashMap<String,assignatura> assignatures = new HashMap<>(); //Assignatures pertanyents al pla d'estudis
    private Horari h = null;

    public PlaEstudis(String id) {
        this.id = id;
    }


    /**
     *
     * @param assig
     *
     */
    public void addAssignatura(assignatura assig) {
         assignatures.put(assig.getId(), assig);
    }

    /**
     *
     * @return el id del Pla d'Estudis
     */
    public String getID() {
        return this.id;
    }

    /**
     *
     * @return un element String[] amb els identificadors de totes les assignatures del pla d'estudis
     */
    public String[] getAssignatures(){
        String[] id_assignatures;
        int i;

        id_assignatures = new String[assignatures.size()];
        i = 0;
        for (Map.Entry<String, assignatura> a : assignatures.entrySet()) {
            id_assignatures[i] = a.getKey();
            i++;
        }
        return id_assignatures;
    }


    public boolean hasHorari() {
        return (h != null);
    }


    public void showAssignatures () {
        for (Map.Entry<String, assignatura> assig : assignatures.entrySet()) {
            assignatura a = assig.getValue();
            System.out.println(a.getId() + ":" + a.getNom() + ":" + a.getNivell() + "\n");
            a.showGrups();
            a.showClasses();
            a.getAssignacions();
        }

    }

    public String toStringAssignatures() {
        String ret = new String();
        for (assignatura assig : assignatures.values()) {
            ret = ret + "---------\n";
            ret = ret + assig.getId() + "," + assig.getNom() + "\nNivell:" +
                    assig.getNivell() + "\n";
        }
        return ret;
    }

    public String detallsAssignatura(String idAssig) {
        return assignatures.get(idAssig).toStringComplet();
    }

    public String[] toStringNomAssignatures(){
        String[] ret = new String[assignatures.size()];
        int i = 0;
        for (String idAssig : assignatures.keySet()) {
            ret[i] = idAssig;
            i++;
        }
        return ret;
    }


    public void generaHorari () {
        ArrayList<assignacio> assignacions = new ArrayList<>();
        for (Map.Entry<String, assignatura> assig : assignatures.entrySet()) {
            assignatura a = assig.getValue();
            a.noSolapis_Teoria_i_Problemes();
            assignacions.addAll(a.getAssignacions());       //AQUI ESTA EL FALLO
        }

        //aqui tenim totes les assignacions totals
        h = new Horari (assignacions);
        h.findHorari();

    }


    public void afegirCorrequisits (String[] c) {
        String primera = c[0];
        String segona  = c[1];
        assignatures.get(primera).addCorrequisit(segona);
        assignatures.get(segona).addCorrequisit(primera);
    }

    public void printHorari(){
        h.printHorari();
    }

    public String getHorari() {
        return h.toString();
    }

    public boolean existsAssignatura(String id) {
        return (assignatures.get(id) != null);
    }

    public void eliminarAssignatura(String id) {
        if (assignatures.remove(id) == null) {
            System.err.println("ERROR: no existeix l'assignatura " + id);
            return;
        }
        System.err.println("DEBUG: s'ha eliminat l'assignatura " + id);
    }

}
