package domain;

import java.util.ArrayList;
import java.util.Stack;


public class Horari {

    private ArrayList<assignacio> conjuntAssignacions= new ArrayList<>();
    private Stack<Classe> classesFinals = new Stack();


    public Horari (ArrayList <assignacio> conjuntAssignacions) {
        afegeixAssignacions (conjuntAssignacions);
    }


    public void afegeixAssignacions (ArrayList<assignacio> conjuntAssignacions) {
        for (assignacio a : conjuntAssignacions)
            this.conjuntAssignacions.add(a);

    }


    public void findHorari () {
        Stack<Classe> c = new Stack();
        boolean r = selectClasse(0);
        if (r) printHorari();
    }

    //retorna true si ja ha acabat o false si encara no
    public boolean selectClasse (int index) {
        if (index < conjuntAssignacions.size()) {
            assignacio a = conjuntAssignacions.get(index);
            ArrayList<Classe> possibleClasses = a.getAllPossibleClasses();

            for (Classe c: possibleClasses )
            {
                classesFinals.push(c); //triem una classe
                a.updateClassesRestants(-1);
                a.eliminaPossibilitat (c);

                boolean result = a.checkRestriccions (classesFinals);

                if (result)  //l'horari compleix totes les restriccions
                {
                    boolean r;
                    if (a.getNumeroClassesRestants() == 0)  //vol dir que ja no cal seleccionar mes classes per aquesta assignacio
                        r = selectClasse(index + 1); //passem a comprovar la seguent assignacio

                     else r = selectClasse(index);  //encara queden classes que assignar

                    if (r) //ja hem acabat
                        return r;

                    //si not r, hem de seguir iterant

                }

                //revertim els canvis
                classesFinals.pop();
                a.afegeixPossibilitat (c);
                a.updateClassesRestants(1);

            }
            return false;   //vol dir que hem mirat totes les opcions i no n'hi ha cap que funcioni

        }
        else return true;

    }



    public void printHorari () {
        while (! classesFinals.empty())
        {
            Classe c = classesFinals.pop();
            c.showClasse();
        }

    }

}
