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
import static testsClasses.Drivers.MainDriver.keyboard;
import static testsClasses.Drivers.MainDriver.output;

class driverAssignacio {

    private static Map<String, Aula> aules = new TreeMap<>();

    private static assignacio ass = new assignacio("10", 60, Tipus_Aula.TEORIA, "FM", 1, 2, 2, "M", aules);

    static void main() {
        initAules();
        printMenu();
        executar();
    }

    private static void initAules() {
        aules.put("AulaT", new Aula("AulaT", 9999999, Tipus_Aula.TEORIA));
        aules.put("AulaP", new Aula("AulaP", 9999999, Tipus_Aula.PROBLEMES));
        aules.put("AulaLI", new Aula("AulaLI", 9999999, Tipus_Aula.LAB_INFORMATICA));
        aules.put("AulaLF", new Aula("AulaLF", 9999999, Tipus_Aula.LAB_FISICA));
        aules.put("AulaLE", new Aula("AulaLE", 9999999, Tipus_Aula.LAB_ELECTRONICA));
        aules.put("AulaT2", new Aula("AulaT2", 9999999, Tipus_Aula.TEORIA));
        aules.put("AulaP2", new Aula("AulaP2", 9999999, Tipus_Aula.PROBLEMES));
        aules.put("AulaLI2", new Aula("AulaLI2", 9999999, Tipus_Aula.LAB_INFORMATICA));
        aules.put("AulaLF2", new Aula("AulaLF2", 9999999, Tipus_Aula.LAB_FISICA));
        aules.put("AulaLE2", new Aula("AulaLE2", 9999999, Tipus_Aula.LAB_ELECTRONICA));
    }

