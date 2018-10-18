package domain;

// Aquesta classe ens permet tenir tota la informacio necessaria per a calcular l'horari d'un subgrup concret

/**
 * @Author: David
 */

public class GrupConcret {

    /** Atributs **/
    private String idGrup, idAssig;
    private int capacitat, nivellAssig;
    private Tipus_Aula tAula;
    private Tipus_Lab tLab = null;

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
    }

    public GrupConcret (String idGrup, int cap, Tipus_Aula tAula, Tipus_Lab tLab, String idAssig, int nivellAssig) {
        this.idGrup = idGrup;
        this.capacitat = cap;
        this.tAula = tAula;
        this.idAssig = idAssig;
        this.nivellAssig = nivellAssig;
        this.tLab = tLab;
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


}
