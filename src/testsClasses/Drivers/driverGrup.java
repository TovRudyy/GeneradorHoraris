package testsClasses.Drivers;

import domain.Aula_Exception;
import domain.Tipus_Aula;
import domain.grup;
import testsClasses.Stubs.StubRestriccioSubgrup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;
import static testsClasses.Drivers.MainDriver.output;

class driverGrup {

    private static grup grup = new grup("Id_Inicial", 0, "M", Tipus_Aula.TEORIA);

    static void main() {
        grup.afegirRestriccio(new StubRestriccioSubgrup());
        printMenu();
        executar();
    }

    private static void printMenu() {
        out.println("Driver de la Classe Grup.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat: ");
        printCodis();
        out.println("Tot i que el driver no ho comprova, perque no es rellevant per la classe, el programa real admet una unica codificacio pels identificadors.");
        out.println("Aquesta codificacio es la que s'utilitza a la FIB: tots els grups de teoria tenen un nombre acabat en 0, mentre que els seus subgrups tenen el mateix sense acabar en 0.");
        out.println("Aquest driver mante una unica instancia de Grup, que va sobreescribint cada cop que proves la constructora.");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
        out.println("Com pots veure, no hi ha setters. Aixo es perque no permetem modificar els grups un cop creats.");
        out.println("En aquest driver s'utilitza un Stub de la classe RestriccioSubgrup. Per aixo, al provar el getter de subgrup, et sortiran uns valors per defecte");
    }

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Getters");
        out.println("\t3) Executar Joc de Proves");
        out.println("\t4) Sortir");
    }

    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while ((codi = keyboard.nextInt()) != 4){
                switch (codi) {
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        constructor_Test();
                        break;
                    case 2:
                        getter_Test();
                        break;
                    case 3:
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
            keyboard = new Scanner(new FileReader("data/Jocs_de_Prova_Drivers/4.DriverGrup"));
            output = new PrintStream(new File("data/Jocs_de_Prova_Drivers/Sortida_Jocs"));
        }catch(FileNotFoundException fnfe){
            out.println(fnfe.getMessage());
        }
    }

    private static void getter_Test() {
        out.println("Tria el getter que vols provar introduint el seu codi associat: ");
        out.println("\t1) Identificador");
        out.println("\t2) Capacitat");
        out.println("\t3) Tipus");
        out.println("\t4) Horari");
        out.println("\t5) Subgrup");
        out.println("\t6) Tots");
        switch (keyboard.nextInt()) {
            case 1:
                output.println("Identificador : " + grup.getId());
                break;
            case 2:
                output.println("Capacitat : " + grup.getCapacitat());
                break;
            case 3:
                output.println("Tipus_Aula : " + grup.getTipus());
                break;
            case 4:
                output.println("Horari : " + grup.getHorariAssig());
                break;
            case 5:
                output.println("Subgrup : " + grup.getSubgrup().toString());
                break;
            case 6:
                output.println("Identificador : " + grup.getId());
                output.println("Capacitat : " + grup.getCapacitat());
                output.println("Tipus_Aula : " + grup.getTipus());
                output.println("Horari : " + grup.getHorariAssig());
                output.println("Subgrup : " + grup.getSubgrup().toString());
                break;
            default:
                out.println("Codi d'opcio no valid.");
                keyboard.nextLine();
        }
    }

    private static void constructor_Test() {
        out.println("Introdueix els seguents atributs, separats per un espai:");
        out.println("\tIdentificador<String> Capacitat<int> Horari<M | T> Tipus<Tipus_Aula>");
        try {
            String id = keyboard.next();
            int cap = keyboard.nextInt();
            String hor = keyboard.next();
            if(!hor.equals("M") && !hor.equals("T")) throw new InputMismatchException();
            grup = new grup(id, cap, hor, Tipus_Aula.string_to_Tipus_Aula(keyboard.next()));
            grup.afegirRestriccio(new StubRestriccioSubgrup());
        }catch(Aula_Exception ae){
            output.println(ae.getMessage());
            keyboard.nextLine();
        }catch (InputMismatchException ime){
            output.println("Has introduit algun atribut incorrectament");
            keyboard.nextLine();
        }
    }
}
