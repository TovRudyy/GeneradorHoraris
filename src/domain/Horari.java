package domain;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Autor David Pujol
 */

public class Horari {

    private LinkedHashMap<String, assignacio> conjuntAssignacions = new LinkedHashMap<>();   //fem servir linked per mantenir el ordre d'entrada
    private ArrayList<assignacio> l;


    /**
     * Creadora de la classe Horari.
     *
     * @param conjuntAssignacions ArrayList amb tot el conjunt d'assignacions que hem d'assignar al nostre horari.
     */
    public Horari(ArrayList<assignacio> conjuntAssignacions) {
        afegeixAssignacions(conjuntAssignacions);
        this.l = new ArrayList<> (this.conjuntAssignacions.values());
    }


    /**
     * Afegeix totes les assignacions passades pel paràmetre al map de la classe horari.
     * Primer afegirem les assignacions de matins i despres les de tardes per motius d'optimització de l'algorisme.
     *
     * @param conjuntAssignacions ArrayList amb tot el conjunt d'assignacions que hem d'assignar al nostre horari.
     */
    private void afegeixAssignacions(ArrayList<assignacio> conjuntAssignacions) {
        for (assignacio a : conjuntAssignacions)    //primer afegim les de matins i despres les de tardes
            if (a.esMatins()) this.conjuntAssignacions.put((a.getIdAssig() + a.getIdGrup()), a);

        for (assignacio a : conjuntAssignacions)
            if (!a.esMatins()) this.conjuntAssignacions.put((a.getIdAssig() + a.getIdGrup()), a);

    }


    /** METODES PEL CALCUL DE L'HORARI **/

    /**
     * Genera el horari i imprimeix per pantalla si ha trobat un horari possible o no.
     */
    public boolean findHorari() {
        boolean r = selectClasse(0);
        if (r) System.err.println("HEM TROBAT UN HORARI: ");

        else System.err.println("NO HEM POGUT FORMAR UN HORARI");

        return r;

    }


    /**
     * Implementa el algorisme que calculara un horari per les diferents assignacions previament guardades
     * i que compleixin totes les restriccions.
     *
     * @param index Indica quina assignacio estem tractant en aquesta iteracio.
     * @return Un boolea que indica si el horari es possible o no.
     */
    //retorna true si ja ha acabat o false si encara no
    public boolean selectClasse (int index) {

        if (index < l.size()) {
            assignacio a = l.get(index);
            ArrayList<Classe> possibleClasses = a.getAllPossibleClasses();

            for (Classe c: possibleClasses )
            {
                a.afegirSeleccionada(c);    //tambe fa el update de les que falten
                Stack<Classe> eliminades = new Stack();
                boolean valid = forward_checking (c, eliminades); //forward checking

                if (valid) {
                    boolean r;
                    if (a.getNumeroClassesRestants() == 0)  //hem de saltar al seguent
                    {
                        eliminades.addAll(a.nomesSeleccionades());
                        r = selectClasse(index + 1); //passem a comprovar la seguent assignacio
                    }

                    else   //si encara hem d'assignar classes a aquesta assignacio
                        r = selectClasse(index);

                    if (r) return r;
                }


                revertChanges(eliminades, c);
                a.eliminarSeleccionada(c);
            }

            return false;   //vol dir que hem mirat totes les opcions i no n'hi ha cap que funcioni
        }

        else return true;

    }


    /**
     * Recorre totes les assignacions i fa la poda de les possibilitats que ja no son viables amb la nova eleccio de l'horari.
     *
     * @param c Nova classe seleccionada per l'horari
     * @return Una pila amb totes les possibilitats que les assignacions han eliminat en aquesta "poda"
     */

    private boolean forward_checking(Classe c, Stack<Classe> totes_eliminades) {

        for (Map.Entry<String, assignacio> aux : conjuntAssignacions.entrySet()) {
            assignacio a = aux.getValue();
            ArrayList<Classe> eliminades = a.forwardChecking(c);
            totes_eliminades.addAll(eliminades);
            if (a.isEmpty()) return false;
        }

        return true;
    }



    /**
     * Torna a afegir totes aquestes possibilitats a les seves assignacions originals, revertint així els canvis.
     * @param eliminades Una pila amb totes les classes que previament hem "podat" perque ja no eren viables.
     */
    private void revertChanges(Stack<Classe> eliminades, Classe actual) {
        while (!eliminades.empty()) {
            Classe c = eliminades.pop();
            assignacio a = conjuntAssignacions.get(c.getId_assig() + c.getId_grup());
            if (c != actual)
            a.afegeixPossibilitat(c);
        }
    }


