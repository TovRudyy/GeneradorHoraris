package testsClasses.Stubs;

import domain.RestriccioSubgrup;
import domain.Tipus_Aula;

/**
 * @author Victor
 */

public class GrupStub extends domain.grup {

    /**
     * Crea un grup
     * @param id Identificador del grup
     */
    public GrupStub(String id) {
        super(id, 0, "M", Tipus_Aula.TEORIA);
    }

    /**
     *
     * @return Retorna el identificador.
     */
    @Override
    public String getId() {
        return "0";
    }

    /**
     * @return Retorna la capacitat del grup.
     */
    @Override
    public int getCapacitat() {
        return 0;
    }

    /**
     * @return Retorna el horari del grup (M/T)
     */
    @Override
    public String getHorariAssig() {
        return "M";
    }

    /**
     * @return El tipus d'aula que necessita el grup.
     */
    @Override
    public Tipus_Aula getTipus() {
        return Tipus_Aula.TEORIA;
    }

    /**
     * Afegeix la restriccio de subgrup al grup.
     * @param r Una nova restricció de subgrup.
     */
    @Override
    public void afegirRestriccio(RestriccioSubgrup r) {
        System.out.println("afegirRestriccio cridat");
    }

    /**
     * @return La informacio del grup en forma de string.
     */
    @Override
    public String toString() {
        return "0";
    }
}
