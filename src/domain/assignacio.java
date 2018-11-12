package domain;

// Aquesta classe ens permet tenir tota la informacio necessaria per a calcular l'horari d'un subgrup concret

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: David
 */

public class assignacio {

    /** Atributs **/
    private String idGrup, idAssig;
    private int capacitat, nivellAssig;
    private Tipus_Aula tAula;
    private String horariGrup;
    private int numeroClasses, duracioClasses, numeroClassesRestants; //numero i duracio de les classes
    private int inici_possible, final_possible;
    private Map<String, Map<DiaSetmana, ArrayList<Classe>>> possibles_classes;
    private ArrayList<Classe> classes_seleccionades = new ArrayList<>();

    //RESTRICCIONS
    private RestriccioOcupacio ocupacio = new RestriccioOcupacio();
    private RestriccioCorequisit corequisit = null;
    private RestriccioSubgrup subgrup = null;


    /** Constructors **/
    /**
     *  Creadora de la classe assignació.
     * @param idGrup String amb el id del grup
     * @param cap   Enter amb la capacitat del grup
     * @param tAula Indica el tipus de l'aula que requereix el grup.
     * @param idAssig   String amb el identificador de la assignatura.
     * @param nivellAssig   Nivell al que pertany la assignatura.
     * @param numeroClasses Numero de classes a la semana (tenint en compte que una assignació només serà de TEORIA,PROBLEMES o LABORATORI)
     * @param duracioClasses Duració de les seves classes.
     * @param horariGrup    Indica si la assignatura és de matins o tardes.
     */
    public assignacio (String idGrup, int cap, Tipus_Aula tAula, String idAssig, int nivellAssig, int numeroClasses, int duracioClasses, String horariGrup) {
        this.idGrup = idGrup;
        this.capacitat = cap;
        this.tAula = tAula;
        this.idAssig = idAssig;
        this.nivellAssig = nivellAssig;
        this.numeroClasses = numeroClasses;
        this.duracioClasses = duracioClasses;
        this.horariGrup = horariGrup;

        this.inici_possible = 8;
        this.final_possible = 20;
        if (horariGrup.equals("T")) this.inici_possible = 14;
        else if (horariGrup.equals("M")) this.final_possible = 14;

        this.possibles_classes  = generaPossiblesClasses();
        this.numeroClassesRestants = numeroClasses;
    }

    /** Public **/

    /**
     * Imprimeix per pantalla tota la informació de la assignació.
     */
    public void showAll () {
        System.out.println (idGrup + ":" + capacitat + ":"+ tAula + ":" + idAssig +":"+ nivellAssig + ""+ horariGrup + " " + inici_possible + " " + final_possible + " " + duracioClasses);

    }


    /**
     * @return El id de la assignatura de la assignació.
     */
    public String getIdAssig() {
        return idAssig;
    }

    /**
     * @return El id del grup de la assignació.
     */
    public String getIdGrup () {
        return idGrup;
    }

    /**
     * Afegeix un nou correquisit a la assignació.
     * @param c Un nou correquisit.
     */
    public void afegirCorrequisit (RestriccioCorequisit c) {
        this.corequisit = c;
    }

    /**
     * Afegeix la restricció de subgrup a la assignació.
     * @param r Rep per paràmetre la restricció de subgrup.
     */
    public void afegirSubgrup (RestriccioSubgrup r) {
        subgrup = r;
    }


    /**
     * Genera i retorna totes les possibilitats de la assignació, resultat de entrellaçar la assignació amb tot el conjunt
     * d'aules possibles (que siguin del tipus necessari) i amb tots els intervals d'hores possibles.
     * @return Un map que conté tot el conjunt de possibilitats d'aquesta assignació.
     */
    public Map<String, Map<DiaSetmana, ArrayList<Classe>>> generaPossiblesClasses() {

        Map<String, Aula> aules = ControladorAules.getAules();

        Map<String, Map<DiaSetmana, ArrayList<Classe>>> totesClasses = new HashMap<>();

        for (Aula aula : aules.values()) {
            if (aula.getTipus() == tAula && aula.getCapacitat() >= this.capacitat) {   //mirem que l'aula i el grup sigui compatible
                ArrayList<Classe> t = new ArrayList<>();

                for (DiaSetmana dia : DiaSetmana.values()) {

                    for (int i = inici_possible; (i+duracioClasses) <= final_possible; i++) {
                        Classe aux = new Classe(idAssig, idGrup, dia, i, (i+duracioClasses), aula.getId());
                        String nom_aula = aula.getId();
                        //mirem si ja tenim la entrada de aquesta aula i sino la afegim
                        totesClasses.putIfAbsent(nom_aula, new HashMap<>());

                        //mirem si ja tenim la entrada de aquest dia, i sinó l'afegim
                        totesClasses.get(nom_aula).putIfAbsent(dia, new ArrayList<>());

                        //afegim la nova classe
                        totesClasses.get(nom_aula).get(dia).add(aux);
                    }
                }
            }
        }
        return totesClasses;
    }


