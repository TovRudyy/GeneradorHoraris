package domain;

public class assignatura {
    /** Atributs **/
    private String id;
    private String nom;
    private int nivell;  //potser caldria que fós una string


    /** Constructores **/
    public assignatura (String id, String nom, int nivell) {
        this.id = id;
        this.nom = nom;
        this.nivell = nivell;
    }


    /** Mètodes públics **/
    public void setId (String id) {
        this.id = id;
    }

    public void setNom (String nom) {
        this.nom = nom;
    }

    public void setNivell (int nivell) {
        this.nivell = nivell;
    }

    public String getId (){
        return id;
    }

    public String getNom () {
        return nom;
    }

    public int getNivell () {
        return nivell;
    }

}
