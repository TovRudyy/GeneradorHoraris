package domain;

public class grup {
    /** Atributs **/
    private String id;
    private int capacitat;

    /** Constructores **/
    public grup (String id, int capacitat) {
        this.id = id;
        this.capacitat = capacitat;
    }


    /** Mètodes públics **/
    public void setId (String id) {
        this.id = id;
    }

    public void setCapacitat (int capacitat) {
        this.capacitat = capacitat;
    }

    public String getId () {
        return id;
    }

    public int getCapacitat () {
        return capacitat;
    }

}
