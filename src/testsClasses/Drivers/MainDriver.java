package testsClasses.Drivers;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * @author Victor
 */

public class MainDriver {

    static Scanner keyboard = new Scanner(System.in);
    static PrintStream output = System.out;
    private static PrintStream old;

    public static void main(String[] args) {
        printMenu();
        executar();
    }

    /**
     * Permet a l'usuari introduir un codi i executar diverses funcionalitats corresponents.
     */
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
                        printMenu();
                        break;
                    case 4:
                        driverGrup.main();
                        printMenu();
                        break;
                    case 5:
                        driverRestriccioOcupacio.main();
                        printMenu();
                        break;
                    case 6:
                        driverRestriccioSubgrup.main();
                        printMenu();
                        break;
                    case 7:
                        driverRestriccioCorrequisit.main();
                        printMenu();
                        break;
                    case 8:
                        driverAssignacio.main();
                        printMenu();
                        break;
                    case 9:
                        driverAssignatura.main();
                        printMenu();
                        break;
                    case 10:
                        //driverHorari.main();
                        printMenu();
                        break;
                    case 11:
                        driverPlaEstudis.main();
                        printMenu();
                        break;
                    default:
                        keyboard.nextLine();
                        out.println("Codi no valid. Aqui tens els codis que ho son:");
                        printCodis();
                }
                keyboard = new Scanner(System.in);
                output = System.out;
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

    /**
     * Imprimeix un menu de benvinguda amb alguna informacio rellevant.
     */
    private static void printMenu() {
        out.println("Benvingut al menu principal dels drivers.");
        out.println("Selecciona el driver que vols utilitzar introduint el seu codi associat:");
        printCodis();
        out.println("Els drivers estan ordenats segons l'us que fan les classes de les altres, de forma que una classe pot utilitzar una altre amb codi inferior, pero no amb codi superior.");
        out.println("Per exemple, la classe Aula conte un atribut Tipus_Aula, pero no conte cap de les altres classes, mentre que Pla d'Estudis conte, entre altres, Horari i Assignatura, pero ninguna classe de dades el conte a ell.");
        out.println("L'unica excepcio son les classes Grup i Restriccio_Subgrup, que es contenen la una a la altra, de manera que en el driver de Grup s'utilitza un Stub de Restriccio_Subgrup.");
        out.println("La classe abstracta Restriccio (pare de Restriccio_Subgrup, Restriccio_Ocupacio i RestriccioCorequisit), es prova amb el driver Restriccio_Ocupacio, de manera que els drivers de Restriccio_Subgrup i RestriccioCorequisit nomes provaran els metodes que implementen o sobreescriuen.");
        out.println("Recorda que les execucions dels drivers son independents. Es a dir, qualsevol cosa que facis en un driver no es conservara quan hi surtis.");
        out.println("Si vols tronar a consultar els codis, pots introduir el codi -1 (tambe valid en tots els drivers).");
    }

    /**
     * Imprimeix els codis corresponents a les diferents funcionalitats.
     */
    private static void printCodis(){
        out.println("\t1) Tipus Aula");
        out.println("\t2) Aula");
        out.println("\t3) Classe");
        out.println("\t4) Grup");
        out.println("\t5) Restriccio Ocupacio");
        out.println("\t6) Restriccio Subgrup");
        out.println("\t7) Restriccio Corequisit");
        out.println("\t8) Assignacio");
        out.println("\t9) Assignatura");
        out.println("\t10) Horari");
        out.println("\t11) Pla d'Estudis");
        out.println("\t12) Sortir");
    }

    /**
     * Captura un horari creat.
     */
    static void captura(){
        old = System.out;
        System.setOut(output);
    }

    /**
     * Allibera el horari creat.
     */
    static void allibera(){
        System.out.flush();
        System.setOut(old);
    }
}
