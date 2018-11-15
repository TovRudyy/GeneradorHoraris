package testsClasses.Drivers;

import domain.*;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;


class driverRestriccioSubgrup  {

    private static RestriccioSubgrup rest = new RestriccioSubgrup(new grup("0", 0, "M", Tipus_Aula.TEORIA));

    static void main() {
        printMenu();
        executar();
    }

    private static void printMenu() {
        out.println("Driver de la Classe RestriccioSubgrup.");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat: ");
        printCodis();
        out.println("Aquest driver mante una unica instancia de RestriccioSubgrup, que va sobreescribint cada cop que proves la constructora");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
    }

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Esborrar classes incompatibles");
        out.println("\t3) Sortir");
    }

    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while((codi = keyboard.nextInt()) != 5){
                switch (codi) {
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        constructor_Test();
                        break;
                    case 4:
                        delete_Possibilities_Test();
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

    private static void delete_Possibilities_Test() {
        out.println("Aquesta funcio rep com a parametres una Classe, i un conjunt de Classes classificades segons la seva aula i el Dia de la setmana.");
        out.println("El resultat d'aquesta funcio es la llista de Classes que s'han eliminat del conjunt degut a que son incompatibles amb la Classe passada.");
        out.println("Dues classes son incompatibles si violen la restriccio de subgrup.");
        out.println("Com que el conjunt de classes que rep aquesta funcio es bastant complex, s'ha d'introduir mitjancant un fitxer JSON.");
        out.println("Aquest fitxer es troba dins la carpeta \"Data\", subcarpeta \"Drivers_Input\", i el seu nom ha de ser \"RestriccioSubgrup_InputMap.json\".");
        out.println("Ja et proporcionem un fitxer creat amb dades de mostra fetes, que pots utilitzar perfectament.");
        out.println("Aquestes dades consisteixen en totes les Classes possibles de dues hores, de dues Assignatures diferents(A i TC), cadascuna amb 3 grups(10, 11 i 12), repetit per cada dia i en dues Aules diferents(Aula1 i Aula2).");
        out.println("Quan tinguis el fitxer llest per ser llegit, escriu \"C\" i apreta \"Enter\": ");
        if(!keyboard.next().equals("C")) return;
        Map<String, Map<DiaSetmana, ArrayList<Classe>>> classes;
        try {
            classes = Lector_Drivers_JSON.llegirFitxer_RestriccioSubgrup_InputMap();
        }catch(FileNotFoundException fnfe){
            out.println("El fitxer no s'ha trobat.");
            return;
        }catch(ParseException pe){
            out.println("El fitxer no te un format correcte.");
            return;
        }catch(IOException ioe){
            out.println("Hi ha hagut algun problema amb el fitxer:");
            out.println(ioe.getMessage());
            return;
        }
        out.println("Fitxer llegit. Ara introdueix la Classe que vols utilitzar per eliminar les incompatibles: ");
        out.println("\tId_Assignatura<String> Id_Grup<String> Id_Aula<String> Dia<DiaSetmana> HoraInici<int> HoraFinal<int>");
        Classe cl;
        try{
            String as = keyboard.next(), g = keyboard.next(), au = keyboard.next(), dia = keyboard.next();
            int ini = keyboard.nextInt(), fin = keyboard.nextInt();
            if(ini > fin){
                out.println("L'hora final ha de ser posterior a la inicial.");
                return;
            }
            cl = new Classe(as, g, DiaSetmana.string_To_DiaSetmana(dia), ini, fin, au);
        }catch(IllegalArgumentException iae){
            out.println("Has introduit algun dels atributs incorrectament.");
            return;
        }
        out.println("Classes eliminades: ");
        for(Classe c: rest.deletePossibilities(classes, cl)){
            out.println("\t" + c.toString());
        }
    }

    private static void constructor_Test() {
        out.println("La Classe RestriccioSubgrup te un unic atribut que es un grup. Aquest es el grup pare del grup que conte la instancia de RestriccioSubgrup.");
        out.println("A efectes d'aquest driver, el grup pare que posis no tindra cap efecte pel que fa a la funcio de esborrar classes incompatibles,");
        out.println("\tja que la comprovacio es fa seguint la codificacio, i no el grup guardat (que s'usa amb fins de consulta).");
        out.println("Introdueix els seguents atributs, separats per un espai: ");
        out.println("\tIdentificador<String> Capacitat<int> Horari<M | T> Tipus<Tipus_Aula>");
        try {
            rest = new RestriccioSubgrup(new grup(keyboard.next(), keyboard.nextInt(), keyboard.next(), Tipus_Aula.string_to_Tipus_Aula(keyboard.next())));
        }catch(Aula_Exception ae){
            out.println(ae.getMessage());
        }catch (InputMismatchException ime){
            out.println("Has introduit algun atribut incorrectament");
        }
    }


}