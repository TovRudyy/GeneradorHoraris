package testsClasses.Drivers;

import domain.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.*;

/**
 * @author Victor
 */

class driverAssignatura {


    private static Map<String, Aula> aules = new TreeMap<>();

    private static assignatura ass = new assignatura("AI","Assignatura_Inicial", 0);

    static void main() {
        initAules();
        printMenu();
        executar();
    }

    /**
     * Inicialitza unes instancies de la classe aula.
     */
    private static void initAules() {
        aules.put("AulaT", new Aula("AulaT", 9999999, Tipus_Aula.TEORIA));
        aules.put("AulaP", new Aula("AulaP", 9999999, Tipus_Aula.PROBLEMES));
        aules.put("AulaLI", new Aula("AulaLI", 9999999, Tipus_Aula.LAB_INFORMATICA));
        aules.put("AulaLF", new Aula("AulaLF", 9999999, Tipus_Aula.LAB_FISICA));
        aules.put("AulaLE", new Aula("AulaLE", 9999999, Tipus_Aula.LAB_ELECTRONICA));
    }

    /**
     * Imprimeix un menu amb les opcions.
     */
    private static void printMenu() {
        out.println("Driver de la Classe Assignatura.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat: ");
        printCodis();
        out.println("Aquest driver mante una unica instancia d'Assignatura, que va sobreescribint cada cop que proves la constructora.");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
    }

