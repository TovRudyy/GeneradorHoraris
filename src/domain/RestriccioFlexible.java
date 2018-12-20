package domain;

import java.util.LinkedList;
import java.util.Map;

public abstract class RestriccioFlexible extends Restriccio {

    /**
     * Constructor de les restriccions flexibles.
     */
    public RestriccioFlexible () {
    };

    /**
     * Mostra per pantalla la informacio relativa a la restriccio.
     */
    public abstract void showInfo ();

    /**
     * Fa la poda de les possibilitats que ja no son possibles amb aquesta restriccio.
     * @param possibles_classes Tot el map amb totes les possibilitats.
     */
    public abstract void podaPossibilitats (Map<String, Map<DiaSetmana, LinkedList<Classe>>> possibles_classes);

    /**
     * @return Una string amb tota la informacio de la restriccio.
     */
    public abstract String getInfo();

    public abstract String getAssignacioId ();

    public abstract void setId(String id);

}
