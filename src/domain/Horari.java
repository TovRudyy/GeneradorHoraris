package domain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.jar.JarEntry;


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
            //printHorari();
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
                //System.out.println("nova classe a tractar");
                //c.showClasse();

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

    class SortClasses implements Comparator<Classe> {
        @Override
        public int compare(Classe o1, Classe o2) {
            if (o1.getDia().compareTo(o2.getDia()) > 0)
                return 1;
            else if (o1.getDia().compareTo(o2.getDia()) < 0)
                return -1;
            else{
                if (o1.getHoraInici() < o2.getHoraInici())
                    return -1;
                else if (o1.getHoraInici() > o2.getHoraInici())
                    return 1;
                else return 0;
            }
        }
    }

    public void printHoraribetter() {
        Classe[] classes = sortClasses();
        for (int i = 0; i < classes.length; i++) {
            classes[i].showClasse();
        }
    }


    private Classe[] sortClasses() {
        //System.out.println("DEBUG: size of classesFinals = " + classesFinals.size());
        Classe[] classes = classesFinals.toArray(new Classe[classesFinals.size()]);
        //System.out.println("DEBUG: size of classes = " + classes.length);
        Arrays.sort(classes, new SortClasses());
        return classes;
/*        for (Classe c : classes) {
            System.out.println(c.getId_assig() + "," + c.getId_grup() +
                    "," + c.getDia() + "," + c.getHoraInici());
        }*/
    }

/*    private void displayHorari(String[][] horari) {
        String formatHeader = "|%-15s|%-20s|%-20s|%-20s|%-20s|%-20s|\n";
        System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
        System.out.format(formatHeader, "Hora/Dia", "DILLUNS", "DIMARTS", "DIMECRES", "DIJOUS", "DIVENDRES");
        System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
        System.out.format(formatHeader, "8:00-9:00", )
        //System.out.format(
        *//*for (int i = 0; i < horari.length; i++) {
            for (int j = 0; j < horari[0].length; j++)
                System.out.format("%-15s", horari[i][j]);
            System.out.println();
        }
        System.out.println();*//*
    }*/

/*    private void buildHorariMatrix(String[][]horari, Classe[] classes) {
        for (int i = 0; i < horari.length; i++)
            for (int j = 0; j < horari[0].length; j++) {
                horari[i][j] = "";
        }
    }*/


}
