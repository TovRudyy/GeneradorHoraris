package domain;

import java.util.LinkedList;
import java.util.Map;

public abstract class RestriccioFlexible extends Restriccio {

    public RestriccioFlexible () {
    };

    public abstract void podaPossibilitats (Map<String, Map<DiaSetmana, LinkedList<Classe>>> possibles_classes);
}
