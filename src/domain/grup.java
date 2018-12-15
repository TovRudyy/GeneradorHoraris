package domain;


import java.io.Serializable;

/**
 * Implementa l'abstraccio de "grup d'una assignatura".
 * @author David Pujol
 */

public class grup implements Serializable {
    /** Atributs **/
    private String idGrup; //identificador del grup
    private int capacitat;  //capacitat de l'aula
    private String horariGrup;
    private RestriccioSubgrup subgrup;
    private Tipus_Aula tipus;



    /** Constructores **/
    /**
     * Constructora de la classe grup.
     * @param idGrup Identificador del grup
     * @param capacitat Capacitat del grup
     * @param horariGrup Horari del grup (Mati/Tardes)
     * @param tipus Tipus d'aula que requereix aquest grup
     */
    public grup (String idGrup, int capacitat, String horariGrup, Tipus_Aula tipus) {
        this.idGrup = idGrup;
        this.capacitat = capacitat;
        this.horariGrup = horariGrup;
        this.tipus = tipus;
    }


    /** Metodes publics **/

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


    /**
     * @return Retorna el horari del grup (Matins/Tardes)
     */
    public String getHorariAssig () {
        return horariGrup;
    }

    /**
     * @return Retorna el tipus d'aula que necessita el grup.
     */
    public Tipus_Aula getTipus () {
        return tipus;
    }

    /**
     * @return Retorna la restriccio de subgrup d'aquest grup.
     */
    public RestriccioSubgrup getSubgrup () {
        return subgrup;
    }

    /**
     * Guarda la nova restriccio de subgrup en aquest grup.
     * @param r Una nova restriccio de subgrup.
     */
    public void afegirRestriccio (RestriccioSubgrup r) {
        this.subgrup = r;
    }

    @Override
    /**
     * @return Retorna una string amb la informacio del id del grup, la seva capacitat, si es de mati o tarde i
     * finalment el tipus d'aula que necessita.
     */
    public String toString() {
        return idGrup + ":" + capacitat + ":" + horariGrup + ":" + tipus;
    }

    public boolean esSubgrup() {
        return !this.tipus.equals(Tipus_Aula.TEORIA);
    }

    public String getTipusAula() {
        return this.tipus.toString();
    }

    public void setCapacitat(int qt) {
        this.capacitat = qt;
    }

    public void setHorari(String valor) {
        this.horariGrup = valor;
    }

    public void setTipus(Tipus_Aula tipusAula) {
        System.err.println("DEBUG: vols canviar el tipus a " + tipusAula);
        this.tipus = tipusAula;
    }
}
