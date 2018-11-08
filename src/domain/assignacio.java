package domain;

// Aquesta classe ens permet tenir tota la informacio necessaria per a calcular l'horari d'un subgrup concret

import persistencia.Lector_Aules_JSON;

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
    private Corequisit corequisit = null;
    private RestriccioSubgrup subgrup = null;


    /** Constructors **/
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

    //imprimeix per pantalla la informacio
    public void showAll () {
        System.out.println (idGrup + ":" + capacitat + ":"+ tAula + ":" + idAssig +":"+ nivellAssig + ""+ horariGrup + " " + inici_possible + " " + final_possible + " " + duracioClasses);

    }


    public String getIdAssig() {
        return idAssig;
    }


    public String getIdGrup () {
        return idGrup;
    }


    public void afegirCorrequisit (Corequisit c) {
        this.corequisit = c;
    }

    public void afegirSubgrup (RestriccioSubgrup r) {
        subgrup = r;
    }


    private Map<String, Map<DiaSetmana, ArrayList<Classe>>> generaPossiblesClasses() {

        Map<String, Aula> aules = Lector_Aules_JSON.getAules();  //map amb totes les aules
        //TODO: Eliminar el getAules, ja que trenca l'aillament entre capes

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


    //Funció trivial per testos ~Olek
    public void printPossiblesClasses() {
        for (String nomAula : possibles_classes.keySet()) {
            for (DiaSetmana dia: possibles_classes.get(nomAula).keySet() ) {
                for (Classe classe : possibles_classes.get(nomAula).get(dia)){
                    classe.showClasse();
                }
            }

        }

    }


    //retorna totes les classes possibles en forma de ArrayList
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



    //retorna quantes classes queden per assignar
    public int getNumeroClassesRestants () {
        return this.numeroClassesRestants;
    }



    //afegeix una classe que ara torna a ser una possibilitat
    public void afegeixPossibilitat (Classe c) {
        String id_aula = c.getIdAula();
        DiaSetmana d = c.getDia();
        possibles_classes.get(id_aula).get(d).add(c);   //afegim la possibilitat
    }


    public ArrayList<Classe> forwardChecking (Classe c) {
        ArrayList<Classe> result = new ArrayList<>();
        result.addAll( ocupacio.deletePossibilities(possibles_classes, c));

        if (subgrup != null) result.addAll( subgrup.deletePossibilities(possibles_classes, c));

        if (corequisit != null) result.addAll(corequisit.deletePossibilities(possibles_classes, c));

        return result;
    }



    public ArrayList<Classe> nomesSeleccionades () {
        ArrayList <Classe> eliminades = getAllPossibleClasses();    //eliminem tota la resta de possibilitats
        possibles_classes = new HashMap<>();
        return (eliminades);   //aqui ja no hi ha les que hem anat agafant
    }


    public void afegirSeleccionada (Classe c) {
        classes_seleccionades.add(c);
        this.numeroClassesRestants -= 1;
    }

    public void eliminarSeleccionada (Classe c) {
        classes_seleccionades.remove (c);
        this.numeroClassesRestants += 1;
    }


    public ArrayList<Classe> getSeleccionades () {
        return classes_seleccionades;
    }

    public boolean isEmpty () { //si no tenim suficients possibilitats per cobrir les necessitats de l'assignatura
        if (numeroClassesRestants > (getAllPossibleClasses().size())) return true;
        return false;
    }

    @Override
    public String toString() {
        return idAssig + ":" + idGrup + ":" + capacitat + ":" + tAula + ":" + horariGrup;
    }
}
