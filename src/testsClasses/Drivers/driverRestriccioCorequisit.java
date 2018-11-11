package testsClasses.Drivers;

import domain.RestriccioCorequisit;

import java.util.InputMismatchException;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;


class driverRestriccioCorequisit {

    private static RestriccioCorequisit corequisit = new RestriccioCorequisit();

    static void main(){
        printMenu();
        executar();
    }

    private static void printMenu() {
        out.println("Driver de la Classe RestriccioCorequisit.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat: ");
        printCodis();
        out.println("Aquest driver mante una unica instancia de RestriccioCorequisit, que va sobreescribint cada cop que proves la constructora");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
        out.println("La Classe RestriccioCorequisit mante una llista d'assignatures que son corequisit de l'assignatura que conte la instancia de RestriccioCorequisit.");
    }

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Consultora llistat buit");
        out.println("\t3) Getter assignatures");
        out.println("\t4) Afegir assignatura");
        out.println("\t5) Consultora corequisit");
        out.println("\t6) Comprovacio restriccio");
        out.println("\t7) Esborrar classes incompatibles");
        out.println("\t8) To String");
        out.println("\t9) Sortir");
    }

    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while((codi = keyboard.nextInt()) != 9){
                switch (codi) {
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        constructor_Test();
                        break;
                    case 2:
                        is_Empty_Test();
                        break;
                    case 3:
                        get_Assignatures_Test();
                        break;
                    case 4:
                        add_Assignatura_Test();
                        break;
                    case 5:
                        es_Corequisit_Test();
                        break;
                    case 6:

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

    private static void es_Corequisit_Test() {

    }

    private static void add_Assignatura_Test() {

    }

    private static void get_Assignatures_Test() {

    }

    private static void is_Empty_Test() {
    }

    private static void constructor_Test() {
    }
}