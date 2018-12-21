package domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public abstract class RestriccioNoFlexible extends Restriccio {

    /**
     *
     * @param possibles_classes Map amb totes les possibilitats de la assignacio.
     * @param c Classe que el horari acaba de seleccionar.
     * @return Retorna una array list amb les classes que hem eliminat.
     */
    public abstract ArrayList<Classe> deletePossibilities (Map<String, Map<DiaSetmana, LinkedList<Classe>>> possibles_classes, Classe c);


}
