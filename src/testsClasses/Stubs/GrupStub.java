package testsClasses.Stubs;

import domain.RestriccioSubgrup;
import domain.Tipus_Aula;

public class GrupStub extends domain.grup {

    public GrupStub(String id) {
        super(id, 0, "M", Tipus_Aula.TEORIA);
    }

    @Override
    public String getId() {
        return "0";
    }

    @Override
    public int getCapacitat() {
        return 0;
    }

    @Override
    public String getHorariAssig() {
        return "M";
    }

    @Override
    public Tipus_Aula getTipus() {
        return Tipus_Aula.TEORIA;
    }

    @Override
    public void afegirRestriccio(RestriccioSubgrup r) {
        System.out.println("afegirRestriccio cridat");
    }

    @Override
    public String toString() {
        return "0";
    }
}
