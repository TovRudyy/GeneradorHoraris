package domain;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedList;


/**
 * Implementa la restriccio de ocupacio.
 * @author David Pujol
 */


public class RestriccioOcupacio extends Restriccio {

    /**
     * Crea una instancia de la classe RestriccioOcupacio.
     */
    public RestriccioOcupacio () {
    }

    /**
     * @param possibles_classes Map amb totes les possibilitats de la assignacio.
     * @param c Classe que el horari acaba de seleccionar.
     * @return Retorna una array list amb les classes que hem eliminat.
     */
    public ArrayList<Classe> deletePossibilities (Map<String, Map<DiaSetmana, LinkedList<Classe>>> possibles_classes, Classe c) {
        String id_aula = c.getIdAula();
        DiaSetmana dia = c.getDia();
        ArrayList<Classe> aux = new ArrayList<>();

        if (possibles_classes.containsKey(id_aula) && possibles_classes.get(id_aula).containsKey(dia))
            aux.addAll(possibles_classes.get(id_aula).get(dia));

        ArrayList<Classe> eliminades = new ArrayList<>();

        for (Classe a : aux) {  //recorrem les classes a a la mateix aula i dia i filtrem
            if (solapenHores(a.getHoraInici(), a.getHoraFi(), c.getHoraInici(), c.getHoraFi())) {
                eliminades.add (a);
            }
        }

        for (Classe c_aux: eliminades)  //eliminem les classes amb les que hi ha conflicte
            possibles_classes.get(c_aux.getIdAula()).get(c_aux.getDia()).remove (c_aux);

        return eliminades;
    }


}
