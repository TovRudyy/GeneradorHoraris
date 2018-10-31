package domain;

import java.lang.reflect.Array;
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
        if (r){
            System.out.println("HEM TROBAT UN HORARI: ");
            printHorari();
        }
        else System.out.println("NO HEM POGUT FORMAR UN HORARI");
    }

    //retorna true si ja ha acabat o false si encara no
    public boolean selectClasse (int index) {
        if (index < conjuntAssignacions.size()) {
            assignacio a = conjuntAssignacions.get(index);
            ArrayList<Classe> possibleClasses = a.getAllPossibleClasses();

            for (Classe c: possibleClasses )
            {
                System.out.println("nova classe a tractar");
                c.showClasse();

                classesFinals.push(c); //triem una classe
                a.updateClassesRestants(-1);
                a.eliminaPossibilitat (c);

                //aqui hauriem d'eliminar les opcions que es solapen amb ella en aquella aula

                boolean valid = a.checkRestriccions (classesFinals);

                if (valid)  //l'horari compleix totes les restriccions
                {
                    boolean r;
                    if (a.getNumeroClassesRestants() == 0)  //vol dir que ja no cal seleccionar mes classes per aquesta assignacio
                        r = selectClasse(index + 1); //passem a comprovar la seguent assignacio

                     else r = selectClasse(index);  //encara queden classes que assignar

                    if (r) //ja hem acabat
                        return r;

                    //si not r, hem de seguir iterant

                }

                classesFinals.pop();

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
