package domain;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * La classe Horari contee l'algorisme de backtracking encarregat de trobar un horari valid
 * donat el conjunt de dades de totes les Assignacions.
 * @author David Pujol
 */

public class Horari {

    private LinkedHashMap<String, assignacio> conjuntAssignacions = new LinkedHashMap<>();   //fem servir linked per mantenir el ordre d'entrada
    private ArrayList<assignacio> l;
    private ArrayList<Classe> classesSeleccionades = new ArrayList<>();//ho guardem en forma de stack perque quan retrocedim sempre treurem la ultima afegida
    private Map<String, Aula> aules;

    /**
     * Creadora de la classe Horari.
     *
     * @param conjuntAssignacions ArrayList amb tot el conjunt d'assignacions que hem d'assignar al nostre horari.
     */
    public Horari(ArrayList<assignacio> conjuntAssignacions, Map<String, Aula> aules) {
        afegeixAssignacions(conjuntAssignacions);
        this.l = new ArrayList<>(this.conjuntAssignacions.values());
        this.aules = aules;
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

        for (assignacio a : conjuntAssignacions)    //primer afegim les de matins i despres les de tardes
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
    public boolean selectClasse(int index) {

        if (index < l.size()) {
            assignacio a = l.get(index);
            ArrayList<Classe> possibleClasses = a.getAllPossibleClasses();
            //Stack <Classe> alreadyProcessed = new Stack<>();

            for (Classe c : possibleClasses) {
                a.afegirSeleccionada(c);    //tambe fa el update de les que falten
                Stack<Classe> eliminades = new Stack();

                //alreadyProcessed.push(c);
                boolean valid = forward_checking(c, eliminades); //forward checking

                if (valid) {
                    boolean r;
                    classesSeleccionades.add(c);
                    if (a.getNumeroClassesRestants() == 0)  //hem de saltar al seguent
                    {
                        eliminades.addAll(a.nomesSeleccionades());
                        r = selectClasse(index + 1); //passem a comprovar la seguent assignacio
                    } else   //si encara hem d'assignar classes a aquesta assignacio
                        r = selectClasse(index);

                    if (r) return r;
                }

                classesSeleccionades.remove(classesSeleccionades.size() - 1);
                revertChanges(eliminades, c);
                a.eliminarSeleccionada(c);
            }

            //anem borrant tots els que anem processant. I nomes els tornem a posar quan arribem aqui perque realment tornem enrere.
            //revertChanges2(alreadyProcessed);

            return false;   //vol dir que hem mirat totes les opcions i no n'hi ha cap que funcioni
        } else return true;

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

            //tractem aquest cas per separat perque si tenim dos classes d'una mateixa assignacio sino entrem en bucle
            //if (a.getIdAssig().equals(c.getId_assig()) && a.getIdGrup().equals(c.getId_grup()))
            //    a.eliminarConcreta(c);

            ArrayList<Classe> eliminades = a.forwardChecking(c);
            totes_eliminades.addAll(eliminades);
            if (a.isEmpty()) return false;
        }

        return true;
    }


