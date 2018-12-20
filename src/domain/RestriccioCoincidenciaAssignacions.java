package domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class RestriccioCoincidenciaAssignacions extends RestriccioFlexible {
    private ArrayList<String> assignacions;
    private DiaSetmana d;
    private String id;


    public RestriccioCoincidenciaAssignacions (ArrayList<String> assignacions, DiaSetmana d) {
        this.assignacions = assignacions;
        this.d = d;
    }

    public void showInfo () {
        String a = "Les seguents assignacions han de coincidir ";
        for (String ass : assignacions)
        {
            a += ass + " ,";
        }
        a = a + " el dia " + d;

        System.out.println(a);
    }


    public void podaPossibilitats (Map<String, Map<DiaSetmana, LinkedList<Classe>>> possibles_classes){


    }

    /**
     * @return Una string amb tota la informacio de la restriccio.
     */
    public String getInfo() {
        String a = "Les assignacions ";
        //for (int i=0;)
        for (String ass : assignacions)
        {
            a += ass + " ,";
        }
        a = a + " el dia " + d;
        return a;
    }

    public String getAssignacioId () {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
