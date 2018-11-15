package domain;

import java.util.ArrayList;
import java.util.Map;

public class RestriccioCorequisit extends Restriccio {

    //conte les assignatures amb les que es correquisit
    private ArrayList<String> assignatures = new ArrayList<String>();

    /**
     * Crea una instància de restriccio de correquisit.
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



    /** Aquesta part es per comprovar fer el podat **/

    public ArrayList<Classe> deletePossibilities (Map<String, Map<DiaSetmana, ArrayList<Classe>>> possibles_classes, Classe c) {
        //primerament comprovem si aquesta assignacio es un correquisit de la nova classe que hem agafat
        //si es cert haurem de podar, altrament ja hem acabat
        ArrayList<Classe> eliminades = new ArrayList<>();
        String id_aula = c.getIdAula();
        DiaSetmana dia = c.getDia();

        if (esCorrequisit(c.getId_assig())) {
            //comprovem si estem un dels seus correquisits

            ArrayList<Classe> classes = new ArrayList<>();
            if (possibles_classes.containsKey(id_aula) && possibles_classes.get(id_aula).containsKey(dia))
                classes = possibles_classes.get(id_aula).get(dia);

            for (Classe classe_aux : classes)
            {
                if (solapenHores(classe_aux.getHoraInici(), classe_aux.getHoraFi(), c.getHoraInici(), c.getHoraFi()))
                            eliminades.add(classe_aux);
            }


            for (Classe c_aux: eliminades)  //eliminem les classes amb les que hi ha conflicte
                possibles_classes.get(c_aux.getIdAula()).get(c_aux.getDia()).remove (c_aux);

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
     * @return Una string amb tota la informació de la restriccio de correquisit.
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
