package testsClasses.Drivers;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.out;

public class MainDriver {

    static Scanner keyboard;

    public static void main(String[] args) {
        keyboard = new Scanner(System.in);
        printMenu();
        executar();
    }

    private static void executar(){
        try {
            int codi;
            out.println("Introdueix un codi: ");
            while ((codi = keyboard.nextInt()) != 12) {
                switch (codi) {
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        driverTipus_Aula.main();
                        printMenu();
                        break;
                    case 2:
                        driverAula.main();
                        printMenu();
                        break;
                    case 3:
                        driverClasse.main();
                        //TODO
                        printMenu();
                        break;
                    case 4:
                        driverGrup.main();
                        //TODO
                        printMenu();
                        break;
                    case 5:
                        driverRestriccioOcupacio.main();
                        //TODO
                        printMenu();
                        break;
                    case 6:
                        driverRestriccioSubgrup.main();
                        //TODO
                        printMenu();
                        break;
                    case 7:
                        driverControladorAules.main();
                        //TODO
                        printMenu();
                        break;
                    case 8:
                        driverAssignacio.main();
                        //TODO
                        printMenu();
                        break;
                    case 9:
                        driverAssignatura.main();
                        //TODO
                        printMenu();
                        break;
                    case 10:
                        driverHorari.main();
                        //TODO
                        printMenu();
                        break;
                    case 11:
                        driverPlaEstudis.main();
                        //TODO
                        printMenu();
                        break;
                    default:
                        keyboard.nextLine();
                        out.println("Codi no valid. Aqui tens els codis que ho son:");
                        printCodis();
                }
                out.println();
                out.println("Introdueix un codi: ");
            }
        }catch(InputMismatchException ime){
            out.println("Codi no valid. Aqui tens els codis que ho son:");
            printCodis();
            keyboard.nextLine();
            executar();
        }
    }

    private static void printMenu() {
        out.println("Benvingut al menu principal dels drivers.");
        out.println("Selecciona el driver que vols utilitzar introduint el seu codi associat:");
        printCodis();
        out.println("Els drivers estan ordenats segons l'us que fan les classes de les altres, de forma que una classe pot utilitzar una altre amb codi inferior, pero no amb codi superior.");
        out.println("Per exemple, la classe Aula conte un atribut Tipus_Aula, pero no conte cap de les altres classes, mentre que Pla d'Estudis conte, entre altres, Horari i Assignatura, pero ninguna classe de dades el conte a ell.");
        out.println("L'unica excepcio son les classes Grup i Restriccio_Subgrup, que es contenen la una a la altra, de manera que en el driver de Grup s'utilitza un Stub de Restriccio_Subgrup.");
        out.println("Recorda que les execucions dels drivers son independents. Es a dir, qualsevol cosa que facis en un driver no es conservara quan hi surtis.");
        out.println("Si vols tronar a consultar els codis, pots introduir el codi -1 (tambe valid en tots els drivers).");
    }

    private static void printCodis(){
        out.println("\t1) Tipus Aula");
        out.println("\t2) Aula");
        out.println("\t3) Classe");
        out.println("\t4) Grup");
        out.println("\t5) Restriccio Ocupacio");
        out.println("\t6) Restriccio Subgrup");
        out.println("\t7) Corequisit");
        out.println("\t8) Assignacio");
        out.println("\t9) Assignatura");
        out.println("\t10) Horari");
        out.println("\t11) Pla d'Estudis");
        out.println("\t12) Sortir");
    }
}
