package testsClasses.Drivers;

import domain.Aula_Exception;
import domain.Tipus_Aula;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;
import static testsClasses.Drivers.MainDriver.output;

/**
 * @author Victor
 */

class driverTipus_Aula {



    static void main() {
        printMenu();
        executar();
    }

    /**
     * Imprimeix un menu amb totes les funcionalitat accessibles per a l'usuari.
     */
    private static void printMenu(){
        out.println("Driver de la Enumeracio Tipus_Aula.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat:");
        printCodis();
        out.println("La primera opcio (Escriure tipus) no es una funcio en si, sino que esta per a que puguis veure tots els tipus d'aula que hi ha actualment.");
    }

    /**
     * Dona els codis necessaris per a accedir a les diferents funcionalitats.
     */
    private static void printCodis(){
        out.println("\t1) Escriure tipus");
        out.println("\t2) Escriure codis valids");
        out.println("\t3) Conversor String -> Tipus_Aula");
        out.println("\t4) Consultora laboratori");
        out.println("\t5) Obtenir abreviacio");
        out.println("\t6) Executar Joc de Proves");
        out.println("\t7) Sortir");
    }

    /**
     * Executa la funcio triada per l'usuari.
     */
    private static void executar(){
        try {
            int codi;
            out.println("Introdueix un codi: ");
            while ((codi = keyboard.nextInt()) != 7) {
                switch (codi) {
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        for (Tipus_Aula tp : Tipus_Aula.values()) output.println("\t" + tp);
                        break;
                    case 2:
                        Tipus_Aula.escriure_codis_valids();
                        break;
                    case 3:
                        String_To_Tipus_Aula_Test();
                        break;
                    case 4:
                        es_Laboratori_Test();
                        break;
                    case 5:
                        get_Short_Test();
                        break;
                    case 6:
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

    /**
     * Executa el joc de proves associat a aquest driver.
     */
    private static void executar_Joc_de_Proves() {
        try{
            keyboard = new Scanner(new FileReader("data/Jocs_de_Prova_Drivers/1.DriverTipus_Aula"));
            output = new PrintStream(new File("data/Jocs_de_Prova_Drivers/Sortida_Jocs"));
        }catch(FileNotFoundException fnfe){
            out.println(fnfe.getMessage());
        }
    }

    /**
     * Construeix un test.
     */
    private static void get_Short_Test() {
        out.println("Tria la opcio que vols introduint el seu codi associat: ");
        out.println("\t1) Introduir un unic string, que sera convertit a Tipus_Aula, i obtenir la seva abreviacio");
        out.println("\t2) Fer que el driver imprimeixi, per cada valor possible de Tipus_Aula, la seva abreviacio");
        switch (keyboard.nextInt()) {
            case 1:
                out.println("Introdueix un string: ");
                try {
                    Tipus_Aula tp = Tipus_Aula.string_to_Tipus_Aula(keyboard.next());
                    output.println(tp + " : " + Tipus_Aula.getShort(tp));
                } catch (Aula_Exception ae) {
                    output.println("El string que has introduit no es correspon a cap Tipus_Aula i, per tant, s'ha llencat la Excepcio Aula_Exception.");
                    output.println("Missatge d'Aula_Exception: " + ae.getMessage());
                }
                break;
            case 2:
                for (Tipus_Aula tp : Tipus_Aula.values())
                    output.println(tp + " : " + Tipus_Aula.getShort(tp));
                break;
            default:
                out.println("Codi d'opcio no valid.");
        }
    }

    /**
     * Comprova si donat un test, aquest es o no un laboratori.
     */
    private static void es_Laboratori_Test() {
        out.println("Tria la opcio que vols introduint el seu codi associat: ");
        out.println("\t1) Introduir un unic string, que sera convertit a Tipus_Aula, i consultar si es Laboratori o no");
        out.println("\t2) Fer que el driver imprimeixi, per cada valor possible de Tipus_Aula, el resultat de consultar si es Laboratori o no");
        switch (keyboard.nextInt()) {
            case 1:
                out.println("Introdueix un string: ");
                try {
                    Tipus_Aula tp = Tipus_Aula.string_to_Tipus_Aula(keyboard.next());
                    output.println(tp + " : " + Tipus_Aula.es_Laboratori(tp));
                } catch (Aula_Exception ae) {
                    output.println("El string que has introduit no es correspon a cap Tipus_Aula i, per tant, s'ha llencat la Excepcio Aula_Exception.");
                    output.println("Missatge d'Aula_Exception: " + ae.getMessage());
                }
                break;
            case 2:
                for (Tipus_Aula tp : Tipus_Aula.values())
                    output.println(tp + " : " + Tipus_Aula.es_Laboratori(tp));
                break;
            default:
                out.println("Codi d'opcio no valid.");
        }
    }

    /**
     * Imprimeix una string amb la informacio guardada en el test.
     */
    private static void String_To_Tipus_Aula_Test() {
        out.println("Introdueix un string: ");
        String s = keyboard.next();
        try {
            output.println(s + " -> " + Tipus_Aula.string_to_Tipus_Aula(s));
        } catch (Aula_Exception ae) {
            output.println("El string que has introduit no es correspon a cap Tipus_Aula i, per tant, s'ha llencat la Excepcio Aula_Exception.");
            output.println("Missatge d'Aula_Exception: " + ae.getMessage());
        }
    }
}
