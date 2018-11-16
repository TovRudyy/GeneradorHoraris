package testsClasses.Drivers;

import domain.Classe;
import domain.DiaSetmana;
import domain.RestriccioOcupacio;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;
import static testsClasses.Drivers.MainDriver.output;

/**
 * @author Victor
 */

class driverRestriccioOcupacio  {

    private static RestriccioOcupacio rest = new RestriccioOcupacio();

    static void main() {
        printMenu();
        executar();
    }

    /**
     * Imprimeix un menu amb totes les funcionalitat accessibles per a l'usuari.
     */
    private static void printMenu() {
        out.println("Driver de la Classe RestriccioOcupacio.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat: ");
        printCodis();
        out.println("Aquest driver mante una unica instancia de RestriccioOcupacio.");
        out.println("Aquesta instancia ja ve inicialitzada, ja que aquesta classe no te atributs i, per tant, nomes te la constructora per defecte.");
        out.println("Aquest driver tambe prova les funcions propies de la classe abstracte Restriccio");
    }

    /**
     * Dona els codis necessaris per a accedir a les diferents funcionalitats.
     */
    private static void printCodis() {
        out.println("\t1) Consultora de solapament d'hores");
        out.println("\t2) Esborrar classes incompatibles");
        out.println("\t3) Executar Joc de Proves");
        out.println("\t4) Sortir");
    }

    /**
     * Executa la funcio triada per l'usuari.
     */
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
                        no_Solapis_Test();
                        break;
                    case 2:
                        delete_Possibilities_Test();
                        break;
                    case 3:
                        executar_Joc_de_Proves();
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

    /**
     * Executa el joc de proves associat a aquest driver.
     */
    private static void executar_Joc_de_Proves() {
        try{
            keyboard = new Scanner(new FileReader("data/Jocs_de_Prova_Drivers/5.DriverRestriccioOcupacio"));
            output = new PrintStream(new File("data/Jocs_de_Prova_Drivers/Sortida_Jocs"));
        }catch(FileNotFoundException fnfe){
            out.println(fnfe.getMessage());
        }
    }

    /**
     * Poda les possibilitats que ja no puguin tenir lloc si hem elegit una Classe donada.
     */
    private static void delete_Possibilities_Test() {
        out.println("Aquesta funcio rep com a parametres una Classe, i un conjunt de Classes classificades segons la seva aula i el Dia de la setmana.");
        out.println("El resultat d'aquesta funcio es la llista de Classes que s'han eliminat del conjunt degut a que son incompatibles amb la Classe passada.");
        out.println("Dues classes son incompatibles si ocupen la mateixa aula en una mateixa hora.");
        out.println("Com que el conjunt de classes que rep aquesta funcio es bastant complex, s'ha d'introduir mitjancant un fitxer JSON.");
        out.println("Aquest fitxer es troba dins la carpeta \"Data\", subcarpeta \"Drivers_Input\", i el seu nom ha de ser \"RestriccioOcupacio_InputMap.json\".");
        out.println("Ja et proporcionem un fitxer creat amb dades de mostra fetes, que pots utilitzar perfectament.");
        out.println("Aquestes dades consisteixen en totes les Classes possibles de dues hores d'una assignatura amb 6 grups(10, 11, 12, 20, 21 i 22), repetit per cada dia i en dues Aules diferents(Aula1 i Aula2).");
        out.println("Quan tinguis el fitxer llest per ser llegit, escriu \"C\" i apreta \"Enter\": ");
        if(!keyboard.next().equals("C")) return;
        Map<String, Map<DiaSetmana, ArrayList<Classe>>> classes;
        try {
            classes = Lector_Drivers_JSON.llegirFitxer_RestriccioOcupacio_InputMap();
        }catch(FileNotFoundException fnfe){
            output.println("El fitxer no s'ha trobat.");
            return;
        }catch(ParseException pe){
            output.println("El fitxer no te un format correcte.");
            return;
        }catch(IOException ioe){
            output.println("Hi ha hagut algun problema amb el fitxer:");
            output.println(ioe.getMessage());
            return;
        }
        out.println("Fitxer llegit. Ara introdueix la Classe que vols utilitzar per eliminar les incompatibles: ");
        out.println("\tId_Assignatura<String> Id_Grup<String> Id_Aula<String> Dia<DiaSetmana> HoraInici<int> HoraFinal<int>");
        Classe cl;
        try{
            String as = keyboard.next(), g = keyboard.next(), au = keyboard.next(), dia = keyboard.next();
            int ini = keyboard.nextInt(), fin = keyboard.nextInt();
            if(ini > fin){
                output.println("L'hora final ha de ser posterior a la inicial.");
                return;
            }
            cl = new Classe(as, g, DiaSetmana.string_To_DiaSetmana(dia), ini, fin, au);
        }catch(IllegalArgumentException iae){
            output.println("Has introduit algun dels atributs incorrectament.");
            return;
        }
        output.println("Classes eliminades: ");
        for(Classe c: rest.deletePossibilities(classes, cl)){
            output.println("\t" + c.toString());
        }
    }

    /**
     * Comprova que dues Classes no es solapin en el temps.
     */
    private static void no_Solapis_Test() {
        out.println("Aquesta funcio esta implementada a la classe Restriccio, i comprova si dos intervals de temps es solapen.");
        out.println("Introdueix els seguents valors, separats per un espai:");
        out.println("\tIniciA<int> FinalA<int> IniciB<int> FinalB<int>");
        int ai = keyboard.nextInt(), af = keyboard.nextInt(), bi = keyboard.nextInt(), bf = keyboard.nextInt();
        if(af < ai || bf < bi){
            output.println("Les hores finals han de ser posteriors a les inicials.");
            return;
        }
        output.println("Resultat: " + rest.solapenHores(ai, af, bi, bf));
    }

}