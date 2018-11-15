package domain;

import java.util.Map;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Object;

/**
 * @author Rudyy, Oleksandr
 * Date: 07/10/2018
 */
public abstract class Restriccio {

    /**
     * @param ai Hora inici de la primera classe
     * @param af Hora final de la primera classe.
     * @param bi Hora inici de la segona classe.
     * @param bf Hora final de la segona classe.
     * @return Un booleà que indica si les hores de les dues classes es solapen o no.
     */
    public boolean solapenHores(int ai, int af, int bi, int bf) {
        if ((bi >= ai &&  bi < af) || (bf > ai && bf < af) ||
            (ai >= bi &&  ai < bf) || (af > bi && af < bf)) return true;

        return false;
    }


    public abstract ArrayList<Classe> deletePossibilities (Map<String, Map<DiaSetmana, ArrayList<Classe>>> possibles_classes, Classe c);




}
