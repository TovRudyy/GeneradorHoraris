package domain;

// Aquesta classe ens permet tenir tota la informacio necessaria per a calcular l'horari d'un subgrup concret

import java.io.Serializable;
import java.util.*;

/**
 *
 Assignacio conte informacio sobre assignatura i grup els quals son els seus identificadors. A mes, ens serveix com a objecte on crear l'horari per al grup d'una assignatura, es a dir, Assignacio reuneix el conjunt d'objectes Classe que formen l'horari d'un grup.
 * @author: David
 */

public class assignacio implements Serializable {

    /** Atributs **/
    private String idGrup, idAssig;
    private int capacitat, nivellAssig;
    private Tipus_Aula tAula;
    private String horariGrup;
    private int duracioClasses, numeroClassesRestants; //numero i duracio de les classes
    private int inici_possible, final_possible;
    private Map<String, Map<DiaSetmana, LinkedList<Classe>>> possibles_classes;

    //RESTRICCIONS
    private RestriccioOcupacio ocupacio = new RestriccioOcupacio();
    protected RestriccioCorequisit corequisit = null;
    private RestriccioSubgrup subgrup = null;


    /** Constructors **/
    /**
     *  Creadora de la classe assignacio.
     * @param idGrup String amb el id del grup
     * @param cap   Enter amb la capacitat del grup
     * @param tAula Indica el tipus de l'aula que requereix el grup.
     * @param idAssig   String amb el identificador de la assignatura.
     * @param nivellAssig   Nivell al que pertany la assignatura.
     * @param numeroClasses Numero de classes a la semana (tenint en compte que una assignacio nomes sera de TEORIA,PROBLEMES o LABORATORI)
     * @param duracioClasses Duració de les seves classes.
     * @param horariGrup    Indica si la assignatura és de matins o tardes.
     * @param aules Conjunt d'aules amb les que es generen les possibles classes de la assignacio
     */
    public assignacio(String idGrup, int cap, Tipus_Aula tAula, String idAssig, int nivellAssig, int numeroClasses, int duracioClasses, String horariGrup, Map<String, Aula> aules) {
        this.idGrup = idGrup;
        this.capacitat = cap;
        this.tAula = tAula;
        this.idAssig = idAssig;
        this.nivellAssig = nivellAssig;
        this.duracioClasses = duracioClasses;
        this.horariGrup = horariGrup;

        this.inici_possible = 8;
        this.final_possible = 20;
        if (horariGrup.equals("T")) this.inici_possible = 14;
        else if (horariGrup.equals("M")) this.final_possible = 14;

        this.possibles_classes  = generaPossiblesClasses(aules);
        this.numeroClassesRestants = numeroClasses;
    }



    /** Public **/
    /**
     * Imprimeix per pantalla tota la informacio de la assignacio.
     */
    public void showAll () {
        System.out.println (idGrup + ":" + capacitat + ":"+ tAula + ":" + idAssig +":"+ nivellAssig + ""+ horariGrup + " " + inici_possible + " " + final_possible + " " + duracioClasses);

    }


    /**
     * @return El id de la assignatura de la assignacio.
     */
    public String getIdAssig() {
        return idAssig;
    }


    /**
     * @return La duracio de les seves classes.
     */
    public int getDuracioClasses ()
    {
        return duracioClasses;
    }

    /**
     * @return El id del grup de la assignacio.
     */
    public String getIdGrup () {
        return idGrup;
    }

    /**
     * @return Un enter amb el nombre d'alumnes del grup.
     */
    public int getCapacitat () {
        return capacitat;
    }


    /**
     * @return El tipus de classe que es.
     */
    public Tipus_Aula getTipus () {
        return tAula;
    }


    /**
     * @return Retorna true si la assignatura es de matins o false altrament.
     */
    public boolean esMatins () {
        if (horariGrup.equals("M")) return true;
        return false;
    }


    /**
     * Afegeix un nou correquisit a la assignacio.
     * @param c Un nou correquisit.
     */
    public void afegirCorrequisit (RestriccioCorequisit c) {
        this.corequisit = c;
    }


    /**
     * Afegeix la restriccio de subgrup a la assignacio.
     * @param r Rep per parametre la restriccio de subgrup.
     */
    public void afegirSubgrup (RestriccioSubgrup r) {
        subgrup = r;
    }


