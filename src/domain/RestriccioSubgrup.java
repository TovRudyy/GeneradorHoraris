package domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 * Implementa la restriccio subgrup.
 * @author David Pujol
 */

public class RestriccioSubgrup extends Restriccio {

    private grup pare;

    /**
     * @param pare és l'objecte grup de l'assignacio
     */
    public RestriccioSubgrup(grup pare) {
        this.pare = pare;
    }


    public ArrayList<Classe> deletePossibilities (Map<String, Map<DiaSetmana, LinkedList<Classe>>> possibles_classes, Classe c) {
        //primerament comprovem si aquesta assignacio es un subgrup de la nova classe que hem agafat
        //si es cert haurem de podar, altrament ja hem acabat
        ArrayList<Classe> eliminades = new ArrayList<>();


        //no cal comprovar que siguin de la mateixa assignatura perque ho comprovem abans de cridar la funcio. Sera un prerequisit.
        if (c.getId_grup().equals (pare.getId())) { //si el que hem agafat te el id del seu grup de teoria

            for (Map.Entry<String, Map<DiaSetmana, LinkedList<Classe>>> aula: possibles_classes.entrySet()) {
                String id_aula = aula.getKey();

                ArrayList<Classe> classes = new ArrayList<>();
                DiaSetmana dia = c.getDia();

                if (possibles_classes.get(id_aula).containsKey(dia))
                    classes.addAll(possibles_classes.get(id_aula).get(dia));


                for (Classe classe_aux : classes)
                {
                        if (solapenHores(classe_aux.getHoraInici(), classe_aux.getHoraFi(), c.getHoraInici(), c.getHoraFi()))
                            eliminades.add(classe_aux);
                }
            }
        }

        for (Classe c_aux: eliminades)  //eliminem les classes amb les que hi ha conflicte
            possibles_classes.get(c_aux.getIdAula()).get(c_aux.getDia()).remove (c_aux);


        return eliminades;
    }

    /**
     * @return Una string amb la informacio de la restriccio (qui es el seu grup de teoria).
     */
    @Override
    public String toString() {
        return "Pare: " + pare.toString();
    }
}
