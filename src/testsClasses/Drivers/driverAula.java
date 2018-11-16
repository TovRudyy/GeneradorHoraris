package testsClasses.Drivers;

import domain.Aula;
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


class driverAula  {

    private static Aula aula = new Aula("Aula_Inicial", 0, Tipus_Aula.TEORIA);

    static void main () {
        printMenu();
        executar();
    }

    private static void printMenu() {
        out.println("Driver de la Classe Aula.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat:");
        printCodis();
        out.println("Aquest driver mante una unica instancia de Aula, que va sobreescribint cada cop que proves la constructora.");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
        out.println("Com pots veure, no hi ha setters. Aixo es perque no permetem modificar les aules un cop creades.");
    }

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Getters");
        out.println("\t3) To String");
        out.println("\t4) Executar Joc de Proves");
        out.println("\t5) Sortir");
    }

    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while((codi = keyboard.nextInt()) != 5){
                switch (codi) {
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        constructor_Test();
                        break;
                    case 2:
                        getter_test();
                        break;
                    case 3:
                        output.println("Aula en format String: " + aula.toString());
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

    private static void executar_Joc_de_Proves() {
        try{
            keyboard = new Scanner(new FileReader("data/Jocs_de_Prova_Drivers/2.DriverAula"));
            output = new PrintStream(new File("data/Jocs_de_Prova_Drivers/Sortida_Jocs"));
        }catch(FileNotFoundException fnfe){
            out.println(fnfe.getMessage());
        }
    }

    private static void getter_test() {
        out.println("Tria el getter que vols provar introduint el seu codi associat: ");
        out.println("\t1) Identificador");
        out.println("\t2) Capacitat");
        out.println("\t3) Tipus");
        out.println("\t4) Tots");
        switch(keyboard.nextInt()){
            case 1:
                output.println("Identificador : " + aula.getId());
                break;
            case 2:
                output.println("Capacitat : " + aula.getCapacitat());
                break;
            case 3:
                output.println("Tipus_Aula : " + aula.getTipus());
                break;
            case 4:
                output.println("Identificador : " + aula.getId());
                output.println("Capacitat : " + aula.getCapacitat());
                output.println("Tipus_Aula : " + aula.getTipus());
                break;
            default:
                out.println("Codi d'opcio no valid.");
        }
    }

    private static void constructor_Test() {
        out.println("Introdueix els seguents atributs, separats per un espai:");
        out.println("\tIdentificador<String> Capacitat<int> Tipus_Aula<Tipus_Aula>");
        try{
            aula = new Aula(keyboard.next(), keyboard.nextInt(), Tipus_Aula.string_to_Tipus_Aula(keyboard.next()));
        }catch(Aula_Exception ae){
            output.println(ae.getMessage());
        }catch(InputMismatchException ime){
            output.println("Has introduit algun atribut incorrectament.");
            keyboard.nextLine();
        }
    }


}