    /**
     * Torna a afegir totes aquestes possibilitats a les seves assignacions originals, revertint així els canvis.
     *
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

        ArrayList<ArrayList<Queue<String>>> horaris = new ArrayList<>();    //inicialitzacio
        for (int i = 0; i < 12; ++i) {  //primerament ho organitzem per hores (de 8 a 8)
            horaris.add(new ArrayList<>());
            for (int j = 0; j < 5; ++j) {   //ara per dies (de dilluns a divendres)
                horaris.get(i).add(new PriorityQueue<>());  //creem 5 priority queues
            }
        }

        for (int k = 0; k < classesSeleccionades.size(); ++k) {
            Classe c = classesSeleccionades.get(k);
            for (int i = c.getHoraInici(); i < c.getHoraFi(); ++i) {
                int dia = 0;    //assignem un valor numeric al dia
                switch (c.getDia()) {   //DILLUNS no cal ja que si ho es, el valor sera igualment 0
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
                horaris.get(i - 8).get(dia).add("  " + c.getId_assig() + "  " + c.getId_grup() + "  " + c.getIdAula());
            }
        }

        int hora0 = 8, hora1 = 9;
        for (ArrayList<Queue<String>> hora : horaris) { //obtenim una per cada hora
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
        Set<String> aules = new TreeSet<>(this.aules.keySet());

        ArrayList<Classe> classesSeleccionades = new ArrayList<>(this.classesSeleccionades);

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

            for (int k = 0; k < classesSeleccionades.size(); ++k) {
                Classe c = classesSeleccionades.get(k);
                if (c.getIdAula().equals(aula)) {
                    for (int i = c.getHoraInici(); i < c.getHoraFi(); ++i) {
                        int dia = 0;    //assignem un valor numeric al dia
                        switch (c.getDia()) {   //DILLUNS no cal ja que si ho es, el valor sera igualment 0
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
                        horaris.get(i - 8).get(dia).add("  " + c.getId_assig() + "  " + c.getId_grup() + "  ");
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
        Set<String> assigs = new TreeSet<>();   //conjunt de assignatures
        ArrayList<Classe> classesSeleccionades = new ArrayList<>(this.classesSeleccionades);

        for (Classe c : classesSeleccionades)
            assigs.add(c.getId_assig());

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

            for (Classe c : classesSeleccionades) {
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
                        break;
                }
                int ini = c.getHoraInici(), fi = c.getHoraFi();
                for (int i = ini; i < fi; ++i) {
                    horaris.get(i - 8).get(dia).add("     " + c.getId_grup() + "   " + c.getIdAula());
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



    public boolean modificaClasse(String idAssig, String idGrup, DiaSetmana diaAntic, int horaAntiga, DiaSetmana diaNou, int horaNova, String aulaNova) {
        //la eliminem de les seleccionades fins al moment
        Classe borrada = null;
        int i = 0;
        boolean found = false;
        while (!found && i < classesSeleccionades.size()) {
            Classe c = classesSeleccionades.get(i);
            if (c.getId_assig().equals(idAssig) && c.getId_grup().equals(idGrup) && c.getHoraInici() == horaAntiga && c.getDia().equals(diaAntic)) {
                borrada = c;
                classesSeleccionades.remove(c);
                conjuntAssignacions.get(c.getId_assig() + c.getId_grup()).eliminarSeleccionada(c);    //tambe la eliminem de les seleccionades de la assignacio
                found = true;
            }
            ++i;
        }


        if (borrada != null) {  //ens assegurem que nomes ho fem si hem trobat la classe especificada
            //Ara modifiquem la classe amb les noves dades i la provem d'afegir
            Classe m = borrada;
            assignacio a = conjuntAssignacions.get(m.getId_assig() + m.getId_grup());
            int horaFi = m.getDurada() + horaNova;

            //comprovem que les noves dades son correctes abans de recolocarla (el dia no cal comprovar-lo)
            if (horaFi >= 20 || horaNova < 8) {
                System.out.println("Hora incorrecte");
                return false;
            }

            //comprovem que l'aula existeix i que compleix els requisits.
            if (!(aules.containsKey(aulaNova) && aules.get(aulaNova).getCapacitat() <= a.getCapacitat() &&
               aules.get(aulaNova).getTipus() == a.getTipus())) {
                System.out.println("La aula no es correcte");
                return false;
            }


            m.setDia(diaNou);
            m.setHora_inici(horaNova);
            m.setHora_fi(horaFi);  //ara ja la tenim amb la informacio canviada
            m.setAula(aulaNova);


            a.afegirSeleccionada(m);
            //ara comprovem si la podem afegir

            for (Classe c : classesSeleccionades) {
                if (solapenHores(c.getHoraInici(), c.getHoraFi(), m.getHoraInici(), m.getHoraFi())
                    && c.getDia().equals(m.getDia())) {
                    if (c.getIdAula().equals(m.getIdAula())) {
                        System.out.println("Tenim un conflicte amb " + c.getId_assig() + " "+ c.getId_grup() + " de ocupacio.");
                        return false;  //restriccio ocupacio

                    }
                    int c1 = Integer.parseInt(c.getId_grup());
                    int m1 = Integer.parseInt(m.getId_grup());

                    //error amb un subgrup
                    if ((c.getId_assig().equals(m.getId_assig())) && (c1 % 10 == 0) && (c1 / 10 == m1 / 10)) {
                        System.out.println("Tenim un conflicte amb " + c.getId_assig() +" " + c.getId_grup() + " de subgrup");
                        return false;
                    }

                    //les assignatures han de ser correquisit i els grups han de coincidir
                    if (a.corequisit.esCorrequisit(c.getId_assig()) && c.getId_grup().equals(m.getId_grup())) {
                        System.out.println("Tenim un conflicte amb " + c.getId_assig() + " " + c.getId_grup() + " de correquisit");
                        return false;
                    }
                }

            }
            classesSeleccionades.add(m);
            return true;

        }
        System.out.println("No hem trobat la classe indicada");
        return false;

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


    //aquesta funcio cal modificarli el scope perque tant les restriccions com el horari hi puguin accedir
    public boolean solapenHores(int ai, int af, int bi, int bf) {
        if ((bi >= ai && bi < af) || (bf > ai && bf < af) ||
                (ai >= bi && ai < bf) || (af > bi && af < bf)) return true;

        return false;
    }

}