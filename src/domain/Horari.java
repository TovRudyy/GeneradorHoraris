package domain;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.jar.JarEntry;
import java.util.Stack;


public class Horari {

    private HashMap<String, assignacio> conjuntAssignacions= new HashMap<>();
    private Stack<Classe> classesFinals = new Stack();

    public Horari (ArrayList <assignacio> conjuntAssignacions) {
        afegeixAssignacions (conjuntAssignacions);
    }


    public void afegeixAssignacions (ArrayList<assignacio> conjuntAssignacions) {
        for (assignacio a : conjuntAssignacions)
            this.conjuntAssignacions.put((a.getIdAssig()+a.getIdGrup()), a);
    }


    public void findHorari () {
        boolean r = selectClasse(0);
        if (r){
            System.out.println("HEM TROBAT UN HORARI: ");
            //printHorari();
        }
        else System.out.println("NO HEM POGUT FORMAR UN HORARI");
    }

    //retorna true si ja ha acabat o false si encara no
    public boolean selectClasse (int index) {
        ArrayList<assignacio> l = new ArrayList<>( conjuntAssignacions.values());

        if (index < l.size()) {
            assignacio a = l.get(index);
            ArrayList<Classe> possibleClasses = a.getAllPossibleClasses();

            for (Classe c: possibleClasses )
            {
                a.updateClassesRestants(-1);
                Stack<Classe> eliminades = new Stack();

                eliminades.addAll(forward_checking (c)); //forward checking

                boolean valid = checkNotEmpty ();

                if (!valid) System.out.println("Fals");
                if (valid)  //l'horari compleix totes les restriccions
                {
                    boolean r;
                    if (a.getNumeroClassesRestants() == 0) {  //vol dir que ja no cal seleccionar mes classes per aquesta assignacio
                        r = selectClasse(index + 1); //passem a comprovar la seguent assignacio
                    }
                     else {
                         r = selectClasse(index);  //encara queden classes que assignar
                    }

                    if (r) //ja hem acabat
                        return r;

                    //si not r, hem de seguir iterant

                }

                //revertim els canvis fets usant la stack eliminades
                revertChanges (eliminades);


                a.updateClassesRestants(1);

            }

            return false;   //vol dir que hem mirat totes les opcions i no n'hi ha cap que funcioni

        }
        else return true;

    }



    private Stack<Classe> forward_checking (Classe c) {
        Stack<Classe> totes_eliminades = new Stack<>();

        assignacio assig_actual = conjuntAssignacions.get(c.getId_assig()+c.getId_grup());
        totes_eliminades.addAll(assig_actual.borrarTotes (c));


        for (Map.Entry<String, assignacio> aux : conjuntAssignacions.entrySet()) {
            assignacio a = aux.getValue();
            if (a != assig_actual) {
                ArrayList<Classe> eliminades = a.forwardChecking(c);
                totes_eliminades.addAll(eliminades);
            }
        }
        return totes_eliminades;
    }


    private boolean checkNotEmpty () {
        for (Map.Entry<String, assignacio> aux : conjuntAssignacions.entrySet()) {
            assignacio a = aux.getValue();
            if (a.getAllPossibleClasses().isEmpty()) {
                System.out.println("La assignacio ");
                a.showAll();
                return false;
            }
        }
        return true;
    }


    private void revertChanges (Stack<Classe> eliminades) {
        if (! eliminades.empty()) {
            Classe c = eliminades.pop();
            while (!eliminades.isEmpty()) {
                assignacio a = conjuntAssignacions.get(c.getId_assig() + c.getId_grup());
                a.afegeixPossibilitat(c);
                c = eliminades.pop();
            }
        }
    }



    public void printHorari () {
        for (Map.Entry<String,assignacio> aux : conjuntAssignacions.entrySet()) {
            assignacio a = aux.getValue();
            Classe definitiva = a.getAllPossibleClasses().get(0);   //agafem la unica possibilitat possible
            definitiva.showClasse();
        }
        writeHorari();
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

    private void writeHorari(){
        String formatHeader = "|%-15s|%-20s|%-20s|%-20s|%-20s|%-20s|\n";
        System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
        System.out.format(formatHeader, "   Hora/Dia", "      DILLUNS", "      DIMARTS", "     DIMECRES", "      DIJOUS", "     DIVENDRES");
        System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");

        ArrayList<ArrayList<Queue<String>>> horaris = new ArrayList<>();
        for(int i=0; i<12; ++i){
            horaris.add(new ArrayList<>());
            for(int j=0; j<5; ++j){
                horaris.get(i).add(new LinkedBlockingQueue<>());
            }
        }
        for(assignacio a:conjuntAssignacions.values()){
            Classe c = a.getAllPossibleClasses().get(0);
            int dia=0;
            switch(c.getDia()){
                case DILLUNS:
                    dia = 0;
                    break;
                case DIMARTS:
                    dia = 1;
                    break;
                case DIMECRES:
                    dia = 2;
                    break;
                case DIJOUS:
                    dia = 3;
                    break;
                case DIVENDRES:
                    dia = 4;
            }
            int ini = c.getHoraInici(), fi = c.getHoraFi();
            for(int i=ini; i<fi; ++i){
                horaris.get(i-8).get(dia).add("  " + c.getId_assig() + "  " + c.getId_grup() + "  " + c.getIdAula());
            }
        }
        int hora0 = 8, hora1=9;
        for(ArrayList<Queue<String>> hora : horaris){
            boolean fi = false;
            boolean first = true;
            while(!fi){
                String dilluns = (hora.get(0).isEmpty())? " " : hora.get(0).remove();
                String dimarts = (hora.get(1).isEmpty())? " " : hora.get(1).remove();
                String dimecres = (hora.get(2).isEmpty())? " " : hora.get(2).remove();
                String dijous = (hora.get(3).isEmpty())? " " : hora.get(3).remove();
                String divendres = (hora.get(4).isEmpty())? " " : hora.get(4).remove();
                System.out.format(formatHeader, (first)? " " + hora0 + ":00 - " + hora1 + ":00" : "", dilluns, dimarts, dimecres, dijous, divendres);
                first = false;
                fi = (hora.get(0).isEmpty() && hora.get(1).isEmpty() && hora.get(2).isEmpty() && hora.get(3).isEmpty() && hora.get(4).isEmpty());
            }
            ++hora0; ++hora1;
            System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
        }
    }
}
