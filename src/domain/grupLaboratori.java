package domain;

/**
 * @author David Pujol,
 * Date: 07/10/18
 */

public class grupLaboratori extends grup{

    /** atributs **/
    private Tipus_Lab tipus;


    /** Constructora **/
    public grupLaboratori (String id, int capacitat, String t) {
        super (id, capacitat);

        if ( t.equals ("FISICA") )
            this.tipus = Tipus_Lab.FISICA;

        else if (t.equals("INFORMATICA"))
            this.tipus = Tipus_Lab.INFORMATICA;

        else
            this.tipus = Tipus_Lab.ELECTRONICA;
    }



    /** Mètodes publics **/

    /**
     * @return Retorna el tipus de laboratori que és.
     */
    public Tipus_Lab getTipus () {
        return tipus;
    }

}
