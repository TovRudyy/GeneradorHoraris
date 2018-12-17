package testsClasses.Drivers;

import domain.Classe;
import domain.DiaSetmana;

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

class driverClasse  {

    private static Classe classe = new Classe("Assignatura_Inicial", "Grup_Inicial", DiaSetmana.DILLUNS, 0, 1, "Aula_Inicial");

    static void main() {
        printMenu();
        executar();
    }

    /**
     * Imprimeix un menu amb les opcions disponibles per a l'usuari.
     */
    private static void printMenu() {
        out.println("Driver de la Classe Classe.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat: ");
        printCodis();
        out.println("Aquest driver mante una unica instancia de Classe, que va sobreescribint cada cop que proves la constructora.");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
        out.println("Com pots veure, no hi ha setters. Aixo es perque Classe es una classe de dades interna, que no necessita ser modificada mai.");
    }

    /**
     * Imprimeix els codis per a accedir a les diferents funcionalitats.
     */
    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Getters");
        out.println("\t3) To String");
        out.println("\t4) Executar Joc de Proves");
        out.println("\t5) Sortir");
    }

    /**
     * Executa la opcio que hagi elegit el usuari.
     */
    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while((codi = keyboard.nextInt()) != 5){
                switch (codi){
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        constructor_test();
                        break;
                    case 2:
                        getter_Test();
                        break;
                    case 3:
                        output.println("Classe en format String: " + classe.toString());
                        break;
                    case 4:
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
     * Executa el joc de proves associat al driver.
     */
    private static void executar_Joc_de_Proves() {
        try{
            keyboard = new Scanner(new FileReader("data/Jocs_de_Prova_Drivers/3.DriverClasse"));
            output = new PrintStream(new File("data/Jocs_de_Prova_Drivers/Sortida_Jocs"));
        }catch(FileNotFoundException fnfe){
            out.println(fnfe.getMessage());
        }
    }

    /**
     * Imprimeix un atribut triat per l'usuari del test.
     */
    private static void getter_Test() {
        out.println("Tria el getter que vols provar introduint el seu codi associat: ");
        out.println("\t1) Assignatura");
        out.println("\t2) Grup");
        out.println("\t3) Aula");
        out.println("\t4) Dia");
        out.println("\t5) Hora inicial");
        out.println("\t6) Hora final");
        out.println("\t7) Durada");
        out.println("\t8) Tots");
        switch (keyboard.nextInt()){
            case 1:
                output.println("Identificador assignatura: " + classe.getId_assig());
                break;
            case 2:
                output.println("Identificador grup: " + classe.getId_grup());
                break;
            case 3:
                output.println("Identificador aula: " + classe.getIdAula());
                break;
            case 4:
                output.println("Dia: " + classe.getDia());
                break;
            case 5:
                output.println("Hora inicial: " + classe.getHoraInici());
                break;
            case 6:
                output.println("Hora final: " + classe.getHoraFi());
                break;
            case 7:
                output.println("Durada: " + classe.getDurada());
                break;
            case 8:
                output.println("Identificador assignatura: " + classe.getId_assig());
                output.println("Identificador grup: " + classe.getId_grup());
                output.println("Identificador aula: " + classe.getIdAula());
                output.println("Dia: " + classe.getDia());
                output.println("Hora inicial: " + classe.getHoraInici());
                output.println("Hora final: " + classe.getHoraFi());
                output.println("Durada: " + classe.getDurada());
                break;
            default:
                keyboard.nextLine();
                out.println("Codi d'opcio no valid.");
        }
    }

    /**
     * Construeix un test de forma dinamica.
     */
    private static void constructor_test() {
        out.println("Introdueix els seguent atributs, separats per un espai:");
        out.println("\tId_Assignatura<String> Id_Grup<String> Id_Aula<String>, Dia<DiaSetmana> HoraInicial<int> HoraFinal<int>");
        try{
            String as = keyboard.next(), g = keyboard.next(), au = keyboard.next(), dia = keyboard.next();
            int ini = keyboard.nextInt(), fin = keyboard.nextInt();
            if(ini >= fin){
                output.println("L'hora final ha de ser posterior a la inicial.");
                return;
            }
            classe = new Classe(as, g, DiaSetmana.string_To_DiaSetmana(dia), ini, fin, au);
        }catch(IllegalArgumentException | InputMismatchException iae){
            output.println("Has introduit algun dels atributs incorrectament.");
            keyboard.nextLine();
        }
    }
}