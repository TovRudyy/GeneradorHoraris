package testsClasses.Drivers;

import domain.assignacio;
import domain.assignatura;
import domain.grup;
import testsClasses.Stubs.GrupStub;

import java.util.Scanner;
import java.util.TreeMap;

public class driverAssignatura {

    private static Scanner keyboard = new Scanner(System.in);
    private static assignatura ass;

    static void main() {
        ass = new assignatura(null, null, 0);

        System.out.println("Driver assignatura:");
        printCodis();
        System.out.println("Introdueix un codi:");
        int codi = keyboard.nextInt();
        while(codi != 12){
            switch(codi){
                case -1:
                    printCodis();
                    break;
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
                    addCorrequisitTest();
                    break;
                case 8:
                    showClassesTest();
                    break;
                case 9:
                    showGrupsTest();
                    break;
                case 10:
                    getAssignacionsTest();
                    break;
                case 11:
                    noSolapis_Teoria_i_ProblemesTest();
                    break;
                default:
                    System.out.println("Codi no vàlid");
            }
            System.out.println("Introdueix un codi: (o -1 per imprimir la llista de codis)");
            codi = keyboard.nextInt();
        }



    }

    private static void printCodis() {
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
    }

    private static void assignaturaTest(){
        System.out.println("Test Constructora:");
        System.out.println("Introdueix: id<String> nom<String> nivell<int>");
        ass = new assignatura(keyboard.next(), keyboard.next(), keyboard.nextInt());
        System.out.println("\tCreada la seguent assignatura: " + ass.toString());
    }

    private static void getIdTest(){
        System.out.println("Test Getter Id:");
        System.out.println("\tId: " + ass.getId());
    }

    private static void getNomTest(){
        System.out.println("Test Getter Nom:");
        System.out.println("\tId: " + ass.getNom());
    }

    private static void getNivellTest(){
        System.out.println("Test Getter Nivell:");
        System.out.println("\tId: " + ass.getNivell());
    }

    private static void creaGrupsTest(){
        System.out.println("Test Setter Grups:");
        TreeMap<String, grup> grups = new TreeMap<String, grup>();
        grups.put("10",new GrupStub("10"));
        grups.put("11",new GrupStub("11"));
        grups.put("12",new GrupStub("12"));
        grups.put("20",new GrupStub("20"));
        grups.put("21",new GrupStub("21"));
        grups.put("22",new GrupStub("22"));

        ass.creaGrups(grups);
        System.out.println("\tAfegit un Stub de Grup");
    }

    private static void setClassesTest(){
        System.out.println("Test Setter Classes:");
        System.out.println("Introdueix: num_Teo duracio_Teo num_Prob duracio_Prob numLab duracio_Lab <ints>:");
        ass.setClasses(keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt());
        System.out.println("\tTot correcte");
    }

    private static void showClassesTest(){
        System.out.println("Test Getter Classes:");
        ass.showClasses();

    }

    private static void showGrupsTest(){
        System.out.println("Test Getter Grups:");
        ass.showGrups();
    }

    private static void noSolapis_Teoria_i_ProblemesTest(){
        System.out.println("Test Funcio anti-solapament:");
        ass.noSolapis_Teoria_i_Problemes();

    }

    private static void getAssignacionsTest(){
        System.out.println("Test Getter Assignacions:");
        for(assignacio as: ass.getAssignacions()){
            System.out.println(as.toString());
            as.printPossiblesClasses();
        }

    }

    private static void addCorrequisitTest(){
        ass.addCorrequisit("B");
    }
}
