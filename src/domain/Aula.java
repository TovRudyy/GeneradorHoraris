package domain;

/**
 * @author Victor
 * Aula.
 * El seu tipus d'Aula mai pot ser LAB
 */
public class Aula {

    private String id;              //Identificador de l'Aula
    private int capacitat;          //Capacitat de l'Aula
    private Tipus_Aula tipus;       //Tipus d'Aula


    /**
     * Creadora de la classe Aula
     * @param id Identificador de la nova Aula
     * @param capacitat Capacitat de la nova Aula
     * @param tipus Tipus de la nova Aula
     */
    public Aula(String id, int capacitat, Tipus_Aula tipus){
        this.id = id;
        this.capacitat = capacitat;
        this.tipus = tipus;
    }

    /**
     * @return Retorna l'identificador de l'Aula
     */
    public String getId() {
        return id;
    }

    /**
     * @return Retorna la capacitat de la classe
     */
    public int getCapacitat() {
        return capacitat;
    }

    /**
     * @return Retorna el tipus de l'Aula
     */
    public Tipus_Aula getTipus() {
        return tipus;
    }

    /**
     * @return String amb les dades de l'Aula
     */
    public String toString() {
        return  (id +":" + capacitat + ":" + tipus);
    }
}

