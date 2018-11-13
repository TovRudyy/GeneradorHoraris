package domain;

import java.util.ArrayList;

public class RestriccioCorequisit extends Restriccio {

    //conte les assignatures amb les que es correquisit
    private ArrayList<String> assignatures = new ArrayList<String>();

    public RestriccioCorequisit(){
    }

    public boolean isEmpty() {
        return assignatures.isEmpty();
    }


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
     * @param c1 Primera possibilitat.
     * @param c2 Segona possibilitat.
     * @return Retorna un booleà que indica si c2 és correquisit amb c1.
     */
    public boolean checkCorrecte (Classe c1, Classe c2 ) {    //hem de comprovar si la c2 es un correquisit
        if (esCorrequisit (c2.getId_assig()) && (c1.getDia().equals(c2.getDia())) &&
           (solapenHores(c1.getHoraInici(), c1.getHoraFi(), c2.getHoraInici(), c2.getHoraFi())) &&
           (c1.getId_grup().equals(c2.getId_grup())) )
                return false;

        return true;
    }

    public boolean esCorrequisit (String id_assig) {
        return assignatures.contains(id_assig);

    }

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
