package domain;

/**
 * @author David Pujol,
 * Date: 07/10/18
 */

public class grupLaboratori extends grup{

    /** atributs **/
    private Tipus_Lab tipus;


    /** Constructora **/
    public grupLaboratori (String id, int capacitat, Tipus_Lab t) {
        super (id, capacitat);
        tipus = t;
    }



    /** Mètodes publics **/

    /**
     * @return Retorna el tipus de laboratori que és.
     */
    public Tipus_Lab getTipus () {
        return tipus;
    }

}
