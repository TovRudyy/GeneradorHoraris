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
    public boolean checkRestriccio(Stack<Classe> assignats) {
        System.out.println("checkRestriccio invocat");
        return false;
    }

    @Override
    public boolean checkCorrecte(Classe c1, Classe c2) {
        System.out.println("checkCorrecte invocat");
        return false;
    }

    @Override
    public boolean checkSub(String g1, String g2) {
        System.out.println("checkSub invocat");
        return false;
    }

    @Override
    protected boolean solapenHores(int ai, int af, int bi, int bf) {
        System.out.println("solapenHores invocat");
        return false;
    }

    @Override
    public ArrayList<Classe> deletePossibilities(Map<String, Map<DiaSetmana, ArrayList<Classe>>> possibles_classes, Classe c) {
        System.out.println("deletePossibilities invocat");
        return null;
    }
}
