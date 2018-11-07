package domain;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Stack;


public class Horari {

    private HashMap<String, assignacio> conjuntAssignacions= new HashMap<>();

    public Horari (ArrayList <assignacio> conjuntAssignacions) {
        afegeixAssignacions (conjuntAssignacions);
    }


    public void afegeixAssignacions (ArrayList<assignacio> conjuntAssignacions) {
        for (assignacio a : conjuntAssignacions)
            this.conjuntAssignacions.put((a.getIdAssig()+a.getIdGrup()), a);
    }


    public void findHorari () {
        boolean r = selectClasse(0);
        if (r) System.out.println("HEM TROBAT UN HORARI: ");

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
                a.afegirSeleccionada(c);    //tambe fa el update de les que falten
                Stack<Classe> eliminades = new Stack();
                eliminades.addAll(forward_checking (c)); //forward checking

                boolean valid = checkNotEmpty ();

                boolean r;
                if (valid) {
                    if (a.getNumeroClassesRestants() == 0)  //hem de saltar al seguent
                    {
                        eliminades.addAll(a.nomesSeleccionades());
                        r = selectClasse(index + 1); //passem a comprovar la seguent assignacio
                    }

                    else   //si encara hem d'assignar classes a aquesta assignacio
                        r = selectClasse(index);

                    if (r) return r;
                }

                else {  //la combinacio es invalida
                    System.out.println("no es valida");
                    c.showClasse();
                    revertChanges (eliminades);
                    a.eliminarSeleccionada(c);
                }

            }
            return false;   //vol dir que hem mirat totes les opcions i no n'hi ha cap que funcioni

        }

        else return true;

    }



    private Stack<Classe> forward_checking (Classe c) {
        Stack<Classe> totes_eliminades = new Stack<>();

        for (Map.Entry<String, assignacio> aux : conjuntAssignacions.entrySet()) {
            assignacio a = aux.getValue();
            ArrayList<Classe> eliminades = a.forwardChecking(c);
            totes_eliminades.addAll(eliminades);

        }

        return totes_eliminades;
    }


    private boolean checkNotEmpty () {
        for (Map.Entry<String, assignacio> aux : conjuntAssignacions.entrySet()) {
            assignacio a = aux.getValue();
            if (a.isEmpty()) {
                System.out.println("Aquesta falla");
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


    public void printHorari(){
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
            for (Classe c: a.getSeleccionades()) {
                int dia = 0;
                switch (c.getDia()) {
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
                for (int i = ini; i < fi; ++i) {
                    horaris.get(i - 8).get(dia).add("  " + c.getId_assig() + "  " + c.getId_grup() + "  " + c.getIdAula());
                }
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
