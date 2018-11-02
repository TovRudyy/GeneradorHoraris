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
     *
     * @param assignats pila que conté els identificadors de les assignacions i les seves classes assignades. Com a mínim ha de contenir un element.
     * @return certs si la nova assignacio de Classe compleix amb la restricció, fals si no.
     */
    public abstract boolean checkRestriccio(Stack<Classe> assignats);


    protected boolean solapenHores(int ai, int af, int bi, int bf) {
        if ((bi >= ai &&  bi < af) || (bf > ai && bf < af) ||
            (ai >= bi &&  ai < bf) || (af > bi && af < bf)) return true;

        return false;
    }


    public abstract boolean checkCorrecte (Classe c1, Classe c2);

    public ArrayList<Classe> deletePossibilities (Map<String, Map<DiaSetmana, ArrayList<Classe>>> possibles_classes, Classe c) {
        ArrayList<Classe> result = new ArrayList<>();

        for (Map.Entry<String, Map<DiaSetmana, ArrayList<Classe>>> aula: possibles_classes.entrySet()) {
            String id_aula = aula.getKey();
            for (Map.Entry<DiaSetmana, ArrayList<Classe>> dia : possibles_classes.get(id_aula).entrySet() ) {
                DiaSetmana nom_dia = dia.getKey();
                ArrayList<Classe> classes = possibles_classes.get(id_aula).get(nom_dia);

                for (Classe classe_aux : classes)  {
                    boolean r = checkCorrecte (classe_aux, c);
                    if (!r)   //em d'eliminar-la
                        result.add(classe_aux);

                }
            }
        }

        for (Classe c_aux: result)  //eliminem les classes amb les que hi ha conflicte
            possibles_classes.get(c_aux.getIdAula()).get(c_aux.getDia()).remove (c_aux);


        return result;
    }



}
