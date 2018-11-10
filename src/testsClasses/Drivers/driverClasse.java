package testsClasses.Drivers;

import domain.Classe;
import domain.DiaSetmana;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.InputMismatchException;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;


class driverClasse  {

    private static Classe classe = new Classe("Assig_Inicial", "Grup_Inicial", DiaSetmana.DILLUNS, 0, 1, "Aula_Inicial");

    static void main() {
        printMenu();
        executar();
    }

    private static void printMenu() {
        out.println("Driver de la Classe Classe.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat: ");
        printCodis();
        out.println("Aquest driver mante una unica instancia de Classe, que va sobreescribint cada cop que proves la constructora.");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
        out.println("Com pots veure, no hi ha setters. Aixo es perque Classe es una classe de dades interna, que no necessita ser modificada mai.");
    }

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Getters");
        out.println("\t3) To String");
        out.println("\t4) Sortir");
    }

    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while((codi = keyboard.nextInt()) != 4){
                switch (codi){
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        out.println("Introdueix els seguent atributs, separats per un espai:");
                        out.println("\tId_Assignatura<String> Id_Grup<String> Id_Aula<String>, Dia<DiaSetmana> HoraInicia<int> HoraFinal<int>");
                        try{
                            String as = keyboard.next(), g = keyboard.next(), dia = keyboard.next();
                            int ini = keyboard.nextInt(), fin = keyboard.nextInt();
                            if(ini > fin){
                                out.println("L'hora final ha de ser posterior a la inicial.");
                                break;
                            }
                            classe = new Classe(as, g, DiaSetmana.string_To_DiaSetmana(dia), ini, fin, keyboard.next());
                        }catch(IllegalArgumentException iae){
                            out.println("Has introduit algun dels atributs incorrectament.");
                        }
                        break;
                    case 2:
                        out.println("Tria el getter que vols provar introduint el seu codi associat: ");
                        out.println("\t1) Assignatura");
                        out.println("\t2) Grup");
                        out.println("\t3) Aula");
                        out.println("\t4) Dia");
                        out.println("\t5) Hora inicial");
                        out.println("\t6) Hora final");
                        out.println("\t7) Durada");
                        out.println("\t8) Tots");
                        switch (keyboard.nextInt()){
                            case 1:
                                out.println("Identificador assignatura: " + classe.getId_assig());
                                break;
                            case 2:
                                out.println("Identificador grup: " + classe.getId_grup());
                                break;
                            case 3:
                                out.println("Identificador aula: " + classe.getIdAula());
                                break;
                            case 4:
                                out.println("Dia: " + classe.getDia());
                                break;
                            case 5:
                                out.println("Hora inicial: " + classe.getHoraInici());
                                break;
                            case 6:
                                out.println("Hora final: " + classe.getHoraFi());
                                break;
                            case 7:
                                out.println("Durada: " + classe.getDurada());
                                break;
                            case 8:
                                out.println("Identificador assignatura: " + classe.getId_assig());
                                out.println("Identificador grup: " + classe.getId_grup());
                                out.println("Identificador aula: " + classe.getIdAula());
                                out.println("Dia: " + classe.getDia());
                                out.println("Hora inicial: " + classe.getHoraInici());
                                out.println("Hora final: " + classe.getHoraFi());
                                out.println("Durada: " + classe.getDurada());
                                break;
                            default:
                                out.println("Codi d'opcio no valid.");
                        }
                        break;
                    case 3:
                        out.println("Classe en format String: " + classe.toString());
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
}