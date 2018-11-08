package testsClasses;

import domain.assignatura;
import domain.grup;
import testsClasses.Stubs.GrupStub;

import java.util.Scanner;
import java.util.TreeMap;

public class driverAssignatura {

    private static Scanner keyboard = new Scanner(System.in);
    private static assignatura ass;

    public static void main(String[] args) {
        ass = new assignatura(null, null, 0);

        System.out.println("Driver assignatura:");
        System.out.println("Codis:");
        System.out.println("\t1) Constructora");
        System.out.println("\t2) Getter Id");
        System.out.println("\t3) Getter Nom");
        System.out.println("\t4) Getter Nivell");
        System.out.println("\t5) Setter Grups");
        System.out.println("\t6) Setter Classes");
        System.out.println("\t7) Setter Correquisit");
        System.out.println("\t8) Getter Classes");
        System.out.println("\t9) Getter Grups");
        System.out.println("\t10) Getter Assignacions");
        System.out.println("\t11) Funcio anti-solapament");
        System.out.println("\t12) Sortir");

        System.out.println("Introdueix un codi:");
        int codi = keyboard.nextInt();
        while(codi != 12){
            switch(codi){
                case 1:
                    assignaturaTest();
                    break;
                case 2:
                    getIdTest();
                    break;
                case 3:
                    getNomTest();
                    break;
                case 4:
                    getNivellTest();
                    break;
                case 5:
                    creaGrupsTest();
                    break;
                case 6:
                    setClassesTest();
                    break;
                case 7:
                    showClassesTest();
                    break;
                case 8:
                    showGrupsTest();
                    break;
                case 9:
                    noSolapis_Teoria_i_ProblemesTest();
                    break;
                case 10:
                    getAssignacionsTest();
                    break;
                case 11:
                    addCorrequisitTest();
                    break;
                default:
                    System.out.println("Codi no vàlid");
            }
            System.out.println("Introdueix un codi:");
            codi = keyboard.nextInt();
        }



    }

    private static void assignaturaTest(){
        System.out.println("Test Constructora:");
        System.out.println("Introdueix: id<String> nom<String> nivell<int>");
        ass = new assignatura(keyboard.next(), keyboard.next(), keyboard.nextInt());
        System.out.println("Creada la seguent assignatura: " + ass.toString());
    }

    private static void getIdTest(){
        System.out.println("Test Getter Id:");
        System.out.println("Id: " + ass.getId());
    }

    private static void getNomTest(){
        System.out.println("Test Getter Nom:");
        System.out.println("Id: " + ass.getNom());
    }

    private static void getNivellTest(){
        System.out.println("Test Getter Nivell:");
        System.out.println("Id: " + ass.getNivell());
    }

    private static void creaGrupsTest(){
        System.out.println("Test Setter Grups:");
        TreeMap<String, grup> grups = new TreeMap<String, grup>();
        grups.put("0",new GrupStub());
        ass.creaGrups(grups);
        System.out.println("Afegit un Stub de Grup");
    }

    private static void setClassesTest(){
        System.out.println("Test Setter Classes:");
        System.out.println("Introdueix: num_Teo duracio_Teo num_Prob duracio_Prob numLab duracio_Lab <ints>:");
        ass.setClasses(keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt());
        System.out.println("Tot correcte");
    }

    private static void showClassesTest(){
        System.out.println("Test Getter Classes");
        ass.showClasses();

    }

    private static void showGrupsTest(){
        System.out.println("Test Getter Grups");
        ass.showGrups();
    }

    private static void noSolapis_Teoria_i_ProblemesTest(){

    }

    private static void getAssignacionsTest(){

    }

    private static void addCorrequisitTest(){

    }
}
