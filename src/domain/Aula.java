package domain;

import java.io.Serializable;

/**
 * Implementa l'abstracció d'una Aula.
 * @author Victor
 */
public class Aula implements Serializable {

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

    /**
     * Modifica la capacitat per un nou valor.
     * @param nou
     */
    public void setCapacitat(int nou) {
        this.capacitat = nou;
    }

    /**
     * Modifica el seu identificador per un nou valor.
     * @param newValue
     */
    public void setId(String newValue) {
        this.id = newValue;
    }

    /**
     * Canvia el tipus per un nou valor passat per parametre.
     * @param tipus
     */
    public void setTipus(String tipus) {
        try {
            this.tipus = Tipus_Aula.string_to_Tipus_Aula(tipus);
        } catch (Aula_Exception e) {
            e.printStackTrace();
        }
    }
}

