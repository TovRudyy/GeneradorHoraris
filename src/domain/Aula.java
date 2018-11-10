package domain;


// He fet una enumeracio i un atribut de classe en comptes de subclasses perque de moment tenen exactament el mateix comportament



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
     * Modifica el identificador de l'aula pel nou identificador
     * @param id Nou identificador de l'Aula
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Retorna la capacitat de la classe
     */
    public int getCapacitat() {
        return capacitat;
    }

    /**
     * Modifica la capacitat de l'aula per la nova capacitat
     * @param capacitat Nova capacitat de l'Aula
     */
    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }

    /**
     * @return Retorna el tipus de l'Aula
     */
    public Tipus_Aula getTipus() {
        return tipus;
    }

    /**
     * Modifica el tipus de l'aula pel nou tipus.
     * @param tipus Nou tipus de l'Aula
     */
    public void setTipus(Tipus_Aula tipus){
        this.tipus = tipus;
    }

    /**
     * @return String amb les dades de l'Aula
     */
    public String toString() {
        return  (id +":" + capacitat + ":" + tipus);
    }
}

