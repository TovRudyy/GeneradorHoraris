package testsClasses.Stubs;

import domain.Classe;
import domain.DiaSetmana;
import domain.grup;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

public class StubRestriccioSubgrup extends domain.RestriccioSubgrup {

    public StubRestriccioSubgrup() {
        super(new GrupStub("0"));
    }

    @Override
    public boolean checkCorrecte(Classe c1, Classe c2) {
        return false;
    }

    @Override
    public boolean checkSub(String g1, String g2) {
        return false;
    }

    @Override
    protected boolean solapenHores(int ai, int af, int bi, int bf) {
        return false;
    }

    @Override
    public ArrayList<Classe> deletePossibilities(Map<String, Map<DiaSetmana, ArrayList<Classe>>> possibles_classes, Classe c) {
        return new ArrayList<>();
    }
}
