package testsClasses.Drivers;

import domain.Horari;
import domain.assignacio;

import static testsClasses.Drivers.MainDriver.keyboard;
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.InputMismatchException;

class driverHorari {

    private static Horari horari = new Horari(new ArrayList<assignacio>());

    static void main() {
        printMenu();
        executar();
    }

    private static void printMenu() {
        out.println("Driver de la Classe Horari.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat: ");
        printCodis();
        out.println("Aquesta classe genera horaris a partir d'un conjunt d'asignacions.");
        out.println("Com que una assignacio te molts atributs i elements que depenent defactors superiors(d'assignatures, grups, etc), aquest driver llegeix un conjunt d'assignatures is restriccions d'u fitxer JSON i et genera les assignacions automaticament.");
        out.println("Aquests fitxer son 2, ambdos situats a la carpeta \"Data\", subcarpeta \"Drivers_Input\", i es diuen Horari_InputList (per la constructora) i Horari_AddList (per afegir assignacions).");
    }

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Afegir asignacions");
        out.println("\t3) Crear Horari");
        out.println("\t4) Escriure Horari");
        out.println("\t5) Sortir");
    }

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
                        constructor_Test();
                        break;
                    case 2:
                        add_Assignacions_Test();
                        break;
                    case 3:
                        make_Horari_Test();
                        break;
                    case 4:
                        write_Horari_Test();
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

    private static void write_Horari_Test() {

    }

    private static void make_Horari_Test() {

    }

    private static void add_Assignacions_Test() {

    }

    private static void constructor_Test() {
        horari = new Horari(Lector_Drivers_JSON.llegitFitxer_Horari_InputList());
    }
}
