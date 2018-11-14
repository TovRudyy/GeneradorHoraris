package testsClasses.Drivers;

import domain.Horari;
import domain.assignacio;

import static testsClasses.Drivers.MainDriver.keyboard;
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.InputMismatchException;

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
        out.println("\t7) Sortir");
    }

    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while((codi = keyboard.nextInt()) != 7){
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

    private static void to_String_Test() {
        out.println(horari.toString());
    }

    private static void write_Horari_Aules_Test() {
        horari.printHorariAules();
    }

    private static void write_Horari_Assignatures_Test() {
        horari.printHorariAssignatures();
    }

    private static void write_Horari_Test() {
        horari.printHorari();
    }

    private static void make_Horari_Test() {
        horari.findHorari();
    }

    private static void constructor_Test() {
        try {
            ArrayList<assignacio> ass = Lector_Drivers_JSON.llegitFitxer_Horari_InputList();
            for(assignacio a : ass) out.println(a.toString());
            horari = new Horari(ass);
        }catch(Exception e){
            out.println("Hi ha algun problema amb l'arxiu.");
            out.println("Missatge: " + e.getMessage());
        }
    }
}
