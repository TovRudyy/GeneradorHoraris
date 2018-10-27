package domain;

/**
 * @author David Pujol,
 * Date: 07/10/18
 */

public class grup {
    /** Atributs **/
    private String idGrup; //identificador de l'aula
    private int capacitat;  //capacitat de l'aula
    private String horariGrup;


    /** Constructores **/
    public grup (String idGrup, int capacitat, String horariGrup) {
        this.idGrup = idGrup;
        this.capacitat = capacitat;
        this.horariGrup = horariGrup;
    }


    /** Mètodes públics **/

    /**
     * @return Retorna el id del grup.
     */
    public String getId () {
        return idGrup;
    }


    /**
     * @return Retorna la capacitat del grup.
     */
    public int getCapacitat () {
        return capacitat;
    }



    public String getHorariAssig () {
        return horariGrup;
    }



    public Tipus_Aula getTipus () {
        return Tipus_Aula.TEORIA;
    }



    public Tipus_Lab getTipusLab () {
        return null;
    }

    /**
     *
     * @param id identificador d'un grup existent i que pertany a la mateixa assignatura
     * @return  cert si aquest objecte grup és un subgrup del grup id, fals si no
     */
    public boolean esSubgrup(String id) {
        int other = Integer.parseInt(id);
        if (other%10 != 0) return false;
        int me = Integer.parseInt(idGrup);
        return (other/10 == me/10);
    }

}