    /** METODES PER MOSTRAR EL HORARI **/

    /**
     * Processa les classes que hem triat per a forma el horari i l'agrupa en aquells que es produeixen el mateix dia,
     * a la mateix hora i ho mostra en forma de taula.
     */
    public void printHorari() {
        String formatHeader = "|%-15s|%-20s|%-20s|%-20s|%-20s|%-20s|\n";
        System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
        System.out.format(formatHeader, "   Hora/Dia", "      DILLUNS", "      DIMARTS", "     DIMECRES", "      DIJOUS", "     DIVENDRES");
        System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");

        ArrayList<ArrayList<Queue<String>>> horaris = new ArrayList<>();
        for (int i = 0; i < 12; ++i) {
            horaris.add(new ArrayList<>());
            for (int j = 0; j < 5; ++j) {
                horaris.get(i).add(new PriorityQueue<>());
            }
        }
        for (assignacio a : conjuntAssignacions.values()) {
            for (Classe c : a.getSeleccionades()) {
                int dia = 0;
                switch (c.getDia()) {
                    case DILLUNS:
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

        int hora0 = 8, hora1 = 9;
        for (ArrayList<Queue<String>> hora : horaris) {
            boolean fi = false;
            boolean first = true;
            while (!fi) {
                String dilluns = (hora.get(0).isEmpty()) ? " " : hora.get(0).remove();
                String dimarts = (hora.get(1).isEmpty()) ? " " : hora.get(1).remove();
                String dimecres = (hora.get(2).isEmpty()) ? " " : hora.get(2).remove();
                String dijous = (hora.get(3).isEmpty()) ? " " : hora.get(3).remove();
                String divendres = (hora.get(4).isEmpty()) ? " " : hora.get(4).remove();
                System.out.format(formatHeader, (first) ? " " + hora0 + ":00 - " + hora1 + ":00" : "", dilluns, dimarts, dimecres, dijous, divendres);
                first = false;
                fi = (hora.get(0).isEmpty() && hora.get(1).isEmpty() && hora.get(2).isEmpty() && hora.get(3).isEmpty() && hora.get(4).isEmpty());
            }
            ++hora0;
            ++hora1;
            System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
        }
    }


    /**
     * Processa les classes que hem triat per a forma el horari i l'agrupa en aquells que es produeixen el mateix dia,
     * a la mateix hora i ho mostra en diverses taules, una per cada Aula diferent. Nomes escriu taules
     * per aquelles Aules en les que es fa alguna classe
     */
    public void printHorariAules() {
        Set<String> aules = new TreeSet<>();
        for (assignacio a : conjuntAssignacions.values())
            for (Classe c : a.getSeleccionades()) aules.add(c.getIdAula());
        for (String aula : aules) {
            System.out.println("Aula " + aula);
            String formatHeader = "|%-15s|%-20s|%-20s|%-20s|%-20s|%-20s|\n";
            System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
            System.out.format(formatHeader, "   Hora/Dia", "      DILLUNS", "      DIMARTS", "     DIMECRES", "      DIJOUS", "     DIVENDRES");
            System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");

            ArrayList<ArrayList<Queue<String>>> horaris = new ArrayList<>();
            for (int i = 0; i < 12; ++i) {
                horaris.add(new ArrayList<>());
                for (int j = 0; j < 5; ++j) {
                    horaris.get(i).add(new PriorityQueue<>());
                }
            }
            for (assignacio a : conjuntAssignacions.values()) {
                for (Classe c : a.getSeleccionades()) {
                    if (!c.getIdAula().equals(aula)) continue;
                    int dia = 0;
                    switch (c.getDia()) {
                        case DILLUNS:
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
                        horaris.get(i - 8).get(dia).add("     " + c.getId_assig() + "    " + c.getId_grup());
                    }
                }
            }

            int hora0 = 8, hora1 = 9;
            for (ArrayList<Queue<String>> hora : horaris) {
                boolean fi = false;
                boolean first = true;
                while (!fi) {
                    String dilluns = (hora.get(0).isEmpty()) ? " " : hora.get(0).remove();
                    String dimarts = (hora.get(1).isEmpty()) ? " " : hora.get(1).remove();
                    String dimecres = (hora.get(2).isEmpty()) ? " " : hora.get(2).remove();
                    String dijous = (hora.get(3).isEmpty()) ? " " : hora.get(3).remove();
                    String divendres = (hora.get(4).isEmpty()) ? " " : hora.get(4).remove();
                    System.out.format(formatHeader, (first) ? " " + hora0 + ":00 - " + hora1 + ":00" : "", dilluns, dimarts, dimecres, dijous, divendres);
                    first = false;
                    fi = (hora.get(0).isEmpty() && hora.get(1).isEmpty() && hora.get(2).isEmpty() && hora.get(3).isEmpty() && hora.get(4).isEmpty());
                }
                ++hora0;
                ++hora1;
                System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
            }
            System.out.println();
        }
    }

    /**
     * Processa les classes que hem triat per a forma el horari i l'agrupa en aquells que es produeixen el mateix dia,
     * a la mateix hora i ho mostra en diverses taules, una per assignatura.
     */
    public void printHorariAssignatures() {
        Set<String> assigs = new TreeSet<>();
        for (assignacio a : conjuntAssignacions.values()) assigs.add(a.getIdAssig());
        for (String ass : assigs) {
            System.out.println("Assignatura " + ass);
            String formatHeader = "|%-15s|%-20s|%-20s|%-20s|%-20s|%-20s|\n";
            System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
            System.out.format(formatHeader, "   Hora/Dia", "      DILLUNS", "      DIMARTS", "     DIMECRES", "      DIJOUS", "     DIVENDRES");
            System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");

            ArrayList<ArrayList<Queue<String>>> horaris = new ArrayList<>();
            for (int i = 0; i < 12; ++i) {
                horaris.add(new ArrayList<>());
                for (int j = 0; j < 5; ++j) {
                    horaris.get(i).add(new PriorityQueue<>());
                }
            }
            for (assignacio a : conjuntAssignacions.values()) {
                for (Classe c : a.getSeleccionades()) {
                    if (!c.getId_assig().equals(ass)) continue;
                    int dia = 0;
                    switch (c.getDia()) {
                        case DILLUNS:
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
                        horaris.get(i - 8).get(dia).add("     " + c.getId_grup() + "   " + c.getIdAula());
                    }
                }
            }

            int hora0 = 8, hora1 = 9;
            for (ArrayList<Queue<String>> hora : horaris) {
                boolean fi = false;
                boolean first = true;
                while (!fi) {
                    String dilluns = (hora.get(0).isEmpty()) ? " " : hora.get(0).remove();
                    String dimarts = (hora.get(1).isEmpty()) ? " " : hora.get(1).remove();
                    String dimecres = (hora.get(2).isEmpty()) ? " " : hora.get(2).remove();
                    String dijous = (hora.get(3).isEmpty()) ? " " : hora.get(3).remove();
                    String divendres = (hora.get(4).isEmpty()) ? " " : hora.get(4).remove();
                    System.out.format(formatHeader, (first) ? " " + hora0 + ":00 - " + hora1 + ":00" : "", dilluns, dimarts, dimecres, dijous, divendres);
                    first = false;
                    fi = (hora.get(0).isEmpty() && hora.get(1).isEmpty() && hora.get(2).isEmpty() && hora.get(3).isEmpty() && hora.get(4).isEmpty());
                }
                ++hora0;
                ++hora1;
                System.out.format("+---------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
            }
            System.out.println();
        }
    }


    /**
     * @return Retorna una string amb el horari que hem generat
     */
    public String toString() {
        // Es crea un nou stream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // Es salva el stream vell
        PrintStream old = System.out;


        //INDIQUEM DE QUINA MANERA VOLEM MOSTRAR EL HORARI
        System.out.println("Pots mostrar el horari de diferents maneres\n" +
                "1. Horari sencer\n" +
                "2. Horari per assignatures\n" +
                "3. Horari per aules\n ");
        Scanner reader = new Scanner(System.in);
        int opcio = reader.nextInt();


        // S'indica a java que passi a utilitzar el nou stream
        System.setOut(ps);

        if (opcio == 1) printHorari();
        else if (opcio == 2) printHorariAssignatures();
        else printHorariAules();


        // Es restaura el stream original:
        System.out.flush();
        System.setOut(old);
        //Es transforma baos en un String
        try {
            return baos.toString(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
}

