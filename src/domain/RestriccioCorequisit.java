package domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 * Implementa la restriccio de correquisit.
 * @author David Pujol
 */

public class RestriccioCorequisit extends RestriccioNoFlexible {

    //conte les assignatures amb les que es correquisit
    private ArrayList<String> assignatures = new ArrayList<String>();


    /**
     * Crea una instancia de restriccio de correquisit.
     */
    public RestriccioCorequisit(){
    }

    /**
     * @return True si no te correquisits i false altrament.
     */
    public boolean isEmpty() {
        return assignatures.isEmpty();
    }


    /**
     * @return Les assignatures amb les que aquesta assignacio es correquisit.
     */
    public ArrayList<String> getAssignatures () {
        return assignatures;
    }


    /**
     * @param id_assignatura Identificador de la assignatura.
     * @return cert si id_assignatura s'ha afegit al Corequisit correctament
     */
    public boolean addAssignatura(String id_assignatura) {
        return assignatures.add(id_assignatura);
    }


    /**
     * Elimina una assignatura dels seus correquisits.
     * @param id_assignatura
     * @return True si l'ha eliminat o false altrament.
     */
    public boolean eliminarAssignatura (String id_assignatura)
    {
        return assignatures.remove(id_assignatura);
    }


    /**
     * @param possibles_classes Map amb totes les possibilitats de la assignacio.
     * @param c Classe que el horari acaba de seleccionar.
     * @return Retorna una array list amb les classes que hem eliminat.
     */
    public ArrayList<Classe> deletePossibilities (Map<String, Map<DiaSetmana, LinkedList<Classe>>> possibles_classes, Classe c) {
        //primerament comprovem si aquesta assignacio es un correquisit de la nova classe que hem agafat
        //si es cert haurem de podar, altrament ja hem acabat
        ArrayList<Classe> eliminades = new ArrayList<>();
        DiaSetmana dia = c.getDia();

        if (esCorrequisit(c.getId_assig())) {
            //comprovem si estem un dels seus correquisits

            for (Map.Entry<String, Map<DiaSetmana, LinkedList<Classe>>> aula : possibles_classes.entrySet()) {
                String id_aula = aula.getKey();

                ArrayList<Classe> classes = new ArrayList<>();
                if (possibles_classes.get(id_aula).containsKey(dia))
                    classes.addAll(possibles_classes.get(id_aula).get(dia));

                for (Classe classe_aux : classes) {
                    if (solapenHores(classe_aux.getHoraInici(), classe_aux.getHoraFi(), c.getHoraInici(), c.getHoraFi()))
                        eliminades.add(classe_aux);
                }
            }

            for (Classe c_aux : eliminades) {  //eliminem les classes amb les que hi ha conflicte
                    possibles_classes.get(c_aux.getIdAula()).get(c_aux.getDia()).remove(c_aux);
            }

        }
        return eliminades;
    }



    /**
     * @param id_assig Identificador de una assignatura.
     * @return True si aquesta assignatura es un dels correquisits. False altrament.
     */
    public boolean esCorrequisit (String id_assig) {
        return assignatures.contains(id_assig);
    }


    /**
     * @return Una string amb tota la informacio de la restriccio de correquisit.
     */
    public String toString(){
        String ret = "";
        for (int i = 0; i < assignatures.size(); i++) {
            if (i == 0)
                ret = assignatures.get(i);
            else
            ret = ret + ":" + assignatures.get(i);
        }
        ret = ret + "\n";
        return ret;
    }




}
