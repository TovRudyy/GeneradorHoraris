package testsClasses.Drivers;

import domain.Aula;
import domain.Aula_Exception;
import domain.Tipus_Aula;

import java.util.InputMismatchException;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;


class driverAula  {

    private static Aula aula = new Aula("Aula_Inicial", 0, Tipus_Aula.TEORIA);

    static void main () {
        printMenu();
        executar();
    }

    private static void printMenu() {
        out.println("Driver de la Classe Aula.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat:");
        printCodis();
        out.println("Aquest driver mante una unica instancia de Aula, que va sobreescribint cada cop que proves la constructora.");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
        out.println("Com pots veure, no hi ha setters. Aixo es perque no permetem modificar les aules un cop creades.");
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
                switch (codi) {
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        out.println("Introdueix els seguents atributs, separats per un espai:");
                        out.println("\tIdentificador<String> Capacitat<int> Tipus_Aula<Tipus_Aula>");
                        try{
                            aula = new Aula(keyboard.next(), keyboard.nextInt(), Tipus_Aula.string_to_Tipus_Aula(keyboard.next()));
                        }catch(Aula_Exception ae){
                            out.println("Missatge d'Aula_Exception: " + ae.getMessage());
                        }catch(InputMismatchException ime){
                            out.println("Has introduit algun atribut incorrectament.");
                        }
                        break;
                    case 2:
                        out.println("Tria el getter que vols provar introduint el seu codi associat: ");
                        out.println("\t1) Identificador");
                        out.println("\t2) Capacitat");
                        out.println("\t3) Tipus");
                        out.println("\t4) Tots");
                        switch(keyboard.nextInt()){
                            case 1:
                                out.println("Identificador : " + aula.getId());
                                break;
                            case 2:
                                out.println("Capacitat : " + aula.getCapacitat());
                                break;
                            case 3:
                                out.println("Tipus_Aula : " + aula.getTipus());
                                break;
                            case 4:
                                out.println("Identificador : " + aula.getId());
                                out.println("Capacitat : " + aula.getCapacitat());
                                out.println("Tipus_Aula : " + aula.getTipus());
                                break;
                            default:
                                out.println("Codi d'opcio no valid.");
                        }
                        break;
                    case 3:
                        out.println("Aula en format String: " + aula.toString());
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
