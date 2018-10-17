package domain;

/**
 * @author David Pujol,
 * Date: 07/10/18
 */

public class grup {
    /** Atributs **/
    private String id; //identificador de l'aula
    private int capacitat;  //capacitat de l'aula


    /** Constructores **/
    public grup (String id, int capacitat) {
        this.id = id;
        this.capacitat = capacitat;
    }


    /** Mètodes públics **/

    /**
     * @return Retorna el id del grup.
     */
    public String getId () {
        return id;
    }


    /**
     * @return Retorna la capacitat del grup.
     */
    public int getCapacitat () {
        return capacitat;
    }




    public Tipus_Aula getTipus () {
        return Tipus_Aula.TEORIA;
    }



    public Tipus_Lab getTipusLab () {
        return null;
    }


}
