package domain;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;

public class Horari {

    private ArrayList<assignacio> conjuntAssignacions= new ArrayList<>();

    public Horari (ArrayList <assignacio> conjuntAssignacions) {
        afegeixAssignacions (conjuntAssignacions);
    }


    public void afegeixAssignacions (ArrayList<assignacio> conjuntAssignacions) {
        for (assignacio a : conjuntAssignacions)
            this.conjuntAssignacions.add(a);

    }


    public void findHorari () {
        selectClasse(0);
        printHorari ();
    }


    public void selectClasse (int index) {
        if (index != conjuntAssignacions.size()) {
            assignacio a_aux = conjuntAssignacions.get(index);
            ArrayList<Classe> possibilitats = a_aux.getAllPossibleClasses();

            for (Classe c : possibilitats) {

                ArrayList<Map<String, Map<DiaSetmana, ArrayList<Classe>>> > possibilitatsTotesAssignacions = new ArrayList<>();
                for (assignacio b : conjuntAssignacions)
                    b.getMapPossibilities();

                //filtrem les possibilitats
                a_aux.deletePossiblesClasses(c.getAula().getId(), c.getDia(), c.getHoraInici(), c.getHoraFi());     //seleccionem aquesta opcio i eliminem les altres


                selectClasse(index + 1);

                //revertim els canvis
                for (assignacio b : conjuntAssignacions) {
                    b.revertirPossibilitats (possibilitatsTotesAssignacions.get(0));
                    possibilitatsTotesAssignacions.remove(0);
                }


            }
        }


    }

    public void printHorari () {
        for ( assignacio a : conjuntAssignacions) {
            ArrayList<Classe> possibilitatsFinals = a.getAllPossibleClasses();
                for (Classe c : possibilitatsFinals)
                    System.out.println(a.getId() + " " + c.getDia() + " " + c.getHoraInici() + "");
        }

    }

}
