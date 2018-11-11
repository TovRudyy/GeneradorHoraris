package testsClasses.Drivers;

import domain.Tipus_Aula;
import domain.assignacio;

import static java.lang.System.out;

class driverAssignacio {

    private static assignacio ass = new assignacio("10", 60, Tipus_Aula.TEORIA, "FM", 1, 2, 2, "M");

    static void main() {
        printMenu();
        executar();
    }

    private static void printMenu() {
        out.println("Driver de la Classe Assignacio.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat:");
        printCodis();
        out.println("Aquest driver mante una unica instancia de Aula, que va sobreescribint cada cop que proves la constructora.");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
    }

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Getters");
        out.println("\t3) Setters");
        out.println("\t4) Generador de Classes");
        out.println("\t5) Afegir possible Classe");

    }

    private static void executar() {

    }
}
