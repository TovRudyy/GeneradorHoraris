package domain;

import java.util.ArrayList;

/**
 * @author Rudyy, Oleksandr
 * Date: 06/10/2018
 */

public class PlaEstudis {
    private String id;  //Acrònim del Pla d'Estudis
    private ArrayList<assignatura> assignatures = new ArrayList<>(); //Assignatures pertanyents al pla d'estudis
    private Horari h;

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


    public void showAssignatures () {

        for (assignatura a: assignatures) {
            System.out.println(a.getId() + ":" + a.getNom() + ":" + a.getNivell() + "\n");
            a.showGrups();
            a.showClasses();
            a.getAssignacions();
        }

    }


    public void generaHorari () {
        ArrayList<assignacio> assignacions = new ArrayList<>();
        for (assignatura a: assignatures) {
            assignacions.addAll(a.getAssignacions());       //AQUI ESTA EL FALLO
        }

        //aqui tenim totes les assignacions totals
        h = new Horari (assignacions);
        h.findHorari();

    }


}
