package testsClasses.Drivers;

import domain.*;

import java.util.InputMismatchException;
import java.util.TreeMap;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;

class driverAssignatura {

    private static assignatura ass = new assignatura("AI","Assignatura_Inicial", 0);

    static void main() {
        printMenu();
        executar();
    }

    private static void printMenu() {
        out.println("Driver de la Classe Assignatura.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat: ");
        printCodis();
        out.println("Aquest driver mante una unica instancia d'Assignatura, que va sobreescribint cada cop que proves la constructora.");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
    }

    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while((codi = keyboard.nextInt()) != 7) {
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

    private static void afegir_corequisit_Test() {
        out.println("Introdueix l'identificador de la assignatura corequisit:");
        out.println("\tIdentificador<String>");
        ass.addCorrequisit(keyboard.next());
    }

    private static void to_String_Test() {
        out.println("Versio curta: ");
        out.println("\t" + ass.toString());
        out.println("Versio completa: ");
        out.println("\t" + ass.toStringComplet());

    }

    private static void no_Solapis_Test() {
        out.println("Aquesta funcio s'encarrega de afegir a cada grup les seves restriccions de subgrup");
        out.println("\tSubgrups abans de la funcio:");
        for(grup g: ass.getGrups().values()){
            out.println("\t\t" + ((g.getSubgrup() == null)? "null" : g.getSubgrup().toString()));
        }
        ass.noSolapis_Teoria_i_Problemes();

        out.println("\tSubgrups despres de la funcio:");
        for(grup g: ass.getGrups().values()){
            out.println("\t\t" + g.getId() + " ->" + ((g.getSubgrup() == null)? "null" : g.getSubgrup().toString()));
        }
    }

    private static void setters_Test() {
        out.println("Tria el setter que vols provar introduient el seu codia associat: ");
        out.println("\t1) Grups");
        out.println("\t2) Classes");
        switch(keyboard.nextInt()){
            case 1:
                out.println("Introdueix el nombre de grups que vols introduir a la assignatura:");
                TreeMap<String, grup> grups = new TreeMap<>();
                int n = keyboard.nextInt();
                out.println("Introdueix els seguents atributs per cada grup, separats per un espai:(-1 per sortir)");
                out.println("\tIdentificador<String> Capacitat<int> Horari<M | T> Tipus<Tipus_Aula>");
                for(int i=0; i<n; ++i){
                    try {
                        String id = keyboard.next();
                        if(id.equals("-1")) return;
                        grups.put(id, new grup(id, keyboard.nextInt(), keyboard.next(), Tipus_Aula.string_to_Tipus_Aula(keyboard.next())));
                        out.println("Grup afegit.");
                    }catch(Aula_Exception ae){
                        out.println(ae.getMessage());
                        --i;
                        out.println("Torna a introduir el grup.");
                    }catch (InputMismatchException ime){
                        out.println("Has introduit algun atribut incorrectament.");
                        --i;
                        out.println("Torna a introduir el grup.");
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
                out.println("Identificador: " + ass.getId());
                break;
            case 2:
                out.println("Nom: " + ass.getNom());
                break;
            case 3:
                out.println("Nivell: " + ass.getNivell());
                break;
            case 4:
                out.println("Classes: ");
                ass.showClasses();
                break;
            case 5:
                out.println("Corequisit: ");
                out.println(ass.getCorequisits().toString());
                break;
            case 6:
                out.println("Grups: ");
                ass.showGrups();
                break;
            case 7:
                out.println("Assignacions: ");
                for(assignacio a: ass.getAssignacions()) out.println("\t" + a.toString());
                break;
            case 8:
                out.println("Identificador: " + ass.getId());
                out.println("Nom: " + ass.getNom());
                out.println("Nivell: " + ass.getNivell());
                out.println("Classes: ");
                ass.showClasses();
                out.println("Corequisit: ");
                out.println(ass.getCorequisits().toString());
                out.println("Grups: ");
                ass.showGrups();
                out.println("Assignacions: ");
                for(assignacio a: ass.getAssignacions()) out.println("\t" + a.toString());
                break;
            default:
                keyboard.nextLine();
                out.println("Codi d'opcio no valid.");
        }
    }

    private static void constructor_Test() {
        out.println("Introdueix els seguent atributs, separats per un espai:");
        out.println("\tIdentificador<String> Nom<String(sense espais)> Nivell<int>");
        try{
            ass = new assignatura(keyboard.next(), keyboard.next(), keyboard.nextInt());
        }catch(IllegalArgumentException iae){
            out.println("Has introduit algun dels atributs incorrectament.");
        }
    }

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Getters");
        out.println("\t3) Setters");
        out.println("\t4) Afegir corequisit");
        out.println("\t5) Funcio anti-solapament");
        out.println("\t6) To String");
        out.println("\t7) Sortir");
    }
}
