package testsClasses.Drivers;

import domain.PlaEstudis;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;

class driverPlaEstudis {

    private static PlaEstudis pla = new PlaEstudis("Pla_Test");

    static void main() {
        printMenu();
        executar();
    }

    private static void printMenu() {
        out.println("Driver de la Classe Pla d'Estudis");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat: ");
        printCodis();


    }

    private static void printCodis() {

    }

    private static void executar() {

    }

}
