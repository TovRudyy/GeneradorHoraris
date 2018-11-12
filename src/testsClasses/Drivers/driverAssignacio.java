package testsClasses.Drivers;

import domain.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;

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
        out.println("Tot i que son funcions separades, et recomanem que just despres de crear una nova assignacio, fagis el setSubgrup(si es un subgrup) i setCorequisit(si te corequisits).");
    }

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Getters");
        out.println("\t3) Setters");
        out.println("\t4) Generador de Classes");
        out.println("\t5) Afegir possible Classe");
        out.println("\t6) Forward Checking");
        out.println("\t7) Eliminar classes no seleccionades");
        out.println("\t8) Seleccionar classe");
        out.println("\t9) Deseleccionar classe");
        out.println("\t10) Obtenir classes seleccionades");
        out.println("\t11) Consultora combinacio possible");
        out.println("\t12) To String");
        out.println("\t13) Sortir");

    }

    private static void executar() {

    }

    private static void constructor_Test() {
        out.println("Introdueix els seguents atributs, separats per un espai:");
        out.println("\tId_Grup<String> Capacitat<int> Tipus<Tipus_Aula> Id_Assignatura<String> nivellAssignatura<int> nombreClasses<int> duracioClasses<int> horariGrup<M | T>");
        try{
            ass = new assignacio(keyboard.next(), keyboard.nextInt(), Tipus_Aula.string_to_Tipus_Aula(keyboard.next()), keyboard.next(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.next());
        }catch(IllegalArgumentException iae){
            out.println("Has introduit algun dels atributs incorrectament.");
        }catch(Aula_Exception ae){
            out.println(ae.getMessage());
        }
    }

    private static void getters_Test() {
        out.println("Tria el getter que vols provar introduint el seu codi associat: ");
        out.println("\t1) Assignatura");
        out.println("\t2) Grup");
        out.println("\t3) Classes possibles");
        out.println("\t4) Nombre de Classes restants");
        out.println("\t5) Tots");
        switch (keyboard.nextInt()){
            case 1:
                out.println("Identificador assignatura: " + ass.getIdAssig());
                break;
            case 2:
                out.println("Identificador grup: " + ass.getIdGrup());
                break;
            case 3:
                out.println("Classes posibles: ");
                for(Classe c: ass.getAllPossibleClasses()) out.println("\t" + c.toString());
                break;
            case 4:
                out.println("Classes restants: " + ass.getNumeroClassesRestants());
                break;
            case 5:
                out.println("Identificador assignatura: " + ass.getIdAssig());
                out.println("Identificador grup: " + ass.getIdGrup());
                out.println("Classes posibles: ");
                for(Classe c: ass.getAllPossibleClasses()) out.println("\t" + c.toString());
                out.println("Classes restants: " + ass.getNumeroClassesRestants());
                break;
            default:
                keyboard.nextLine();
                out.println("Codi d'opcio no valid.");
        }
    }

    private static void setters_Test() {
        out.println("Tria el setter que vols provar introduint el seu codi associat: ");
        out.println("\t1) RestriccioCorequisit");
        out.println("\t2) RestriccioSubgrup");
        switch (keyboard.nextInt()){
            case 1:
                RestriccioCorequisit corequisit = new RestriccioCorequisit();
                out.println("Introdueix totes les Assignatures(nomes identificadors) que son correquisits de la Assignatura d'aquesta assignacio separats per un espai:");
                while(keyboard.hasNext()) corequisit.addAssignatura(keyboard.next());
                ass.afegirCorrequisit(corequisit);
                out.println("S'ha afegit el seguent corequisit: " + corequisit.toString());
                break;
            case 2:
                out.println("Introdueix les dades del grup del qual el grup de la assignacio es subgrup: ");
                out.println("\tIdentificador<String> Capacitat<int> Horari<M | T> Tipus<Tipus_Aula>");
                try {
                    ass.afegirSubgrup(new RestriccioSubgrup(new grup(keyboard.next(), keyboard.nextInt(), keyboard.next(), Tipus_Aula.string_to_Tipus_Aula(keyboard.next()))));
                }catch(Aula_Exception ae){
                    out.println(ae.getMessage());
                }catch (InputMismatchException ime){
                    out.println("Has introduit algun atribut incorrectament");
                }
                break;
            default:
                keyboard.nextLine();
                out.println("Codi d'opcio no valid.");
        }
    }

    private static void genera_Possibles_Classes_Test() {
        out.println("Aquestes son les classes que s'han generat: ");
        for(Map<DiaSetmana, ArrayList<Classe>> m: ass.generaPossiblesClasses().values()){
            for(ArrayList<Classe> a: m.values()){
                for(Classe c: a){
                    out.println("\t" + c.toString());
                }
            }
        }
    }

    private static void afegeix_possibilitat_Test() {

    }

    private static void forward_Checking_Test() {

    }

    private static void nomes_Seleccionades_Test() {

    }

    private static void afegir_Seleccionada_Test() {

    }

    private static void eliminar_Seleccionada_Test() {

    }

    private static void get_Seleccionades_Test() {

    }

    private static void is_Empty_Test() {

    }

    private static void to_String_Test() {

    }
}
