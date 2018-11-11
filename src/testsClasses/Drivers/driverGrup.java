package testsClasses.Drivers;

import domain.Aula_Exception;
import domain.Tipus_Aula;
import domain.grup;
import testsClasses.Stubs.StubRestriccioSubgrup;

import java.util.InputMismatchException;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;

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
        out.println("Aquest driver mante una unica instancia de Grup, que va sobreescribint cada cop que proves la constructora.");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
        out.println("Com pots veure, no hi ha setters. Aixo es perque no permetem modificar els grups un cop creats.");
        out.println("En aquest driver s'utilitza un Stub de la classe RestriccioSubgrup. Per aixo, al provar el getter de subgrup, et sortiran uns valors per defecte");
    }

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Getters");
        out.println("\t3) Sortir");
    }

    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while ((codi = keyboard.nextInt()) != 3){
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
                out.println("Identificador : " + grup.getId());
                break;
            case 2:
                out.println("Capacitat : " + grup.getCapacitat());
                break;
            case 3:
                out.println("Tipus_Aula : " + grup.getTipus());
                break;
            case 4:
                out.println("Horari : " + grup.getHorariAssig());
                break;
            case 5:
                out.println("Subgrup : " + grup.getSubgrup().toString());
                break;
            case 6:
                out.println("Identificador : " + grup.getId());
                out.println("Capacitat : " + grup.getCapacitat());
                out.println("Tipus_Aula : " + grup.getTipus());
                out.println("Horari : " + grup.getHorariAssig());
                out.println("Subgrup : " + grup.getSubgrup().toString());
                break;
            default:
                out.println("Codi d'opcio no valid.");
        }
    }

    private static void constructor_Test() {
        out.println("Introdueix els seguents atributs, separats per un espai:");
        out.println("\tIdentificador<String> Capacitat<int> Horari<M | T> Tipus<Tipus_Aula>");
        try {
            grup = new grup(keyboard.next(), keyboard.nextInt(), keyboard.next(), Tipus_Aula.string_to_Tipus_Aula(keyboard.next()));
            grup.afegirRestriccio(new StubRestriccioSubgrup());
        }catch(Aula_Exception ae){
            out.println(ae.getMessage());
        }catch (InputMismatchException ime){
            out.println("Has introduit algun atribut incorrectament");
        }
    }
}
