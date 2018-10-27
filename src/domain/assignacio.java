package domain;

// Aquesta classe ens permet tenir tota la informacio necessaria per a calcular l'horari d'un subgrup concret

import jdk.nashorn.internal.codegen.ClassEmitter;
import persistencia.Lector_Aules;

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
    private Tipus_Lab tLab = null;
    private String horariGrup;

    private int inici_possible, final_possible;
    private Map<String, Map<DiaSetmana, ArrayList<Classe>>> possibles_classes;
    private ArrayList<Restriccio> restriccions;


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

    public assignacio (String idGrup, int cap, Tipus_Aula tAula, Tipus_Lab tLab, String idAssig, int nivellAssig, int numeroClasses, int duracioClasses, String horariGrup) {
        this.idGrup = idGrup;
        this.capacitat = cap;
        this.tAula = tAula;
        this.idAssig = idAssig;
        this.nivellAssig = nivellAssig;
        this.tLab = tLab;
        this.numeroClasses = numeroClasses;
        this.duracioClasses = duracioClasses;
        this.horariGrup = horariGrup;

        this.inici_possible = 8;
        this.final_possible = 20;
        if (horariGrup.equals("T")) this.inici_possible = 14;
        else if (horariGrup.equals("M")) this.final_possible = 14;

        this.possibles_classes = generaPossiblesClasses();
        this.numeroClassesRestants = numeroClasses;
    }


    /** Public **/

    //imprimeix per pantalla la informacio
    public void showAll () {
        if (tLab == null) System.out.println (idGrup + ":" + capacitat + ":"+ tAula + ":" + idAssig +":"+ nivellAssig + ""+ horariGrup + " " + inici_possible + " " + final_possible + " " + duracioClasses);
        else System.out.println (idGrup + ":" + capacitat + ":"+ tAula + ":" + tLab +":"+ idAssig +":"+ nivellAssig + "" + horariGrup + " " + inici_possible + " " + final_possible +" " + duracioClasses) ;

    }


    public String getIdAssig() {
        return idAssig;
    }


    public String getIdGrup () {
        return idGrup;
    }


    private Map<String, Map<DiaSetmana, ArrayList<Classe>>> generaPossiblesClasses() {

        Map<String, Aula> aules = Lector_Aules.getAules();  //map amb totes les aules

        Map<String, Map<DiaSetmana, ArrayList<Classe>>> totesClasses = new HashMap<>();

        for (Aula aula : aules.values()) {
            if (aula.getTipus() == tAula && aula.getTipusLab() == tLab && aula.getCapacitat() >= this.capacitat) {   //mirem que l'aula i el grup sigui compatible
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



    //eliminem les classes possibles i ho guardem a classes_filtrades (UTIL PEL FORWARD CHECKING)
    public void deletePossiblesClasses(String id_aula, DiaSetmana dia, int hora_inici, int hora_fi) {
        //Donats els valors d'entrada borra totes les possibles classes que es donen en l'aula id_aula, el dia dia, des de hora_inici fins a hora_fi)
        //aixo ho farem servir quan fem el forward checking
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

    //elimina una classe que ja no es una possibilitat triarla
    public void eliminaPossibilitat (Classe c) {
        String id_aula = c.getIdAula();
        DiaSetmana d = c.getDia();
        ArrayList<Classe> p = possibles_classes.get(id_aula).get(d);   //arrayList
        int i=0;
        boolean found = false;
        while (i < p.size() && ! found)
        {
            if (p.get(i) == c) {
                possibles_classes.get(id_aula).get(d).remove(i); //eliminem la possibilitat
                found = true;
            }
            ++i;
        }
    }


    //afegeix una classe que ara torna a ser una possibilitat
    public void afegeixPossibilitat (Classe c) {
        String id_aula = c.getIdAula();
        DiaSetmana d = c.getDia();
        possibles_classes.get(id_aula).get(d).add(c);   //afegim la possibilitat
    }


    //mira a totes les seves restriccions i comprova que es segueixin complint
    public boolean checkRestriccions (Stack<Classe> c) {
        while (! c.empty())
            c.pop();

        return true;
    }
}