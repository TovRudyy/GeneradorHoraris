package domain;


// He fet una enumeracio i un atribut de classe en comptes de subclasses perque de moment tenen exactament el mateix comportament



/**
 * @author Victor
 * Aula.
 * El seu tipus d'Aula mai pot ser LAB
 */
public class Aula {

    private String id;          //Identificador de l'Aula
    private int capacitat;      //Capacitat de l'Aula
    private Tipus_Aula tipus;   //Tipus d'Aula


    /**
     * @param id Identificador de la nova Aula
     * @param capacitat Capacitat de la nova Aula
     * @param tipus Tipus de la nova Aula
     * @throws Exception @tipus == LAB quan no és un Laboratori
     */
    public Aula(String id, int capacitat, Tipus_Aula tipus) throws Exception {
        this.id = id;
        this.capacitat = capacitat;
        this.tipus = tipus;
        if (tipus == Tipus_Aula.LAB && !(this instanceof Laboratori)) throw new Exception();
    }

    /**
     * @return Retorna l'identificador de l'Aula
     */
    public String getId() {
        return id;
    }

    /**
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
     * @param tipus Nou tipus de l'Aula
     */
    public void setTipus(Tipus_Aula tipus) throws Exception {
        if(tipus == Tipus_Aula.LAB || this.tipus == Tipus_Aula.LAB ) throw new Exception();
        this.tipus = tipus;
    }

    @Override
    public String toString() {
        String s = "Id: " + id + "\n";
        s += "Capacitat: " + capacitat + "\n";
        s += "Tipus d'Aula: " + tipus + "\n";
        return s;
    }
}