    /**
     * Genera i retorna totes les possibilitats de la assignacio, resultat de entrellaçar la assignació amb tot el conjunt
     * d'aules possibles (que siguin del tipus necessari) i amb tots els intervals d'hores possibles.
     * @return Un map que conté tot el conjunt de possibilitats d'aquesta assignació.
     * @param aules conjunt d'aules amb les que fer les classes
     */
    public Map<String, Map<DiaSetmana, LinkedList<Classe>>> generaPossiblesClasses(Map<String, Aula> aules) {
        Map<String, Map<DiaSetmana, LinkedList<Classe>>> totesClasses = new LinkedHashMap<>();

        for (Aula aula : aules.values()) {
            if (aula.getTipus() == tAula && aula.getCapacitat() >= this.capacitat) {   //mirem que l'aula i el grup sigui compatible
                ArrayList<Classe> t = new ArrayList<Classe>();

                for (DiaSetmana dia : DiaSetmana.values()) {

                    for (int i = inici_possible; (i+duracioClasses) <= final_possible; i++) {
                        Classe aux = new Classe(idAssig, idGrup, dia, i, (i+duracioClasses), aula.getId());
                        String nom_aula = aula.getId();
                        //mirem si ja tenim la entrada de aquesta aula i sino la afegim
                        totesClasses.putIfAbsent(nom_aula, new LinkedHashMap<>());

                        //mirem si ja tenim la entrada de aquest dia, i sinó l'afegim
                        totesClasses.get(nom_aula).putIfAbsent(dia, new LinkedList<>());

                        //afegim la nova classe
                        totesClasses.get(nom_aula).get(dia).add(aux);
                    }
                }
            }
        }
        return totesClasses;
    }


    /**
     * @return Totes les Classe que en aquest moment de la execucio encara son possibles d'assignar.
     */
    public ArrayList<Classe> getAllPossibleClasses () {
        ArrayList<Classe> c = new ArrayList<Classe>();
        for (String nomAula : possibles_classes.keySet()) {
            for (DiaSetmana dia: possibles_classes.get(nomAula).keySet() ) {
                c.addAll(possibles_classes.get(nomAula).get(dia));
            }

        }
        return c;
    }


    /**
     * @return El numero de classes que encara falten per assignar.
     */
    public int getNumeroClassesRestants () {
        return this.numeroClassesRestants;
    }



    /**
     * Afegeix aquesta nova Classe al conjunt de possibilitats que mante aquesta assignacio.
     * @param c Una classe que representarà una nova possibilitat de la classe assignació.
     */
    public void afegeixPossibilitat (Classe c) {
        String id_aula = c.getIdAula();
        DiaSetmana d = c.getDia();

        if (! possibles_classes.get(id_aula).get(d).contains(c))
             possibles_classes.get(id_aula).get(d).add(c);   //afegim la possibilitat
    }

    /**
     * Dur a terme el podat de les seves possibilitats despres de "agafar" una nova Classe a l'horari.
     * @param c La Classe que representa la ultima Classe que hem triat perque formi part del nostre horari.
     * @return Una arrayList amb les possibilitats que hem "podat" ja que ja no son possibles.
     */
    public ArrayList<Classe> forwardChecking (Classe c) {
        ArrayList<Classe> result = new ArrayList<>();

        result.addAll( ocupacio.deletePossibilities(possibles_classes, c));

        if (subgrup != null && c.getId_assig().equals(idAssig))
            result.addAll( subgrup.deletePossibilities(possibles_classes, c));

        //comprovem que el grup sigui el mateix perque sino ja no caldra comprovaro
        if (corequisit != null && c.getId_grup().equals(idGrup)) result.addAll(corequisit.deletePossibilities(possibles_classes, c));

        return result;
    }


    /**
     * Elimina totes les possibilitats de la assignacio menys aquelles que hem seleccionat per l'horari.
     * @return ArrayList amb totes aquelles classes que hem "podat".
     */
    public ArrayList<Classe> nomesSeleccionades () {
        ArrayList <Classe> eliminades = getAllPossibleClasses();    //eliminem tota la resta de possibilitats
        possibles_classes = new HashMap<>();
        return (eliminades);   //aqui ja no hi ha les que hem anat agafant
    }


    /**
     * Li resta una al numero de classes restants
     */
    public void afegirSeleccionada () {
        this.numeroClassesRestants -= 1;
    }


    /**
     * Li suma una al numero de classes restants
     */
    public void eliminarSeleccionada () {
        this.numeroClassesRestants += 1;
    }


    /**
     * Fa la poda de les possibilitats que ja no son possibles a causa d'aquestes restriccions.
     * @param r Cojunt de restriccions modificables per l'usuari i que afecten aquesta restriccio.
     * @return True si encara ens queden possibilitats o false si no ens en queden.
     */
    public boolean podaRestriccionsFlexibles (RestriccioFlexible r)
    {
        r.podaPossibilitats(possibles_classes);
        return (! isEmpty());
    }



    /**
     * @return Un boolea que ens diu si encara tenim suficients possibilitats per assignar tot el numero de classes restants.
     */
    public boolean isEmpty () { //si no tenim suficients possibilitats per cobrir les necessitats de l'assignatura
        if (numeroClassesRestants > (getAllPossibleClasses().size())) return true;
        return false;
    }


    @Override
    /**
     * @return Una string amb el id de la assignatura, grup, la capacitat, el tipus d'aula necessaria i si és de Matins o Tardes.
     */
    public String toString() {
        return idAssig + ":" + idGrup + ":" + capacitat + ":" + tAula + ":" + horariGrup;
    }
}
