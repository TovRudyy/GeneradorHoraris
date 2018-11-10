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
     * @param ai Hora inici de la primera classe
     * @param af Hora final de la primera classe.
     * @param bi Hora inici de la segona classe.
     * @param bf Hora final de la segona classe.
     * @return Un booleà que indica si les hores de les dues classes es solapen o no.
     */
    protected boolean solapenHores(int ai, int af, int bi, int bf) {
        if ((bi >= ai &&  bi < af) || (bf > ai && bf < af) ||
            (ai >= bi &&  ai < bf) || (af > bi && af < bf)) return true;

        return false;
    }


    public abstract boolean checkCorrecte (Classe c1, Classe c2);


    /**
     * @param possibles_classes Mapa amb totes les possibilitats que té fins al moment la assignació.
     * @param c Classe que acabem d'agafar i amb la que cal podar totes les possibilitats.
     * @return Una arrayList amb totes les possibilitats que hem podat.
     */
    public ArrayList<Classe> deletePossibilities (Map<String, Map<DiaSetmana, ArrayList<Classe>>> possibles_classes, Classe c) {
        ArrayList<Classe> result = new ArrayList<>();

        for (Map.Entry<String, Map<DiaSetmana, ArrayList<Classe>>> aula: possibles_classes.entrySet()) {
            String id_aula = aula.getKey();
            for (Map.Entry<DiaSetmana, ArrayList<Classe>> dia : possibles_classes.get(id_aula).entrySet() ) {
                DiaSetmana nom_dia = dia.getKey();
                ArrayList<Classe> classes = possibles_classes.get(id_aula).get(nom_dia);

                for (Classe classe_aux : classes)  {
                    boolean r = checkCorrecte(classe_aux, c);
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
