package domain;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * La classe Horari contee l'algorisme de backtracking encarregat de trobar un horari valid
 * donat el conjunt de dades de totes les Assignacions.
 * @author David Pujol
 */

public class Horari implements Serializable {

    private HashMap<String, assignacio> conjuntAssignacions = new HashMap<>();   //fem servir linked per mantenir el ordre d'entrada
    private LinkedList<assignacio> l;
    private ArrayList<Classe> classesSeleccionades = new ArrayList<>();//ho guardem en forma de stack perque quan retrocedim sempre treurem la ultima afegida
    private Map<String, Aula> aules;

    /**
     * Creadora de la classe Horari.
     *
     * @param conjuntAssignacions ArrayList amb tot el conjunt d'assignacions que hem d'assignar al nostre horari.
     */
    public Horari(LinkedList<assignacio> conjuntAssignacions, Map<String, Aula> aules) {
        afegeixAssignacions(conjuntAssignacions);
        this.l = conjuntAssignacions;
        this.aules = aules;
    }


    /**
     * Afegeix totes les assignacions passades pel paràmetre al map de la classe horari.
     * Primer afegirem les assignacions de matins i despres les de tardes per motius d'optimització de l'algorisme.
     *
     * @param conjuntAssignacions ArrayList amb tot el conjunt d'assignacions que hem d'assignar al nostre horari.
     */
    private void afegeixAssignacions(LinkedList<assignacio> conjuntAssignacions) {
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
    private boolean selectClasse(int index) {
        if (index < l.size()) {
            assignacio a = l.get(index);
            ArrayList<Classe> possibleClasses = a.getAllPossibleClasses();
            Collections.shuffle(possibleClasses);   //barrejem les possibilitats per tal de trobar un algorisme random

            for (int i=0; i < possibleClasses.size(); ++i) {
                Classe c = possibleClasses.get(i);
                a.afegirSeleccionada();    //aixo cal??
                Stack<Classe> eliminades = new Stack();

                boolean valid = forward_checking (c, eliminades); //forward checking
                classesSeleccionades.add(c);

                if (valid)
                {
                    boolean r;
                    if (a.getNumeroClassesRestants() == 0) {
                        r = selectClasse(index + 1);
                    }
                    else
                        r = selectClasse(index);

                    if (r) return r;
                }

                classesSeleccionades.remove(classesSeleccionades.size() - 1);
                revertChanges(eliminades, c);
                a.eliminarSeleccionada();

            }

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



    //GENERAR EL HORARI EN FUNCIO DE PREFERENCIES.
    private boolean generaHorariAmbPreferencies (HashMap<String, ArrayList<IntervalHorari>> preferencies)
    {
        if (preferencies.isEmpty())//ja no queden preferencies
            return selectClasse(0);

        else {
            if (generaHorariAmbPreassignacions(preferencies)) return true;
            else {
                preferencies.remove(0);
                if (generaHorariAmbPreferencies(preferencies)) //retornara 1 si existeix un que compleix el maxim de restriccions
                    return true;
            }
        }
        return false;
    }



    /**
     * @param preferencies Sera una relacio entre el id de la assignatura i grup i els horaris en el que no es poden donar.
     */
    private boolean generaHorariAmbPreassignacions (HashMap<String, ArrayList<IntervalHorari>> preferencies)
    {
        for (Map.Entry<String, ArrayList<IntervalHorari>> aux : preferencies.entrySet())
        {
            String nomAssignacion = aux.getKey();
            assignacio a = conjuntAssignacions.get(nomAssignacion);

            //tornem a generar les possibles classes perque pot ser que amb la anterior passada de preferencies les
            //haguessim eliminat i ens cal tornar al principi.
            a.generaPossiblesClasses(aules);
            a.eliminarPossibilitatsIntervels(aux.getValue());
        }

        return (selectClasse(0));
    }




    /**
     * Operacio que ens permet modificar una de les assignacions que tenim dins de l'horari per una d'altre. Tot i aixi,
     * abans de fer el canvi, comprovarem que no hi ha cap restriccio que ens ho impedeixi.
     * @param idAssig
     * @param idGrup
     * @param diaAntic
     * @param horaAntiga
     * @param diaNou
     * @param horaNova
     * @param aulaNova
     * @return True si hem pogut fer el canvi, false altrament.
     */
    public boolean modificaClasse(String idAssig, String idGrup, DiaSetmana diaAntic, int horaAntiga, DiaSetmana diaNou, int horaNova, String aulaNova) {
        //la eliminem de les seleccionades fins al moment
        Classe borrada = null;
        int i = 0;
        boolean found = false;
        while (!found && i < classesSeleccionades.size()) {
            Classe c = classesSeleccionades.get(i); //seleccionem una de les classes seleccionades
            if (c.getId_assig().equals(idAssig) && c.getId_grup().equals(idGrup) && c.getHoraInici() == horaAntiga && c.getDia().equals(diaAntic)) {
                borrada = c;    //entrem aqui si hem trobat la classe
                classesSeleccionades.remove(c);
                conjuntAssignacions.get(c.getId_assig() + c.getId_grup()).eliminarSeleccionada();    //tambe la eliminem de les seleccionades de la assignacio
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

            //ara comprovem si la podem afegir

            for (Classe c : classesSeleccionades) {
                if (Restriccio.solapenHores(c.getHoraInici(), c.getHoraFi(), m.getHoraInici(), m.getHoraFi())
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
            a.afegirSeleccionada();
            classesSeleccionades.add(m);
            return true;

        }
        System.out.println("No hem trobat la classe indicada");
        return false;

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

        ArrayList<ArrayList<Queue<String>>> horaris = new ArrayList<>();    //inicialitzacio de una matriu formada per 16files (hores) x 5 dies
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

        //quan sortim d'aqui ja tenim la nostre matriu organitzada amb 16 files i 5 columnes. Cada "quadrat" conte una queue amb tots els seus valors

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



    public LinkedList<LinkedList<Queue<String>>> getHorariSencer ()
    {
        LinkedList<LinkedList<Queue<String>>> horaris = new LinkedList<>();    //inicialitzacio de una matriu formada per 16files (hores) x 5 dies
        for (int i = 0; i < 12; ++i) {  //primerament ho organitzem per hores (de 8 a 8)
            horaris.add(new LinkedList<>());
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
        return horaris;
    }


    public Queue<LinkedList<LinkedList<Queue<String>>>> getHorariAssignatures ()
    {
        Set<String> assigs = new TreeSet<>();   //conjunt de assignatures
        ArrayList<Classe> classesSeleccionades = new ArrayList<>(this.classesSeleccionades);

        for (Classe c : classesSeleccionades)
            assigs.add(c.getId_assig());

        Queue<LinkedList<LinkedList<Queue<String>>>> horarisAssignatures = new PriorityQueue<>();

        for (String ass : assigs) {
            LinkedList<LinkedList<Queue<String>>> horaris = new LinkedList<>();
            for (int i = 0; i < 12; ++i) {
                horaris.add(new LinkedList<>());
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
            horarisAssignatures.add(horaris);
        }
        return horarisAssignatures;
    }


    public Queue<LinkedList<LinkedList<Queue<String>>>> getHorariAules ()
    {
        Set<String> aules = new TreeSet<>(this.aules.keySet());

        ArrayList<Classe> classesSeleccionades = new ArrayList<>(this.classesSeleccionades);

        Queue<LinkedList<LinkedList<Queue<String>>>> horarisAules = new PriorityQueue<>();

        for (String aula : aules) {
            LinkedList<LinkedList<Queue<String>>> horaris = new LinkedList<>();
            for (int i = 0; i < 12; ++i) {
                horaris.add(new LinkedList<>());
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
            horarisAules.add(horaris);
        }
        return horarisAules;
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