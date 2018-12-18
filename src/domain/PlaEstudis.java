package domain;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * PlaEstudis permet agrupar les Assignatures en conjunts de dades independents d'altres
 * plans d'estudis.
 * @author Olek
 * Date: 06/10/2018
 */
public class PlaEstudis implements Serializable {

    /**Atributs **/
    private String id;  //Acrònim del Pla d'Estudis
    private TreeMap<String,assignatura> assignatures = new TreeMap<>(); //Assignatures pertanyents al pla d'estudis
    private Horari horari = null;
    private HashMap<String, ArrayList<RestriccioFlexible>> restriccionsModificables = new HashMap<>();

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
    public boolean generaHorari (Map<String, Aula> aules) {
        //Timer start
        Instant start = Instant.now();

        LinkedList<assignacio> assignacions = new LinkedList<assignacio>();
        for (Map.Entry<String, assignatura> assig : assignatures.entrySet()) {
            assignatura a = assig.getValue();
            a.noSolapis_Teoria_i_Problemes();
            assignacions.addAll(a.getAssignacions(aules));
        }

        afegirRestriccionsNivell(); //afeim a les assignatures les seves restriccions de nivell

        //aqui tenim totes les assignacions totals
        horari = new Horari (assignacions, aules, restriccionsModificables);
        boolean ret;
        boolean b = horari.findHorari();
        ret = b;
        if (! b) horari = null; //vol dir que com que l'horari no es correcte simplement el borrem

        //Timer end
        Instant end = Instant.now();
        long ElapsedTime = Duration.between(start, end).toMillis();
        System.err.println("DEBUG: l'algorisme ha tardat (elapsed time) " + ElapsedTime + " ms");
        return ret;
    }


    /**
     * Afegeix a cadascuna de les assignatures, els seus nous correquisits.
     * @param c Una array de strings que indiquen els diferents identificadors dels correquisits. (el primer sera el identificador de la primer assignatura i la segona el de la segona)
     */
    public void afegirCorrequisits (String[] c) {
        String primera = c[0];
        String segona  = c[1];
        if (! (assignatures.containsKey(primera)) | !(assignatures.containsKey(segona))) {
            System.err.println("No es pot afegir perque una de les assignatures no existeix");
            return;
        }
        else {
            assignatures.get(primera).addCorrequisit(segona);
            assignatures.get(segona).addCorrequisit(primera);
        }
    }


    public void eliminarCorrequisit (String[] c)
    {
        String primera = c[0];
        String segona  = c[1];
        if (! (assignatures.containsKey(primera)) | !(assignatures.containsKey(segona))) {
            System.err.println("No es pot afegir perque una de les assignatures no existeix");
            return;
        }
        else {
            assignatures.get(primera).eliminarCorrequisit(segona);
            assignatures.get(segona).eliminarCorrequisit(primera);
        }
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


    //CREACIO I TRACTAMENT DE RESTRICCIONS FLEXIBLES

    /**
     * Ens permetra anar afegint restriccions modificables en el map amb totes les restriccions i que després
     * passarem al horari a la hora de generar les seves opcions.
     * @param r
     * @param idAssigIGrup
     */
    public void afegirRestriccioFlexible (RestriccioFlexible r, String idAssigIGrup) {
        restriccionsModificables.putIfAbsent(idAssigIGrup, new ArrayList<>());
        restriccionsModificables.get(idAssigIGrup).add(r);
    }


    public void eliminarRestriccioFlexible (String idAssigGrup)
    {
        restriccionsModificables.remove(idAssigGrup);
    }



    /**
     * @return El conjunt total de restriccions associades al pla d'estudis.
     */
    public ArrayList<String> getRestriccionsFlexibles ()
    {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, ArrayList<RestriccioFlexible>> r : restriccionsModificables.entrySet())
        {
            String a = "El grup " + r.getKey();
            result.add(a);
        }
        return result;
    }



    //FUNCIONS UTILS PER A INTERACTUAR AMB EL CONTROLADOR QUAN REP REQUESTS DE LA INTERFICIE

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


    public LinkedList<LinkedList<Queue<String>>> getHorariSencer() {
        if (horari == null) {
            System.err.println("ERROR: horari és null!");
            return new LinkedList<>();
        }
        return horari.getHorariSencer();
    }

    public String toStringSencer() {
        return horari.toStringSencer();
    }


    public boolean existsHorari() {
        return (horari != null);
    }


    public boolean hasAssignatura(String assig) {
        return assignatures.containsKey(assig);
    }


    public void removeAssig(String assig) {
        assignatures.remove(assig);
    }


    public boolean existsGrupAssignatura(String assig, String grup) {
        return assignatures.get(assig).hasGrup(grup);
    }


    public void esborrarGrupAssignatura(String assig, String grup) {
        assignatures.get(assig).esborraGrup(grup);
    }


    public void setNomAssigantura(String assig, String newValue) {
        assignatures.get(assig).setNom(newValue);
    }


    public void setNivellAssigantura(String assig, int nivell) {
        assignatures.get(assig).setNivell(nivell);
    }


    public void setQtClassesTeoriaAssigantura(String assig, int qt) {
        assignatures.get(assig).setQtClassesTeoria(qt);
    }


    public void setDuradaClassesTeoriaAssigantura(String assig, int qt) {
        assignatures.get(assig).setDuradaClassesTeoria(qt);
    }

    public void setQtClassesProblemesAssigantura(String assig, int qt) {
        assignatures.get(assig).setQtClassesProblemes(qt);
    }


    public void setDuradaClassesProblemesAssigantura(String assig, int qt) {
        assignatures.get(assig).setDuradaClassesProblemes(qt);
    }


    public void setQtClassesLaboratoriAssigantura(String assig, int qt) {
        assignatures.get(assig).setQtClassesLaboratori(qt);
    }


    public void setDuradaClassesLaboratoriAssigantura(String assig, int qt) {
        assignatures.get(assig).setDuradaClassesLaboratori(qt);
    }

    public void setCapacitatGrupAssignatura(String assig, String grup, int qt) {
        System.err.println("DEBUG: estas apuntant a l'assignatura " + assig);
        assignatures.get(assig).setCapacitatGrup(grup, qt);
    }

    public void setHorariGrupAssignatura(String assig, String grup, String valor) {
        assignatures.get(assig).setHorariGrup(grup, valor);
    }

    public void setTipusGrupAssignatura(String assig, String grup, Tipus_Aula tipusAula) {
        assignatures.get(assig).setTipusGrup(grup, tipusAula);

    }

//DE MOMENT S'AFEGEIXEN PERO NO ES MOSTREN AL HORARI

    /**
     * @param a La assignatura a afegir
     * @return Retorna true si l'hem pogut afegir, i false altrament
     */
    public boolean afegirAssignatura (assignatura a)
    {
        String id = a.getId();
        if (assignatures.containsKey(id)) return false;
        else {
            assignatures.put(id, a);
        }
        return true;
    }

    public boolean afegirGrupAssignatura (String assignatura, grup g)
    {
        assignatura a = assignatures.get(assignatura);
        return a.afegirGrup(g);
    }


}
