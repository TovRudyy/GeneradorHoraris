package domain;

// Aquesta classe ens permet tenir tota la informacio necessaria per a calcular l'horari d'un subgrup concret

//import jdk.nashorn.internal.codegen.ClassEmitter;
import persistencia.Lector_Aules_JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @Author: David
 */

public class assignacio {

    /** Atributs **/
    private String idGrup, idAssig;
    private int capacitat, nivellAssig;
    private Tipus_Aula tAula;
    private String horariGrup;

    private int inici_possible, final_possible;
    private Map<String, Map<DiaSetmana, ArrayList<Classe>>> possibles_classes;

    private RestriccioOcupacio ocupacio = new RestriccioOcupacio();
    private Corequisit corequisit = null;
    private RestriccioSubgrup subgrup = null;


    //numero i duració de classes
    private int numeroClasses, duracioClasses, numeroClassesRestants;

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

    //modifica les classes que queden per assignar
    public void updateClassesRestants (int i) {
        this.numeroClassesRestants += i;
    }

    //afegeix una classe que ara torna a ser una possibilitat
    public void afegeixPossibilitat (Classe c) {
        String id_aula = c.getIdAula();
        DiaSetmana d = c.getDia();
        possibles_classes.get(id_aula).get(d).add(c);   //afegim la possibilitat
    }



    //mira a totes les seves restriccions i comprova que es segueixin complint
    public boolean checkRestriccions (Stack<Classe> c) {
        Stack<Classe> aux = new Stack();
        aux.addAll(c);  //ens assegurem que la restricció pugui eliminar la pila sense modificar la original

        Stack<Classe> aux_2 = new Stack();
        aux_2.addAll(c);  //ens assegurem que la restricció pugui eliminar la pila sense modificar la original

        Stack<Classe> aux_3 = new Stack();
        aux_3.addAll(c);  //ens assegurem que la restricció pugui eliminar la pila sense modificar la original

        if ((ocupacio.checkRestriccio(aux)) && (subgrup == null || subgrup.checkRestriccio(aux_2))
                && (corequisit == null || corequisit.checkRestriccio(aux_3))) {
            //System.out.println("Les restriccions son correctes");
            return true;
        }

        else  {
            //System.out.println("les restriccions són incorrectes");
            return false;
        }

    }


    public ArrayList<Classe> forwardChecking (Classe c) {
        ArrayList<Classe> result = new ArrayList<>();
        result.addAll( ocupacio.deletePossibilities(possibles_classes, c));

        if (subgrup != null) result.addAll( subgrup.deletePossibilities(possibles_classes, c));

        if (corequisit != null) result.addAll(corequisit.deletePossibilities(possibles_classes, c));

        return result;
    }


    public ArrayList<Classe> borrarTotes (Classe c) {
        ArrayList<Classe> result = new ArrayList<>();

        for (Map.Entry<String, Map<DiaSetmana, ArrayList<Classe>>> aula: possibles_classes.entrySet()) {
            String id_aula = aula.getKey();
            for (Map.Entry<DiaSetmana, ArrayList<Classe>> dia : possibles_classes.get(id_aula).entrySet() ) {
                DiaSetmana nom_dia = dia.getKey();
                ArrayList<Classe> classes = possibles_classes.get(id_aula).get(nom_dia);

                for (Classe classe_aux : classes)
                    if (classe_aux != c) result.add(classe_aux);

            }
        }

        for (Classe c_aux: result)  //eliminem les classes amb les que hi ha conflicte
            possibles_classes.get(c_aux.getIdAula()).get(c_aux.getDia()).remove (c_aux);

        return result;
    }


}
