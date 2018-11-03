package JUnit.Stubs;

import domain.RestriccioSubgrup;
import domain.Tipus_Aula;

public class StubGrup extends domain.grup {

    public StubGrup() {
        super("0", 0, "M", Tipus_Aula.TEORIA);
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
    public boolean esSubgrup(String id_grup) {
        return true;
    }

    @Override
    public RestriccioSubgrup getSubgrup() {
        return null;
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
