package domain;

import java.util.Stack;
import java.util.ArrayList;
import java.util.Map;

public class RestriccioOcupacio extends Restriccio {

    public ArrayList<Classe> deletePossibilities (Map<String, Map<DiaSetmana, ArrayList<Classe>>> possibles_classes, Classe c) {
        String id_aula = c.getIdAula();
        DiaSetmana dia = c.getDia();
        ArrayList<Classe> aux = new ArrayList<>();
        if (possibles_classes.containsKey(id_aula) && possibles_classes.get(id_aula).containsKey(dia))
            aux = possibles_classes.get(id_aula).get(dia);

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
