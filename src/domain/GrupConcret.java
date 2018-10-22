package domain;

// Aquesta classe ens permet tenir tota la informacio necessaria per a calcular l'horari d'un subgrup concret

import persistencia.Lector_Aules;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: David
 */

public class GrupConcret {

    /** Atributs **/
    private String idGrup, idAssig;
    private int capacitat, nivellAssig;
    private Tipus_Aula tAula;
    private Tipus_Lab tLab = null;

    private int inici_possible, final_possible;
    private ArrayList<Classe> possibles_classes, classes_assignades;


    //numero i duració de classes
    private int numeroClasses;
    private int duracioClasses;


    /** Constructors **/
    public GrupConcret (String idGrup, int cap, Tipus_Aula tAula, String idAssig, int nivellAssig, int numeroClasses, int duracioClasses) {
        this.idGrup = idGrup;
        this.capacitat = cap;
        this.tAula = tAula;
        this.idAssig = idAssig;
        this.nivellAssig = nivellAssig;
        this.numeroClasses = numeroClasses;
        this.duracioClasses = duracioClasses;

        this.inici_possible = 8;
        this.final_possible = 20;
        this.possibles_classes = generaPossiblesClasses();
    }

    public GrupConcret (String idGrup, int cap, Tipus_Aula tAula, Tipus_Lab tLab, String idAssig, int nivellAssig, int numeroClasses, int duracioClasses) {
        this.idGrup = idGrup;
        this.capacitat = cap;
        this.tAula = tAula;
        this.idAssig = idAssig;
        this.nivellAssig = nivellAssig;
        this.tLab = tLab;
        this.numeroClasses = numeroClasses;
        this.duracioClasses = duracioClasses;

        this.inici_possible = 8;
        this.final_possible = 20;
        this.possibles_classes = generaPossiblesClasses();
    }


    /** Public **/

    //imprimeix per pantalla la informacio
    public void showAll () {
        if (tLab == null) System.out.println (idGrup + ":" + capacitat + ":"+ tAula + ":" + idAssig +":"+ nivellAssig);
        else System.out.println (idGrup + ":" + capacitat + ":"+ tAula + ":" + tLab +":"+ idAssig +":"+ nivellAssig);

       // System.out.println(numeroClasses + " " + duracioClasses);
    }


    private ArrayList<Classe> generaPossiblesClasses() {
        Map<String, Aula> aules = Lector_Aules.getAules();  //map amb totes les aules
        ArrayList<Classe> totesClasses = new ArrayList<>();

        for (Aula aula : aules.values()) {
            if (aula.getTipus() == tAula && aula.getTipusLab() == tLab) {   //mirem que l'aula i el grup sigui compatible
                for (DiaSetmana dia : DiaSetmana.values()) {
                    for (int i = inici_possible; (i+duracioClasses) <= final_possible; i++) {
                        Classe aux = new Classe(dia, i, (i+duracioClasses), aula);
                        totesClasses.add(aux);
                    }
                }
            }
        }
        return totesClasses;
    }




    //Funció trivial per testos ~Olek
    public void printPossiblesClasses() {
        for (Classe classe: possibles_classes) {
            System.out.println(classe.getAula().getId() + " " + classe.getDia()+ " " + classe.getHoraInici());
        }
    }


}
