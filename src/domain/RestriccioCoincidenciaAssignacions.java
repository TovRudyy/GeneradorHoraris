package domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class RestriccioCoincidenciaAssignacions extends RestriccioFlexible {
    private ArrayList<String> assignacions;
    private DiaSetmana d;


    /**
     * Constructora de la classe
     * @param assignacions Conjunt de les seves assignacions que han de coincidir.
     * @param d Dia de la setmana
     */
    public RestriccioCoincidenciaAssignacions (ArrayList<String> assignacions, DiaSetmana d) {
        this.assignacions = assignacions;
        this.d = d;
    }

    /**
     * Mostra per consola tota la informacio de la classe
     */
    public void showInfo () {
        String a = "Les seguents assignacions han de coincidir ";
        for (String ass : assignacions)
        {
            a += ass + " ,";
        }
        a = a + " el dia " + d;

        System.out.println(a);
    }

    /**
     * Poda del map totes les posibilitats que ja no es poden donar.
     * @param possibles_classes Tot el map amb totes les possibilitats.
     */
    public void podaPossibilitats (Map<String, Map<DiaSetmana, LinkedList<Classe>>> possibles_classes){

        ArrayList<Classe> eliminades = new ArrayList<>();
        //recorro totes les aules
        for (Map.Entry<String, Map<DiaSetmana, LinkedList<Classe>>> a : possibles_classes.entrySet()) {
            Map<DiaSetmana, LinkedList<Classe>> diaConcret = a.getValue();
            for (Map.Entry<DiaSetmana, LinkedList<Classe>> aux : diaConcret.entrySet()) {
                if (! aux.getKey().equals(d)) {
                    eliminades.addAll(aux.getValue());  //afegim totes les que no siguin del dia concret
                }
            }
        }
        for (Classe c_aux: eliminades) {  //eliminem les classes amb les que hi ha conflicte
            possibles_classes.get(c_aux.getIdAula()).get(c_aux.getDia()).remove(c_aux);
        }
    }

    /**
     * @return Una string amb tota la informacio de la restriccio.
     */
    public String getInfo() {
        String a = "Les assignacions ";

        for (int i=0; i < assignacions.size(); ++i)
        {
            String ass = assignacions.get(i);
            if (i == (assignacions.size()-1)) a+= ass;
            else a += ass + " ,";
        }
        a = a + " han de coincidir el dia " + d;
        return a;
    }

    /**
     * @return Una string amb tots els identificadors guardats.
     */
    public String getAssignacioId () {
        String result = "";
        for (String a : assignacions)
            result = result + " " + a;

        return result;
    }

    public void setId(String id) {

    }



}
