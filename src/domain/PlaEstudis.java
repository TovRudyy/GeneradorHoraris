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
    private HashMap<String, RestriccioFlexible> restriccionsModificables = new HashMap<>();
    private HashMap<String, RestriccioFlexible> restriccionsModificablesActives = new HashMap<>();


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

    /**
     * @return Retorna les seves assignatures.
     */
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
        horari = new Horari (assignacions, aules, restriccionsModificablesActives);
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
        String a = "El grup " + idAssigIGrup + r.getInfo();
        r.setId (idAssigIGrup);
        restriccionsModificables.putIfAbsent(a, r);
        restriccionsModificablesActives.putIfAbsent(a,r);   //afegim als dos conjunts, al de restriccions actives i al de restriccions existents
    }

    /**
     * Elimina la restriccio flexible identificada pel seu identificador.
     * @param t
     */
    public void eliminarRestriccioFlexible (String t)
    {
        restriccionsModificables.remove(t);
    }


    /**
     * Una restriccio flexible passa a no ser activa.
     * @param t
     */
    public void suavitzarRestriccioFlexible (String t)
    {
        restriccionsModificablesActives.remove(t);
    }

    /**
     * Aquella restriccio passa a ser activa en el nostre horari.
     * @param t Tota la informacio de la restriccio que ens permet identificarla
     */
    public void activarRestriccioFlexible (String t)
    {
        RestriccioFlexible r = restriccionsModificables.get(t);
        restriccionsModificablesActives.putIfAbsent(t, r);
    }

    /**
     * Totes les restriccions existents passen a ser actives.
     */
    public void reiniciarRestriccions ()
    {
        restriccionsModificablesActives = restriccionsModificables;
    }


    /**
     * @return El conjunt total de restriccions associades al pla d'estudis.
     */
    public ArrayList<String> getRestriccionsFlexibles ()
    {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, RestriccioFlexible> r : restriccionsModificables.entrySet())
        {
            result.add(r.getKey());
        }
        return result;
    }


    public ArrayList<String> getRestriccionsFlexiblesActives ()
    {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, RestriccioFlexible> r : restriccionsModificablesActives.entrySet())
        {
            result.add(r.getKey());
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


    /**
     * @param assig
     * @return El nivell de la assignatura.
     */
    public int getNivellAssignatura(String assig) {
        return assignatures.get(assig).getNivell();

    }

    /**
     * @param assig
     * @return El numero de les classes de teoria.
     */
    public int getQtClassesTeoriaAssignatura(String assig) {
        return assignatures.get(assig).getQtClassesTeoria();
    }


    /**
     * @param assig
     * @return La durada de teoria de la assignatura.
     */
    public int getDuradaClassesTeoriaAssignatura(String assig) {
        return assignatures.get(assig).getDuradaClassesTeoria();

    }

    /**
     * @param assig
     * @return El numero de les classes de problemes.
     */
    public int getQtClassesProblemesAssignatura(String assig) {
        return assignatures.get(assig).getQtClassesProblemes();

    }


    /**
     * @param assig
     * @return La durada de problemes de la assignatura.
     */
    public int getDuradaClassesProblemesAssignatura(String assig) {
        return assignatures.get(assig).getDuradaClassesProblemes();

    }

    /**
     * @param assig
     * @return El numero de les classes de laboratori.
     */
    public int getQtClassesLaboratoriAssignatura(String assig) {
        return assignatures.get(assig).getQtClassesLaboratori();

    }

    /**
     * @param assig
     * @return La durada dels laboratoris de la assignatura.
     */
    public int getDuradaClassesLaboratoriAssignatura(String assig) {
        return assignatures.get(assig).getDuradaClassesLaboratori();
    }

    /**
     * @param assig
     * @return Els grup de les assignatures.
     */
    public ArrayList<String> getGrupsAssignatura(String assig) {
        return assignatures.get(assig).getGrupsTeoria();
    }


    /**
     * @param grup
     * @param assig
     * @return La capacitat de un grup.
     */
    public int getCapacitatGrup(String grup, String assig) {
        return assignatures.get(assig).getCapacitatGrup(grup);
    }


    /**
     * @param grup
     * @param assig
     * @return El horari de un grup d'una assignatura.
     */
    public String getHorariGrup(String grup, String assig) {
        return assignatures.get(assig).getHorariGrup(grup);

    }

    /**
     * @param grup
     * @param assig
     * @return El tipus d'aula que necessita un grup.
     */
    public String getTipusAulaGrup(String grup, String assig) {
        return assignatures.get(assig).getTipusAulaGrup(grup);

    }

    /**
     * @param grup
     * @param assig
     * @return Els subgrups de un grup.
     */
    public ArrayList<String> getSubgrupsGrup(String grup, String assig) {
        return assignatures.get(assig).getSubgrupsGrup(grup);
    }


    /**
     * @param assig
     * @return Els correquisits d'una assignatura.
     */
    public ArrayList<String> getCorrequisitsAssignatura(String assig) {
        return assignatures.get(assig).getCorrequisits();

    }

    /**
     * Modifica una entrada del horari per una altre.
     * @param assig
     * @param grup
     * @param d
     * @param h
     * @param diaNou
     * @param horaNova
     * @param aulaNova
     * @return True si hem pogut modificar-ho o no.
     */
    public boolean modificaEntrada (String assig, String grup, DiaSetmana d, int h, DiaSetmana diaNou, int horaNova, String aulaNova) {
        return horari.modificaClasse(assig, grup, d, h, diaNou, horaNova, aulaNova);
    }


    /**
     * @return El horari sencer.
     */
    public LinkedList<LinkedList<Queue<String>>> getHorariSencer() {
        if (horari == null) {
            System.err.println("ERROR: horari és null!");
            return new LinkedList<>();
        }
        return horari.getHorariSencer();
    }

    /**
     * @return String de tot el horari sencer.
     */
    public String toStringSencer() {
        return horari.toStringSencer();
    }

    /**
     * @return True si existeix un horari.
     */
    public boolean existsHorari() {
        return (horari != null);
    }

    /**
     * @param assig
     * @return True si el pla d'estudi conte una assignatura.
     */
    public boolean hasAssignatura(String assig) {
        return assignatures.containsKey(assig);
    }


    /**
     * Elimina una assignatura.
     * @param assig
     */
    public void removeAssig(String assig) {
        assignatures.remove(assig);
    }

    /**
     * @param assig
     * @param grup
     * @return True si existeix un grup en una assignatura.
     */
    public boolean existsGrupAssignatura(String assig, String grup) {
        return assignatures.get(assig).hasGrup(grup);
    }

    /*
     * esborra un grup de una assignatura.
     * @param assig
     * @param grup
     */
    public void esborrarGrupAssignatura(String assig, String grup) {
        assignatures.get(assig).esborraGrup(grup);
    }

    /**
     * Modifica el nom de la assignatura.
     * @param assig
     * @param newValue
     */
    public void setNomAssigantura(String assig, String newValue) {
        assignatures.get(assig).setNom(newValue);
    }

    /**
     * Modifica el nivell de la assignatura.
     * @param assig
     * @param nivell
     */
    public void setNivellAssigantura(String assig, int nivell) {
        assignatures.get(assig).setNivell(nivell);
    }

    /**
     * Modifica el numero de classes de teoria.
     * @param assig
     * @param qt
     */
    public void setQtClassesTeoriaAssigantura(String assig, int qt) {
        assignatures.get(assig).setQtClassesTeoria(qt);
    }

    /**
     * Modifica la durada de les classes de teoria.
     * @param assig
     * @param qt
     */
    public void setDuradaClassesTeoriaAssigantura(String assig, int qt) {
        assignatures.get(assig).setDuradaClassesTeoria(qt);
    }

    /**
     * Modifica el numero de classes de problemes.
     * @param assig
     * @param qt
     */
    public void setQtClassesProblemesAssigantura(String assig, int qt) {
        assignatures.get(assig).setQtClassesProblemes(qt);
    }

    /**
     * Modifica la durada de les classes de problemes.
     * @param assig
     * @param qt
     */
    public void setDuradaClassesProblemesAssigantura(String assig, int qt) {
        assignatures.get(assig).setDuradaClassesProblemes(qt);
    }

    /**
     * Modifica el numero de classes de laboratori.
     * @param assig
     * @param qt
     */
    public void setQtClassesLaboratoriAssigantura(String assig, int qt) {
        assignatures.get(assig).setQtClassesLaboratori(qt);
    }

    /**
     * Modifica la durada de les classes de laboratori.
     * @param assig
     * @param qt
     */
    public void setDuradaClassesLaboratoriAssigantura(String assig, int qt) {
        assignatures.get(assig).setDuradaClassesLaboratori(qt);
    }

    /**
     * Modifica la capacitat de un grup de una assignatura.
     * @param assig
     * @param grup
     * @param qt
     */
    public void setCapacitatGrupAssignatura(String assig, String grup, int qt) {
        System.err.println("DEBUG: estas apuntant a l'assignatura " + assig);
        assignatures.get(assig).setCapacitatGrup(grup, qt);
    }

    /**
     * Modifica el horari de un grup d'una assignatura.
     * @param assig
     * @param grup
     * @param valor
     */
    public void setHorariGrupAssignatura(String assig, String grup, String valor) {
        assignatures.get(assig).setHorariGrup(grup, valor);
    }

    /**
     * Modifica el tipus d'aula necessaria per un grup d'una assignatura.
     * @param assig
     * @param grup
     * @param tipusAula
     */
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

    /**
     * Afegeix un grup a una assignatura.
     * @param assignatura
     * @param g
     * @return True si es pot afegir o false altrament.
     */
    public boolean afegirGrupAssignatura (String assignatura, grup g)
    {
        assignatura a = assignatures.get(assignatura);
        return a.afegirGrup(g);
    }


}
