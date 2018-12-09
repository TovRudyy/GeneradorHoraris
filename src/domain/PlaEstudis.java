package domain;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * PlaEstudis permet agrupar les Assignatures en conjunts de dades independents d'altres
 * plans d'estudis.
 * @author Olek
 * Date: 06/10/2018
 */

public class PlaEstudis implements Serializable {
    /**Atributs **/
    private String id;  //Acrònim del Pla d'Estudis
    private HashMap<String,assignatura> assignatures = new HashMap<>(); //Assignatures pertanyents al pla d'estudis
    private Horari horari = null;


    /** Constructores **/

    /**
     * Crea un pla d'estudis amb aquest identificador.
     * @param id Identificador del pla d'estudis.
     */
    public PlaEstudis(String id) {
        this.id = id;
    }



    /** Metodes **/

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
     * @return Ens indica si aquest pla d'estudis ja te un horari previament generat.
     */
    public boolean hasHorari() {
        return (horari != null);
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
            a.getAssignacions(ControladorAules.getAules());
        }

    }

    /**
     * @return Una string amb tota la informacio de totes les assignatures que conte el pla d'estudis.
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

    public ArrayList<String> getAssignatures() {
        ArrayList<String> ret = new ArrayList<String>();
        for (assignatura a : assignatures.values()) {
            ret.add(a.getId());
        }
        return ret;
    }

    /**
     * @param idAssig Identificador d'una assignatura.
     * @return Una string amb tota la informacio de la assignatura que indica el identificador passat per parametre.
     */
    public String detallsAssignatura(String idAssig) {
        return assignatures.get(idAssig).toStringComplet();
    }


    /**
     * @return Una array de string amb tots els noms de les diferents assignatures del pla d'estudis.
     */
    public String[] toStringNomAssignatures(){
        String[] ret = new String[assignatures.size()];
        int i = 0;
        for (String idAssig : assignatures.keySet()) {
            ret[i] = idAssig;
            i++;
        }
        return ret;
    }

    /**
     * Genera un horari per aquest pla d'estudis.
     * @param aules conjunt d'aules amb les que generar l'horari
     */
    public void generaHorari (Map<String, Aula> aules) {
        //Timer start
        Instant start = Instant.now();

        ArrayList<assignacio> assignacions = new ArrayList<assignacio>();
        for (Map.Entry<String, assignatura> assig : assignatures.entrySet()) {
            assignatura a = assig.getValue();
            a.noSolapis_Teoria_i_Problemes();
            assignacions.addAll(a.getAssignacions(aules));
        }

        afegirRestriccionsNivell(); //afeim a les assignatures les seves restriccions de nivell

        //aqui tenim totes les assignacions totals
        horari = new Horari (assignacions, aules);
        boolean b = horari.findHorari();
        if (! b) horari = null; //vol dir que com que l'horari no es correcte simplement el borrem

        //Timer end
        Instant end = Instant.now();
        long ElapsedTime = Duration.between(start, end).toMillis();
        System.err.println("DEBUG: l'algorisme ha tardat (elapsed time) " + ElapsedTime + " ms");
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
     * Computa totes les assignatures i crea les restriccions de nivell entre les assignatures amb el mateix nivell
     */
    private void afegirRestriccionsNivell () {
        HashMap<Integer, ArrayList<String>> nivellsIassignatures = new HashMap<>();

        for (Map.Entry<String,assignatura> a_aux : assignatures.entrySet()) {
            assignatura a = a_aux.getValue();
            int nivell = a.getNivell();

            nivellsIassignatures.putIfAbsent(nivell, new ArrayList<>());
            nivellsIassignatures.get(nivell).add(a.getId());
        }
        //quan arribem aqui ja tenim un map amb les assignatures agrupades per nivells
        //ara afegim els correquisits

        for (Map.Entry<Integer, ArrayList<String>> n_aux : nivellsIassignatures.entrySet()) {
            ArrayList<String> n = n_aux.getValue();
            int numero_elements = n.size();

            for (int i=0; i < numero_elements; ++i) {
                String actual = n.get(i);
                assignatures.get(actual).addManyCorrequisits(n);    //la funció addManyCorrequisits no s'afegirà a ella mateixa
            }

        }

    }

    /**
     * @return Una string amb el horari corresponent a aquest pla d'estudis.
     */
    public String getHorari() {
        return horari.toString();
    }


    /**
     * @param id Identificador d'una assignatura.
     * @return Indica si en el nostre conjunt d'assignatures n'hi ha una amb aquest identificador.
     */
    public boolean existsAssignatura(String id) {
        return (assignatures.get(id) != null);
    }


    /**
     *  Elimina la assignatura amb aquest identificador del conjunt total d'assignatures del pla d'estudis.
     * @param id Identificador d'una assignatura.
     */
    public boolean eliminarAssignatura(String id) {
        if (assignatures.remove(id) == null) {
            System.err.println("ERROR: no existeix l'assignatura " + id);
            return false;
        }
        System.err.println("DEBUG: s'ha eliminat l'assignatura " + id);
        return true;
    }

    /**
     *
     * @param assig identificador de l'assignatura
     * @return el nom de l'assignatura
     */
    public String getNomAssignatura(String assig) {
       return assignatures.get(assig).getNom();
    }

    public int getNivellAssignatura(String assig) {
        return assignatures.get(assig).getNivell();

    }

    public int getQtClassesTeoriaAssignatura(String assig) {
        return assignatures.get(assig).getQtClassesTeoria();
    }

    public int getDuradaClassesTeoriaAssignatura(String assig) {
        return assignatures.get(assig).getDuradaClassesTeoria();

    }

    public int getQtClassesProblemesAssignatura(String assig) {
        return assignatures.get(assig).getQtClassesProblemes();

    }

    public int getDuradaClassesProblemesAssignatura(String assig) {
        return assignatures.get(assig).getDuradaClassesProblemes();

    }

    public int getQtClassesLaboratoriAssignatura(String assig) {
        return assignatures.get(assig).getQtClassesLaboratori();

    }

    public int getDuradaClassesLaboratoriAssignatura(String assig) {
        return assignatures.get(assig).getDuradaClassesLaboratori();
    }

    public ArrayList<String> getGrupsAssignatura(String assig) {
        return assignatures.get(assig).getGrupsTeoria();
    }

    public int getCapacitatGrup(String grup, String assig) {
        return assignatures.get(assig).getCapacitatGrup(grup);
    }

    public String getHorariGrup(String grup, String assig) {
        return assignatures.get(assig).getHorariGrup(grup);

    }

    public String getTipusAulaGrup(String grup, String assig) {
        return assignatures.get(assig).getTipusAulaGrup(grup);

    }

    public ArrayList<String> getSubgrupsGrup(String grup, String assig) {
        return assignatures.get(assig).getSubgrupsGrup(grup);
    }

    public ArrayList<String> getCorrequisitsAssignatura(String assig) {
        return assignatures.get(assig).getCorrequisits();

    }


    public boolean modificaEntrada (String assig, String grup, DiaSetmana d, int h, DiaSetmana diaNou, int horaNova, String aulaNova) {
        return horari.modificaClasse(assig, grup, d, h, diaNou, horaNova, aulaNova);
    }

}
