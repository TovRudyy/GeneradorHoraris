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

    private int inici_possible;
    private int final_possible;
    private ArrayList<Classe> possibles_classes;
    private ArrayList<Classe> classes_assignades;

    //numero i duració de classes
    private int classes_teoria, classes_problemes, classes_laboratori;
    private double duracio_teoria, duracio_problemes, duracio_laboratori;


    /** Constructors **/
    public GrupConcret (String idGrup, int cap, Tipus_Aula tAula, String idAssig, int nivellAssig) {
        this.idGrup = idGrup;
        this.capacitat = cap;
        this.tAula = tAula;
        this.idAssig = idAssig;
        this.nivellAssig = nivellAssig;
        this.inici_possible = 8;
        this.final_possible = 20;
        this.possibles_classes = generaPossiblesClasses();
    }

    public GrupConcret (String idGrup, int cap, Tipus_Aula tAula, Tipus_Lab tLab, String idAssig, int nivellAssig) {
        this.idGrup = idGrup;
        this.capacitat = cap;
        this.tAula = tAula;
        this.idAssig = idAssig;
        this.nivellAssig = nivellAssig;
        this.tLab = tLab;
        this.inici_possible = 8;
        this.final_possible = 20;
        this.possibles_classes = generaPossiblesClasses();
    }


    /** Public **/
    //es la mateixa funcio que té grup. Valdria la pena heredarla??
    public void setClasses (int nTeoria, double dTeoria, int nProblemes, double dProblemes, int nLaboratori, double dLaboratori) {
        classes_teoria = nTeoria;
        duracio_teoria = dTeoria;
        classes_problemes = nProblemes;
        duracio_problemes = dProblemes;
        classes_laboratori = nLaboratori;
        duracio_laboratori = dLaboratori;
    }



    //imprimeix per pantalla la informacio
    public void showAll () {
        if (tLab == null) System.out.println (idGrup + ":" + capacitat + ":"+ tAula + ":" + idAssig +":"+ nivellAssig);
        else System.out.println (idGrup + ":" + capacitat + ":"+ tAula + ":" + tLab +":"+ idAssig +":"+ nivellAssig);
    }

    private ArrayList<Classe> generaPossiblesClasses() {
        Map<String, Aula> aules = Lector_Aules.getAules();
        ArrayList<Classe> totesClasses = new ArrayList<>();
        for (Aula aula : aules.values()) {
            for (DiaSetmana dia : DiaSetmana.values()) {
                for (int i = inici_possible; i < final_possible; i++) {
                    Classe aux = new Classe(dia, i, aula);
                    totesClasses.add(aux);
                }
            }
        }
        return totesClasses;
    }

    //Funció trivial per testos ~Olek
    public void printPossiblesClasses() {
        for (Classe classe: possibles_classes) {
            System.out.println(classe.getAula().getId());
            System.out.println(classe.getDia());
            System.out.println(classe.getHoraInici());
        }
    }


}
