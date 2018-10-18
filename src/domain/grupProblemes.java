package domain;

/**
 * @author David Pujol,
 * Date: 07/10/18
 */

public class grupProblemes extends grup {

    /** Constructores **/
    public grupProblemes (String id, int capacitat) {
        super (id, capacitat);
    }

    @Override
    public Tipus_Aula getTipus() {
        return Tipus_Aula.PROBLEMES;
    }

}