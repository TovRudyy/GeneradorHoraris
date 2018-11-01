package domain;

import java.util.ArrayList;
/**
 * @author David Pujol,
 * Date: 07/10/18
 */

public class grup {
    /** Atributs **/
    private String idGrup; //identificador de l'aula
    private int capacitat;  //capacitat de l'aula
    private String horariGrup;
    private RestriccioSubgrup subgrup;
    private Tipus_Aula tipus;



    /** Constructores **/
    public grup (String idGrup, int capacitat, String horariGrup, Tipus_Aula tipus) {
        this.idGrup = idGrup;
        this.capacitat = capacitat;
        this.horariGrup = horariGrup;
        this.tipus = tipus;
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
        return tipus;
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


    public RestriccioSubgrup getSubgrup () {
        return subgrup;
    }


    public void afegirRestriccio (RestriccioSubgrup r) {
        this.subgrup = r;
    }

    @Override
    public String toString() {
        return idGrup + ":" + capacitat + ":" + horariGrup + ":" + tipus;
    }
}