    /**
     * Executa la opcio en funcio del codi introduit per l'usuari.
     */
    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while((codi = keyboard.nextInt()) != 8) {
                switch (codi) {
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        constructor_Test();
                        break;
                    case 2:
                        getters_Test();
                        break;
                    case 3:
                        setters_Test();
                        break;
                    case 4:
                        afegir_corequisit_Test();
                        break;
                    case 5:
                        no_Solapis_Test();
                        break;
                    case 6:
                        to_String_Test();
                        break;
                    case 7:
                        executar_Joc_de_Proves();
                        break;
                    default:
                        keyboard.nextLine();
                        out.println("Codi no valid. Aqui tens els codis que ho son: ");
                        printCodis();
                }
                out.println();
                out.println("Introdueix un codi:");
            }
        }catch(
        InputMismatchException ime){
            out.println("Codi no valid. Aqui tens els codis que ho son: ");
            printCodis();
            keyboard.nextLine();
            executar();
        }
    }

    /**
     * Executa el joc de proves d'aquest driver i a mes a mes, mostre el output esperat.
     */
    private static void executar_Joc_de_Proves() {
        try{
            keyboard = new Scanner(new FileReader("data/Jocs_de_Prova_Drivers/9.DriverAssignatura"));
            output = new PrintStream(new File("data/Jocs_de_Prova_Drivers/Sortida_Jocs"));
        }catch(FileNotFoundException fnfe){
            out.println(fnfe.getMessage());
        }
    }

    /**
     * Afegeix un correquisit a la assignatura.
     */
    private static void afegir_corequisit_Test() {
        out.println("Introdueix l'identificador de la assignatura corequisit:");
        out.println("\tIdentificador<String>");
        ass.addCorrequisit(keyboard.next());
    }

    /**
     * Retorna una string del test, tant sigui curta o completa.
     */
    private static void to_String_Test() {
        output.println("Versio curta: ");
        output.println("\t" + ass.toString());
        output.println("Versio completa: ");
        output.println("\t" + ass.toStringComplet());

    }

    /**
     * Crea les restriccions de subgrup corresponents.
     */
    private static void no_Solapis_Test() {
        out.println("Aquesta funcio s'encarrega de afegir a cada grup les seves restriccions de subgrup");
        output.println("\tSubgrups abans de la funcio:");
        for(grup g: ass.getGrups().values()){
            output.println("\t\t" + ((g.getSubgrup() == null)? "null" : g.getSubgrup().toString()));
        }
        ass.noSolapis_Teoria_i_Problemes();

        output.println("\tSubgrups despres de la funcio:");
        for(grup g: ass.getGrups().values()){
            output.println("\t\t" + g.getId() + " ->" + ((g.getSubgrup() == null)? "null" : g.getSubgrup().toString()));
        }
    }

    /**
     * Fa un setter de un atribut concret de la assignatura.
     */
    private static void setters_Test() {
        out.println("Tria el setter que vols provar introduient el seu codia associat: ");
        out.println("\t1) Grups");
        out.println("\t2) Classes");
        switch(keyboard.nextInt()){
            case 1:
                out.println("Introdueix el nombre de grups + subgrups que vols introduir a la assignatura:");
                TreeMap<String, grup> grups = new TreeMap<>();
                int n = keyboard.nextInt();
                out.println("Introdueix els seguents atributs per cada grup, separats per un espai:(-1 per sortir)");
                out.println("\tIdentificador<String> Capacitat<int> Horari<M | T> Tipus<Tipus_Aula>");
                for(int i=0; i<n; ++i){
                    try {
                        String id = keyboard.next();
                        if(id.equals("-1")) return;
                        grups.put(id, new grup(id, keyboard.nextInt(), keyboard.next(), Tipus_Aula.string_to_Tipus_Aula(keyboard.next())));
                        output.println("Grup afegit.");
                    }catch(Aula_Exception ae){
                        output.println(ae.getMessage());
                        --i;
                        output.println("Torna a introduir el grup.");
                    }catch (InputMismatchException ime){
                        output.println("Has introduit algun atribut incorrectament.");
                        --i;
                        output.println("Torna a introduir el grup.");
                    }
                }
                ass.creaGrups(grups);
                break;
            case 2:
                out.println("Introdueix els seguents parametres, separats per un espai:");
                out.println("\tNombre_Teories<int> Duracio_Teories<int> Nombre_Laboratoris<int> Duracio_Laboratoris<int> Nombre_Problemes<int> Duracio_Problemes<int>");
                ass.setClasses(keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt());
                break;
            default:
                keyboard.nextLine();
                out.println("Codi d'opcio no valid.");
        }
    }

    /**
     * Fa un getter de un atribut concret de la assignatura.
     */
    private static void getters_Test() {
        out.println("Tria el getter que vols provar introduint el seu codi associat: ");
        out.println("\t1) Identificador");
        out.println("\t2) Nom");
        out.println("\t3) Nivell");
        out.println("\t4) Hores de classe");
        out.println("\t5) Corequisit");
        out.println("\t6) Grups");
        out.println("\t7) Assignacions");
        out.println("\t8) Tots");
        switch (keyboard.nextInt()){
            case 1:
                output.println("Identificador: " + ass.getId());
                break;
            case 2:
                output.println("Nom: " + ass.getNom());
                break;
            case 3:
                output.println("Nivell: " + ass.getNivell());
                break;
            case 4:
                output.println("Classes: ");
                captura();
                ass.showClasses();
                allibera();
                break;
            case 5:
                output.println("Corequisit: ");
                output.println("\t" + ass.getCorequisits().toString());
                break;
            case 6:
                output.println("Grups: ");
                captura();
                ass.showGrups();
                allibera();
                break;
            case 7:
                output.println("Assignacions: ");
                for(assignacio a: ass.getAssignacions(aules)) output.println("\t" + a.toString());
                break;
            case 8:
                captura();
                out.println("Identificador: " + ass.getId());
                out.println("Nom: " + ass.getNom());
                out.println("Nivell: " + ass.getNivell());
                out.println("Classes: ");
                ass.showClasses();
                out.println("Corequisit: ");
                out.println("\t" + ass.getCorequisits().toString());
                out.println("Grups: ");
                ass.showGrups();
                out.println("Assignacions: ");
                for(assignacio a: ass.getAssignacions(aules)) out.println("\t" + a.toString());
                allibera();
                break;
            default:
                keyboard.nextLine();
                out.println("Codi d'opcio no valid.");
        }
    }

    /**
     * Construeix el test.
     */
    private static void constructor_Test() {
        out.println("Introdueix els seguent atributs, separats per un espai:");
        out.println("\tIdentificador<String> Nom<String(sense espais)> Nivell<int>");
        try{
            ass = new assignatura(keyboard.next(), keyboard.next(), keyboard.nextInt());
        }catch(IllegalArgumentException iae){
            output.println("Has introduit algun dels atributs incorrectament.");
        }
    }

    /**
     * Imprimeix els codis amb les opcions que te disponibles l'usuari.
     */
    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Getters");
        out.println("\t3) Setters");
        out.println("\t4) Afegir corequisit");
        out.println("\t5) Funcio anti-solapament");
        out.println("\t6) To String");
        out.println("\t7) Executar Joc de Proves");
        out.println("\t8) Sortir");
    }
}
