package testsClasses.Drivers;

import java.util.Scanner;

public class MainDriver {
    public static void main(String[] args) {
        printMenu();
        executar();
    }

    private static void executar(){
        int codi;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Introdueix un codi: ");
        while((codi = keyboard.nextInt()) != 12){
            switch (codi) {
                case 1:
                    driverTipus_Aula.main();
                    break;
                case 2:
                    driverAula.main();
                    break;
                case 3:
                    driverClasse.main();
                    break;
                case 4:
                    driverGrup.main();
                    break;
                case 5:
                    driverRestriccioOcupacio.main();
                    break;
                case 6:
                    driverRestriccioSubgrup.main();
                    break;
                case 7:
                    driverControladorAules.main();
                    break;
                case 8:
                    driverAssignacio.main();
                    break;
                case 9:
                    driverAssignatura.main();
                    break;
                case 10:
                    driverHorari.main();
                    break;
                case 11:
                    driverPlaEstudis.main();
                    break;
                default:
                    System.out.println("Codi no valid.");
            }
            System.out.println();
            System.out.println("Introdueix un codi: ");
        }
    }

    private static void printMenu() {
        System.out.println("Benvingut al menu principal dels drivers.");
        System.out.println("Selecciona el driver que vols utilitzar introduint el seu codi associat:");
        printCodis();
        System.out.println("Els drivers estan ordenats segons l'us que fan les classes de les altres, de forma que una classe pot utilitzar una altre amb codi inferior, pero no amb codi superior.");
        System.out.println("Per exemple, la classe Aula conte un atribut Tipus_Aula, pero cap de les altres classes, mentre que Pla d'Estudis conte, entre altres, Horari i Assignatura, pero ninguna classe de dades el conte a ell.");
        System.out.println("L'unica excepcio son les classes Grup i Restriccio_Subgrup, que es contenen la una a la altra, de manera que en el driver de Grup s'utilitza un Stub de Restriccio_Subgrup.");
    }

    private static void printCodis(){
        System.out.println("\t1) Tipus Aula");
        System.out.println("\t2) Aula");
        System.out.println("\t3) Classe");
        System.out.println("\t4) Grup");
        System.out.println("\t5) Restriccio Ocupacio");
        System.out.println("\t6) Restriccio Subgrup");
        System.out.println("\t7) Corequisit");
        System.out.println("\t8) Assignacio");
        System.out.println("\t9) Assignatura");
        System.out.println("\t10) Horari");
        System.out.println("\t11) Pla d'Estudis");
        System.out.println("\t12) Sortir");
    }
}
