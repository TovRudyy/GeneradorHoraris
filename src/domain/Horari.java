package domain;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Stack;


public class Horari {

    private HashMap<String, assignacio> conjuntAssignacions= new HashMap<>();

    /**
     * Creadora de la classe Horari.
     * @param conjuntAssignacions ArrayList amb tot el conjunt d'assignacions que hem d'assignar al nostre horari.
     */
    public Horari (ArrayList <assignacio> conjuntAssignacions) {
        afegeixAssignacions (conjuntAssignacions);
    }

    /**
     * Afegeix totes les assignacions passades pel paràmetre al map de la classe horari.
     * @param conjuntAssignacions ArrayList amb tot el conjunt d'assignacions que hem d'assignar al nostre horari.
     */
    public void afegeixAssignacions (ArrayList<assignacio> conjuntAssignacions) {
        for (assignacio a : conjuntAssignacions)
            this.conjuntAssignacions.put((a.getIdAssig()+a.getIdGrup()), a);
    }

    /**
     * Genera el horari i imprimeix per pantalla si ha trobat un horari possible o no.
     */
    public void findHorari () {
        boolean r = selectClasse(0);
        if (r) System.err.println("HEM TROBAT UN HORARI: ");

        else System.err.println("NO HEM POGUT FORMAR UN HORARI");
    }


    /**
     * Implementa el algorisme que calcularà un horari per les diferents assignacions prèviament guardades
     * i que compleixin totes les restriccions.
     * @param index Indica quina assignació estem tractant en aquesta iteració.
     * @return Un booleà que indica si el horari és possible o no.
     */
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

                else {  //la combinacio es invalida. Per tant revertim els canvis i agafem la seguent Classe
                    System.err.println("no es valida");
                    revertChanges (eliminades); //afegim les possibilitats que haviem podat
                    a.eliminarSeleccionada(c);  //revertim el canvi de les que haviem seleccionat
                }

            }
            return false;   //vol dir que hem mirat totes les opcions i no n'hi ha cap que funcioni

        }

        else return true;

    }

    /**
     * Recorre totes les assignacions i fa la poda de les possibilitats que ja no són viables amb la nova elecció de l'horari.
     * @param c Nova classe seleccionada per l'horari
     * @return Una pila amb totes les possibilitats que les assignacions han eliminat en aquesta "poda"
     */

    private Stack<Classe> forward_checking (Classe c) {
        Stack<Classe> totes_eliminades = new Stack<>();

        for (Map.Entry<String, assignacio> aux : conjuntAssignacions.entrySet()) {
            assignacio a = aux.getValue();
            ArrayList<Classe> eliminades = a.forwardChecking(c);
            totes_eliminades.addAll(eliminades);

        }

        return totes_eliminades;
    }



    /**
     * @return Un booleà que indica si encara tenim suficients possibilitats en totes les assignacions per poder generar
     * tot el horari restant.
     */
    private boolean checkNotEmpty () {
        for (Map.Entry<String, assignacio> aux : conjuntAssignacions.entrySet()) {
            assignacio a = aux.getValue();
            if (a.isEmpty()) {
                System.err.println("Aquesta esta buida");
                a.showAll();
                return false;
            }
        }
        return true;
    }

    /**
     * Torna a afegir totes aquestes possibilitats a les seves assignacions originals, revertint així els canvis.
     * @param eliminades Una pila amb totes les classes que prèviament hem "podat" perquè ja no eren viables.
     */
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


    /**
     * Processa les classes que hem triat per a forma el horari i l'agrupa en aquells que es produeixen el mateix dia,
     * a la mateix hora i ho mostra en forma de taula.
     */
    public void printHorari(){
        String formatHeader = "|%-15s|%-20s|%-20s|%-20s|%-20s|%-20s|\n";
        System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
        System.out.format(formatHeader, "   Hora/Dia", "      DILLUNS", "      DIMARTS", "     DIMECRES", "      DIJOUS", "     DIVENDRES");
        System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");

        ArrayList<ArrayList<Queue<String>>> horaris = new ArrayList<>();
        for(int i=0; i<12; ++i){
            horaris.add(new ArrayList<>());
            for(int j=0; j<5; ++j){
                horaris.get(i).add(new PriorityQueue<>());
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

    public String toString() {
        // Es crea un nou stream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // Es salva el stream vell
        PrintStream old = System.out;
        // S'indica a java que passi a utilitzar el nou stream
        System.setOut(ps);
        printHorari();
        // Es restaura el stream original:
        System.out.flush();
        System.setOut(old);
        //Es transforma baos en un String
        String ret = null;
        try {
            ret = baos.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
