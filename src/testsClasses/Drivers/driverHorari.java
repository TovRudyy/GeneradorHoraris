package testsClasses.Drivers;

import domain.Horari;
import domain.assignacio;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class driverHorari {

    private static Horari horari = new Horari(new ArrayList<>());

    static void main() {
        printMenu();
        executar();
    }

    private static void printMenu() {
        out.println("Driver de la Classe Horari.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat: ");
        printCodis();
        out.println("Aquesta classe genera horaris a partir d'un conjunt d'asignacions.");
        out.println("Com que una assignacio te molts atributs i elements que depenent de factors superiors(d'assignatures, aules, etc), aquest driver llegeix un conjunt d'aules, assignatures i restriccions d'u fitxer JSON i et genera les assignacions automaticament.");
        out.println("Aquests esta situat a la carpeta \"Data\", subcarpeta \"Drivers_Input\", i es diu Horari_InputList.json.");
    }

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Crear Horari");
        out.println("\t3) Escriure Horari");
        out.println("\t4) Escriure Horari per Aula");
        out.println("\t5) Escriure Horari per Assignatura");
        out.println("\t6) To String");
        out.println("\t7) Executar Joc de Proves");
        out.println("\t8) Sortir");
    }

    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while((codi = keyboard.nextInt()) != 8){
                switch (codi){
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        constructor_Test();
                        break;
                    case 2:
                        make_Horari_Test();
                        break;
                    case 3:
                        write_Horari_Test();
                        break;
                    case 4:
                        write_Horari_Aules_Test();
                        break;
                    case 5:
                        write_Horari_Assignatures_Test();
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
        }catch(InputMismatchException ime){
            out.println("Codi no valid. Aqui tens els codis que ho son: ");
            printCodis();
            keyboard.nextLine();
            executar();
        }
    }

    private static void executar_Joc_de_Proves() {
        try{
            keyboard = new Scanner(new FileReader("data/Jocs_de_Prova_Drivers/10.DriverHorari"));
            output = new PrintStream(new File("data/Jocs_de_Prova_Drivers/Sortida_Jocs"));
        }catch(FileNotFoundException fnfe){
            out.println(fnfe.getMessage());
        }
    }

    private static void to_String_Test() {
        output.println(horari.toString());
    }

    private static void write_Horari_Aules_Test() {
        captura();
        horari.printHorariAules();
        allibera();
    }

    private static void write_Horari_Assignatures_Test() {
        captura();
        horari.printHorariAssignatures();
        allibera();
    }

    private static void write_Horari_Test() {
        captura();
        horari.printHorari();
        allibera();
    }

    private static void make_Horari_Test() {
        captura();
        horari.findHorari();
        allibera();
    }

    private static void constructor_Test() {
        try {
            ArrayList<assignacio> ass = Lector_Drivers_JSON.llegitFitxer_Horari_InputList();
            for(assignacio a : ass) out.println(a.toString());
            horari = new Horari(ass);
        }catch(Exception e){
            output.println("Hi ha algun problema amb l'arxiu.");
            output.println("Missatge: " + e.getMessage());
        }
    }
}
