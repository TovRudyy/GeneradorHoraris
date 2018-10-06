package domain;

import java.util.ArrayList;

/**
 * @author  Oleksandr Rudyy,
 * Date: 06/10/2018
 */

public class PlaEstudis {
    String id;  //Acrònim del Pla d'Estudis
    ArrayList<assignatura> assignatures = new ArrayList<assignatura>(); //Assignatures pertanyents al pla d'estudis

    public PlaEstudis(String id) {
        this.id = id;
    }

    /**
     *
     * @param assig
     * @return true si l'element s'ha afegit a les assignatures del Pla d'Estudis, false si no.
     */
    public boolean addAssignatura(assignatura assig) {
        return assignatures.add(assig);
    }

    /**
     *
     * @return el id del Pla d'Estudis
     */
    public String getID() {
        return this.id;
    }

    /**
     *
     * @return un element String[] amb els identificadors de totes les assignatures del pla d'estudis
     */
    public String[] getAssignatures(){
        String[] id_assignatures;
        int i;

        id_assignatures = new String[assignatures.size()];
        i = 0;
        for (assignatura  a : assignatures) {
            id_assignatures[i] = a.getId();
            i++;
        }
        return id_assignatures;
    }

}
