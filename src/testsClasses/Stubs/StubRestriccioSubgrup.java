package testsClasses.Stubs;

import domain.Classe;
import domain.DiaSetmana;
import domain.grup;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 * @author Victor
 */


public class StubRestriccioSubgrup extends domain.RestriccioSubgrup {

    /**
     * Crea una restriccio de subgrup.
     */
    public StubRestriccioSubgrup() {
        super(new GrupStub("0"));
    }
/*
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
*/

    /**
     * Comprova si dues Classes es solapen o no.
     * @param ai Hora inici de la primera classe
     * @param af Hora final de la primera classe.
     * @param bi Hora inici de la segona classe.
     * @param bf Hora final de la segona classe.
     * @return Retorna true si les dues classes formades pels dos parells de hores, es solapen o no.
     */
    @Override
    public boolean solapenHores(int ai, int af, int bi, int bf) {
        System.out.println("solapenHores invocat");
        return false;
    }

    /**
     * Podem les nostres possibilitats amb la classe que acabem de triar a l'horari.
     * @param possibles_classes Map amb totes les Classes possibles
     * @param c Classe que s'acaba de triar a l'horari.
     * @return Una arrayList amb les possibilitats que acabem de podar.
     */
 /*   @Override
    public ArrayList<Classe> deletePossibilities(Map<String, Map<DiaSetmana, ArrayList<Classe>>> possibles_classes, Classe c) {
        System.out.println("deletePossibilities invocat");
        return null;
    }*/

    /**
     * @return Una string amb la informacio de la restriccio de subgrup.
     */
    @Override
    public String toString() {
        return "Stub de RestriccioSubgrup";
    }
}