    /**
     * Imprimeix per pantalla totes les possibilitats(Classe) que té aquesta assignació
     */
    public void printPossiblesClasses() {
        for (String nomAula : possibles_classes.keySet()) {
            for (DiaSetmana dia: possibles_classes.get(nomAula).keySet() ) {
                for (Classe classe : possibles_classes.get(nomAula).get(dia)){
                    classe.showClasse();
                }
            }

        }

    }


    /**
     *
     * @return Totes les Classe que en aquest moment de la execució encara són possibles d'assignar.
     */
    public ArrayList<Classe> getAllPossibleClasses () {
        ArrayList<Classe> c = new ArrayList<>();
        for (String nomAula : possibles_classes.keySet()) {
            for (DiaSetmana dia: possibles_classes.get(nomAula).keySet() ) {
                for (Classe classe : possibles_classes.get(nomAula).get(dia)){
                    c.add(classe);
                }
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
     * Afegeix aquesta nova Classe al conjunt de possibilitats que manté aquesta assignació.
     * @param c Una classe que representarà una nova possibilitat de la classe assignació.
     */
    public void afegeixPossibilitat (Classe c) {
        String id_aula = c.getIdAula();
        DiaSetmana d = c.getDia();
        possibles_classes.get(id_aula).get(d).add(c);   //afegim la possibilitat
    }

    /**
     * Dur a terme el podat de les seves possibilitats després de "agafar" una nova Classe a l'horari.
     * @param c La Classe que representa la última Classe que hem triat perquè formi part del nostre horari.
     * @return Una arrayList amb les possibilitats que hem "podat" ja que ja no són possibles.
     */

    public ArrayList<Classe> forwardChecking (Classe c) {
        ArrayList<Classe> result = new ArrayList<>();
        result.addAll( ocupacio.deletePossibilities(possibles_classes, c));

        if (subgrup != null) result.addAll( subgrup.deletePossibilities(possibles_classes, c));

        if (corequisit != null) result.addAll(corequisit.deletePossibilities(possibles_classes, c));

        return result;
    }


    /**
     * Elimina totes les possibilitats de la assignació menys aquelles que hem seleccionat per l'horari.
     * @return ArrayList amb totes aquelles classes que hem "podat".
     */
    public ArrayList<Classe> nomesSeleccionades () {
        ArrayList <Classe> eliminades = getAllPossibleClasses();    //eliminem tota la resta de possibilitats
        possibles_classes = new HashMap<>();
        return (eliminades);   //aqui ja no hi ha les que hem anat agafant
    }

    /**
     * Afegeix una nova Classe com a definitiva.
     * @param c Classe que l'horari ha triat com a definitiva.
     */
    public void afegirSeleccionada (Classe c) {
        classes_seleccionades.add(c);
        this.numeroClassesRestants -= 1;
    }


    /**
     * Elimina una nova Classe que ara ha deixat de ser definitiva.
     * @param c Classe que l'horari ha eliminat de la selecció final.
     */
    public void eliminarSeleccionada (Classe c) {
        if(classes_seleccionades.remove(c)) this.numeroClassesRestants += 1;
    }

    /**
     * @return ArrayList amb tot el conjunt de possibilitats que estan marcades com a definitives.
     */
    public ArrayList<Classe> getSeleccionades () {
        return classes_seleccionades;
    }

    /**
     * @return Un booleà que ens diu si encara tenim suficients possibilitats per assignar tot el número de classes restants.
     */

    public boolean isEmpty () { //si no tenim suficients possibilitats per cobrir les necessitats de l'assignatura
        if (numeroClassesRestants > (getAllPossibleClasses().size())) return true;
        return false;
    }


    @Override
    /**
     * @return Una string amb el id de la assignatura, grup, la capacitat, el tipus d'aula necessària i si és de Matins o Tardes.
     */
    public String toString() {
        return idAssig + ":" + idGrup + ":" + capacitat + ":" + tAula + ":" + horariGrup;
    }
}
