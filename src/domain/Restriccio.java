package domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
/**
 * Abstreu els tipus de restriccions que existeixen.
 * @author David Pujol
 * Date: 07/10/2018
 */
public abstract class Restriccio {

    /**
     * @param ai Hora inici de la primera classe
     * @param af Hora final de la primera classe.
     * @param bi Hora inici de la segona classe.
     * @param bf Hora final de la segona classe.
     * @return Un boolea que indica si les hores de les dues classes es solapen o no.
     */
    public boolean solapenHores(int ai, int af, int bi, int bf) {
        if ((bi >= ai &&  bi < af) || (bf > ai && bf < af) ||
            (ai >= bi &&  ai < bf) || (af > bi && af < bf)) return true;

        return false;
    }

    /**
     *
     * @param possibles_classes Map amb totes les possibilitats de la assignacio.
     * @param c Classe que el horari acaba de seleccionar.
     * @return Retorna una array list amb les classes que hem eliminat.
     */
    public abstract ArrayList<Classe> deletePossibilities (Map<String, Map<DiaSetmana, LinkedList<Classe>>> possibles_classes, Classe c);




}
