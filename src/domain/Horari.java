package domain;

import java.util.ArrayList;
import java.util.Stack;



public class Horari {

    private ArrayList<assignacio> conjuntAssignacions= new ArrayList<>();
    private Stack<Classe> classesTriades = new Stack();


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
            assignacio a = conjuntAssignacions.get(index);
            ArrayList<Classe> possibleClasses = a.getAllPossibleClasses();

            for (Classe c: possibleClasses )
            {
                classesTriades.push(c); //triem una classe
                a.updateClassesRestants(-1);
                a.eliminaPossibilitat (c);

                boolean result = a.checkRestriccions (classesTriades);

                if (result)  //l'horari compleix totes les restriccions
                {
                    if (a.getNumeroClassesRestants() == 0) //vol dir que ja no cal seleccionar mes classes per aquesta assignacio
                        selectClasse(index + 1); //passem a comprovar la seguent assignacio

                     else selectClasse(index);  //encara queden classes que assignar

                }

                //revertim els canvis
                classesTriades.pop();
                a.afegeixPossibilitat (c);
                a.updateClassesRestants(1);

            }

        }

    }

    public void printHorari () {
        while (! classesTriades.empty())
        {
            Classe c = classesTriades.pop();
            c.showClasse();
        }

    }

}
