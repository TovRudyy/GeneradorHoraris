package domain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Rudyy, Oleksandr
 * Date: 07/10/2018
 */
public abstract class Restriccio {
    private HashMap<String, HashMap<String, assignacio>> conjuntAssignacions;

    public boolean addAssignacio(String id_assig, String id_grup, assignacio ass) {
        return (null == conjuntAssignacions.get(id_assig).putIfAbsent(id_grup, ass));
    }

    public abstract void applyRestriccio(String my_assig, String my_grup, String id_aula, int hora_inici, int hora_fi);

    private void deletePossiblesClasses(assignacio ass, String id_aula, DiaSetmana dia, int hora_inici, int hora_fi) {
        ass.deletePossiblesClasses(id_aula, dia, hora_inici, hora_fi);
    }

}