    private static void printMenu() {
        out.println("Driver de la Classe Assignacio.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat:");
        printCodis();
        out.println("Aquest driver mante una unica instancia d'Assignacio, que va sobreescribint cada cop que proves la constructora.");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
        out.println("Tot i que son funcions separades, et recomanem que just despres de crear una nova assignacio, fagis el setSubgrup(si es un subgrup) i setCorequisit(si te corequisits).");
    }

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Getters");
        out.println("\t3) Setters");
        out.println("\t4) Afegir possible Classe");
        out.println("\t5) Forward Checking");
        out.println("\t6) Eliminar classes no seleccionades");
        out.println("\t7) Seleccionar classe");
        out.println("\t8) Deseleccionar classe");
        out.println("\t9) Obtenir classes seleccionades");
        out.println("\t10) Consultora combinacio possible");
        out.println("\t11) To String");
        out.println("\t12) Executar Joc de Proves");
        out.println("\t13) Sortir");

    }

    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while((codi = keyboard.nextInt()) != 13){
                switch (codi){
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
                        afegeix_possibilitat_Test();
                        break;
                    case 5:
                        forward_Checking_Test();
                        break;
                    case 6:
                        nomes_Seleccionades_Test();
                        break;
                    case 7:
                        afegir_Seleccionada_Test();
                        break;
                    case 8:
                        eliminar_Seleccionada_Test();
                        break;
                    case 9:
                        get_Seleccionades_Test();
                        break;
                    case 10:
                        is_Empty_Test();
                        break;
                    case 11:
                        to_String_Test();
                        break;
                    case 12:
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
        }catch(InputMismatchException ime){
            out.println("Codi no valid. Aqui tens els codis que ho son: ");
            printCodis();
            keyboard.nextLine();
            executar();
        }
    }

    private static void executar_Joc_de_Proves() {
        try{
            keyboard = new Scanner(new FileReader("data/Jocs_de_Prova_Drivers/8.DriverAssignacio"));
            output = new PrintStream(new File("data/Jocs_de_Prova_Drivers/Sortida_Jocs"));
        }catch(FileNotFoundException fnfe){
            out.println(fnfe.getMessage());
        }
    }

    private static void constructor_Test() {
        out.println("Introdueix els seguents atributs, separats per un espai:");
        out.println("\tId_Grup<String> Capacitat<int> Tipus<Tipus_Aula> Id_Assignatura<String> nivellAssignatura<int> nombreClasses<int> duracioClasses<int> horariGrup<M | T>");
        try{
            ass = new assignacio(keyboard.next(), keyboard.nextInt(), Tipus_Aula.string_to_Tipus_Aula(keyboard.next()), keyboard.next(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.next(), aules);
        }catch(IllegalArgumentException iae){
            output.println("Has introduit algun dels atributs incorrectament.");
        }catch(Aula_Exception ae){
            output.println(ae.getMessage());
        }
    }

    private static void getters_Test() {
        out.println("Tria el getter que vols provar introduint el seu codi associat: ");
        out.println("\t1) Assignatura");
        out.println("\t2) Grup");
        out.println("\t3) Classes possibles");
        out.println("\t4) Nombre de Classes restants");
        out.println("\t5) Tots");
        switch (keyboard.nextInt()){
            case 1:
                output.println("Identificador assignatura: " + ass.getIdAssig());
                break;
            case 2:
                output.println("Identificador grup: " + ass.getIdGrup());
                break;
            case 3:
                output.println("Classes posibles: ");
                for(Classe c: ass.getAllPossibleClasses()) output.println("\t" + c.toString());
                break;
            case 4:
                output.println("Classes restants: " + ass.getNumeroClassesRestants());
                break;
            case 5:
                output.println("Identificador assignatura: " + ass.getIdAssig());
                output.println("Identificador grup: " + ass.getIdGrup());
                output.println("Classes posibles: ");
                for(Classe c: ass.getAllPossibleClasses()) output.println("\t" + c.toString());
                output.println("Classes restants: " + ass.getNumeroClassesRestants());
                break;
            default:
                keyboard.nextLine();
                out.println("Codi d'opcio no valid.");
        }
    }

    private static void setters_Test() {
        out.println("Tria el setter que vols provar introduint el seu codi associat: ");
        out.println("\t1) RestriccioCorequisit");
        out.println("\t2) RestriccioSubgrup");
        switch (keyboard.nextInt()){
            case 1:
                RestriccioCorequisit corequisit = new RestriccioCorequisit();
                out.println("Introdueix totes les Assignatures(nomes identificadors) que son correquisits de la Assignatura d'aquesta assignacio separats per un espai amb un \".\" al final de la linia:");
                String s;
                while(!(s = keyboard.next()).equals(".")) corequisit.addAssignatura(s);
                ass.afegirCorrequisit(corequisit);
                output.println("S'ha afegit el seguent corequisit: " + corequisit.toString());
                break;
            case 2:
                out.println("Introdueix les dades del grup del qual el grup de la assignacio es subgrup: ");
                out.println("\tIdentificador<String> Capacitat<int> Horari<M | T> Tipus<Tipus_Aula>");
                try {
                    ass.afegirSubgrup(new RestriccioSubgrup(new grup(keyboard.next(), keyboard.nextInt(), keyboard.next(), Tipus_Aula.string_to_Tipus_Aula(keyboard.next()))));
                }catch(Aula_Exception ae){
                    output.println(ae.getMessage());
                }catch (InputMismatchException ime){
                    output.println("Has introduit algun atribut incorrectament");
                }
                break;
            default:
                keyboard.nextLine();
                out.println("Codi d'opcio no valid.");
        }
    }

    private static void afegeix_possibilitat_Test() {
        out.println("Introdueix la classe que vols afegir a la llista de possibilitats:");
        out.println("\tId_Aula<String>, Dia<DiaSetmana> HoraInicia<int> HoraFinal<int>");
        try{
            String au = keyboard.next(), dia = keyboard.next();
            int ini = keyboard.nextInt(), fin = keyboard.nextInt();
            if(ini > fin){
                output.println("L'hora final ha de ser posterior a la inicial.");
                return;
            }
            ass.afegeixPossibilitat(new Classe(ass.getIdAssig(), ass.getIdGrup(), DiaSetmana.string_To_DiaSetmana(dia), ini, fin, au));
        }catch(IllegalArgumentException iae){
            output.println("Has introduit algun dels atributs incorrectament.");
        }catch(NullPointerException npe){
            output.println("Aquesta Aula no es valida.");
        }
    }

    private static void forward_Checking_Test() {
        out.println("Introdueix la classe amb la que vols podar les possibilitats:");
        out.println("Tingues en compte que no es comprova que aquesta classe sigui una de les possibles.");
        out.println("\tId_Assignatura<String> Id_Grup<String> Id_Aula<String>, Dia<DiaSetmana> HoraInicia<int> HoraFinal<int>");
        try{
            String as = keyboard.next(), g = keyboard.next(), au = keyboard.next(), dia = keyboard.next();
            int ini = keyboard.nextInt(), fin = keyboard.nextInt();
            if(ini > fin){
                output.println("L'hora final ha de ser posterior a la inicial.");
                return;
            }
            output.println("Aquestes son les possibles classes que han sigut eliminades:");
            for(Classe c :ass.forwardChecking(new Classe(as, g, DiaSetmana.string_To_DiaSetmana(dia), ini, fin, au))) output.println("\t" + c.toString());
        }catch(IllegalArgumentException iae){
            out.println("Has introduit algun dels atributs incorrectament.");
        }

    }

    private static void nomes_Seleccionades_Test() {
        output.println("Aquestes son totes les possibilitats que hi havien i han sigut eliminades:");
        for(Classe c: ass.nomesSeleccionades()) output.println("\t" + c.toString());
    }

    private static void afegir_Seleccionada_Test() {
        out.println("Introdueix la classe que vols afegir a la llista de possibilitats:");
        out.println("Tingues en compte que no es comprova que aquesta classe sigui una de les possibles.");
        out.println("\tId_Aula<String>, Dia<DiaSetmana> HoraInicia<int> HoraFinal<int>");
        try{
            String au = keyboard.next(), dia = keyboard.next();
            int ini = keyboard.nextInt(), fin = keyboard.nextInt();
            if(ini > fin){
                output.println("L'hora final ha de ser posterior a la inicial.");
                return;
            }
            ass.afegirSeleccionada(new Classe(ass.getIdAssig(), ass.getIdGrup(), DiaSetmana.string_To_DiaSetmana(dia), ini, fin, au));
        }catch(IllegalArgumentException iae){
            output.println("Has introduit algun dels atributs incorrectament.");
        }
    }

    private static void eliminar_Seleccionada_Test() {
        out.println("Introdueix la classe que vols eliminar de la llista de possibilitats:");
        out.println("Tingues en compte que no es llença cap error si la classe no es seleccionada (pero tampoc te cap efecte secundari).");
        out.println("\tId_Assignatura<String> Id_Grup<String> Id_Aula<String>, Dia<DiaSetmana> HoraInicia<int> HoraFinal<int>");
        try{
            String as = keyboard.next(), g = keyboard.next(), au = keyboard.next(), dia = keyboard.next();
            int ini = keyboard.nextInt(), fin = keyboard.nextInt();
            if(ini > fin){
                output.println("L'hora final ha de ser posterior a la inicial.");
                return;
            }
            ass.eliminarSeleccionada(new Classe(as, g, DiaSetmana.string_To_DiaSetmana(dia), ini, fin, au));
        }catch(IllegalArgumentException iae){
            output.println("Has introduit algun dels atributs incorrectament.");
        }
    }

    private static void get_Seleccionades_Test() {
        output.println("Aquestes son les Classes seleccionades: ");
        for(Classe c:ass.getSeleccionades()) output.println("\t" + c.toString());
    }

    private static void is_Empty_Test() {
        out.println("Aquesta funcio retorna true si no queden prous classes possibles com per satisfer les necessitats:");
        output.println("Resultat: " + ass.isEmpty());
    }

    private static void to_String_Test() {
        output.println("Resultat: " + ass.toString());
    }
}
