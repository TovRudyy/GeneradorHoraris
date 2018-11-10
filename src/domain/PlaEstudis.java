package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rudyy, Oleksandr
 * Date: 06/10/2018
 */

public class PlaEstudis {
    private String id;  //Acrònim del Pla d'Estudis
    private HashMap<String,assignatura> assignatures = new HashMap<>(); //Assignatures pertanyents al pla d'estudis
    private Horari h = null;

    /** Creadores **/

    /**
     * Crea un pla d'estudis amb aquest identificador.
     * @param id Identificador del pla d'estudis.
     */
    public PlaEstudis(String id) {
        this.id = id;
    }


    /**
     * Afegeix aquesta assignatura al conjunt d'assignatures global del pla d'estudis.
     * @param assig Una instància de la classe Assignatura.
     */
    public void addAssignatura(assignatura assig) {
         assignatures.put(assig.getId(), assig);
    }


    /**
     * @return el identificador del Pla d'Estudis
     */
    public String getID() {
        return this.id;
    }


    /**
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


    /**
     * @return Ens indica si aquest pla d'estudis ja té un horari prèviament generat.
     */
    public boolean hasHorari() {
        return (h != null);
    }


    /**
     * Mostra per pantalla la informació de totes les assignatures, incloent tots els seus grups, assignacions i Classes.
     */
    public void showAssignatures () {
        for (Map.Entry<String, assignatura> assig : assignatures.entrySet()) {
            assignatura a = assig.getValue();
            System.out.println(a.getId() + ":" + a.getNom() + ":" + a.getNivell() + "\n");
            a.showGrups();
            a.showClasses();
            a.getAssignacions();
        }

    }

    /**
     * @return Una string amb tota la informació de totes les assignatures que conte el pla d'estudis.
     */

    public String toStringAssignatures() {
        String ret = new String();
        for (assignatura assig : assignatures.values()) {
            ret = ret + "---------\n";
            ret = ret + assig.getId() + "," + assig.getNom() + "\nNivell:" +
                    assig.getNivell() + "\n";
        }
        return ret;
    }


    /**
     * Genera un horari per aquest pla d'estudis.
     */
    public void generaHorari () {
        ArrayList<assignacio> assignacions = new ArrayList<>();
        for (Map.Entry<String, assignatura> assig : assignatures.entrySet()) {
            assignatura a = assig.getValue();
            a.noSolapis_Teoria_i_Problemes();
            assignacions.addAll(a.getAssignacions());
        }

        //aqui tenim totes les assignacions totals
        h = new Horari (assignacions);
        h.findHorari();

    }


    /**
     * Afegeix a cadascuna de les assignatures, els seus nous correquisits.
     * @param c Una array de strings que indiquen els diferents identificadors dels correquisits.
     */
    public void afegirCorrequisits (String[] c) {
        String primera = c[0];
        String segona  = c[1];
        assignatures.get(primera).addCorrequisit(segona);
        assignatures.get(segona).addCorrequisit(primera);
    }

    /**
     * Mostra per pantalla el horari d'aquest pla d'estudis.
     */
    public void printHorari(){
        h.printHorari();
    }

    /**
     * @param id Identificador d'una assignatura.
     * @return Indica si en el nostre conjunt d'assignatures n'hi ha una amb aquest identificador.
     */
    public boolean existsAssignatura(String id) {
        return (assignatures.get(id) == null);
    }


    /**
     *  Elimina la assignatura amb aquest identificador del conjunt total d'assignatures del pla d'estudis.
     * @param id Identificador d'una assignatura.
     */

    public void eliminarAssignatura(String id) {
        if (assignatures.remove(id) == null) {
            System.out.println("ERROR: no existeix l'assignatura " + id);
            return;
        }
        System.out.println("DEBUG: s'ha eliminat l'assignatura " + id);
    }

}
