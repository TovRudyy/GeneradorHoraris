package domain;


// He fet una enumeracio i un atribut de classe en comptes de subclasses perque de moment tenen exactament el mateix comportament

public class Aula {

    private String id;
    private int capacitat;
    private Tipus_Aula tipus;


    public Aula(String id, int capacitat, Tipus_Aula tipus) {
        this.id = id;
        this.capacitat = capacitat;
        this.tipus = tipus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCapacitat() {
        return capacitat;
    }

    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }

    public Tipus_Aula getTipus() {
        return tipus;
    }

    public void setTipus(Tipus_Aula tipus) {
        this.tipus = tipus;
    }
}

