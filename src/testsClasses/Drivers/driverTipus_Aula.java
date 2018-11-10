package testsClasses.Drivers;

import domain.Aula_Exception;
import domain.Tipus_Aula;

import java.util.InputMismatchException;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;

class driverTipus_Aula {
    static void main() {
        printMenu();
        executar();
    }

    private static void printMenu(){
        out.println("Driver de la Enumeracio Tipus_Aula.");
        out.println("Selecciona la funcio que vols probar introduint el seu codi associat:");
        printCodis();
        out.println("La primera opcio (Escriure tipus) no es una funcio en si, sino que esta per a que puguis veure tots els tipus d'aula que hi ha actualment.");
    }

    private static void printCodis(){
        out.println("\t1) Escriure tipus");
        out.println("\t2) Escriure codis valids");
        out.println("\t3) Conversor String -> Tipus_Aula");
        out.println("\t4) Consultora laboratori");
        out.println("\t5) Obtenir abreviacio");
        out.println("\t6) Sortir");
    }

    private static void executar(){
        try {
            int codi;
            out.println("Introdueix un codi: ");
            while ((codi = keyboard.nextInt()) != 6) {
                switch (codi) {
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        for (Tipus_Aula tp : Tipus_Aula.values()) out.println(tp);
                        break;
                    case 2:
                        Tipus_Aula.escriure_codis_valids();
                        break;
                    case 3:
                        out.println("Introdueix un string: ");
                        String s = keyboard.next();
                        try {
                            out.println(s + " -> " + Tipus_Aula.string_to_Tipus_Aula(s));
                        } catch (Aula_Exception ae) {
                            out.println("El string que has introduit no es correspon a cap Tipus_Aula i, per tant, s'ha llencat la Excepcio Aula_Exception.");
                            out.println("Missatge d'Aula_Exception: " + ae.getMessage());
                        }
                        break;
                    case 4:
                        out.println("Tria la opcio que vols introduint el seu codi associat: ");
                        out.println("\t1) Introduir un unic string, que sera convertit a Tipus_Aula, i consultar si es Laboratori o no");
                        out.println("\t2) Fer que el driver imprimeixi, per cada valor possible de Tipus_Aula, el resultat de consultar si es Laboratori o no");
                        switch (keyboard.nextInt()) {
                            case 1:
                                out.println("Introdueix un string: ");
                                try {
                                    Tipus_Aula tp = Tipus_Aula.string_to_Tipus_Aula(keyboard.next());
                                    out.println(tp + " : " + Tipus_Aula.es_Laboratori(tp));
                                } catch (Aula_Exception ae) {
                                    out.println("El string que has introduit no es correspon a cap Tipus_Aula i, per tant, s'ha llencat la Excepcio Aula_Exception.");
                                    out.println("Missatge d'Aula_Exception: " + ae.getMessage());
                                }
                                break;
                            case 2:
                                for (Tipus_Aula tp : Tipus_Aula.values())
                                    out.println(tp + " : " + Tipus_Aula.es_Laboratori(tp));
                                break;
                            default:
                                out.println("Codi d'opcio no valid.");
                        }
                        break;
                    case 5:
                        out.println("Tria la opcio que vols introduint el seu codi associat: ");
                        out.println("\t1) Introduir un unic string, que sera convertit a Tipus_Aula, i obtenir la seva abreviacio");
                        out.println("\t2) Fer que el driver imprimeixi, per cada valor possible de Tipus_Aula, la seva abreviacio");
                        switch (keyboard.nextInt()) {
                            case 1:
                                out.println("Introdueix un string: ");
                                try {
                                    Tipus_Aula tp = Tipus_Aula.string_to_Tipus_Aula(keyboard.next());
                                    out.println(tp + " : " + Tipus_Aula.getShort(tp));
                                } catch (Aula_Exception ae) {
                                    out.println("El string que has introduit no es correspon a cap Tipus_Aula i, per tant, s'ha llencat la Excepcio Aula_Exception.");
                                    out.println("Missatge d'Aula_Exception: " + ae.getMessage());
                                }
                                break;
                            case 2:
                                for (Tipus_Aula tp : Tipus_Aula.values())
                                    out.println(tp + " : " + Tipus_Aula.getShort(tp));
                                break;
                            default:
                                out.println("Codi d'opcio no valid.");
                        }
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
