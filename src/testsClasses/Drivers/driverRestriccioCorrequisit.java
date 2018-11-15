package testsClasses.Drivers;

import domain.Classe;
import domain.DiaSetmana;
import domain.RestriccioCorequisit;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;


class driverRestriccioCorrequisit {

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
                      //  check_Restriccio_Test();
                        break;
                    case 7:
                        delete_Possibilities_Test();
                        break;
                    case 8:
                        to_String_Test();
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

    private static void to_String_Test() {
        out.println("Resultat: " + corequisit.toString());

    }

    private static void delete_Possibilities_Test() {
        out.println("Aquesta funcio esta implementada a Restriccio, i rep com a parametres una Classe, i un conjunt de Classes classificades segons la seva aula i el Dia de la setmana.");
        out.println("El resultat d'aquesta funcio es la llista de Classes que s'han eliminat del conjunt degut a que son incompatibles amb la Classe passada.");
        out.println("El criteri que s'utilitza per determinar que son incompatibles es el provat anteriorment, i en cada subclasse de Restriccio es diferent.");
        out.println("Com que el conjunt de classes que rep aquesta funcio es bastant complex, s'ha d'introduir mitjancant un fitxer JSON.");
        out.println("Aquest fitxer es troba dins la carpeta \"Data\", subcarpeta \"Drivers_Input\", i el seu nom ha de ser \"RestriccioCorequisit_InputMap.json\".");
        out.println("Ja et proporcionem un fitxer creat amb dades de mostra fetes, que pots utilitzar perfectament.");
        out.println("Aquestes dades consisteixen en totes les Classes possibles de dues hores, de dues Assignatures diferents(A i TC), cadascuna amb 3 grups(10, 11 i 12), repetit per cada dia i en dues Aules diferents(Aula1 i Aula2).");
        out.println("Introdueix una C per continuar o una S per sortir: ");
        if(!keyboard.next().toUpperCase().equals("C")) return;
        Map<String, Map<DiaSetmana, ArrayList<Classe>>> classes;
        try {
            classes = Lector_Drivers_JSON.llegirFitxer_RestriccioCorequisit_InputMap();
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
        out.println("Recorda que els corequisits de totes les assignatures llegides del fitxer seran tots els que hi ha actualment dins del corequisit.");
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
        for(Classe c: corequisit.deletePossibilities(classes, cl)){
            out.println("\t" + c.toString());
        }
    }
/*
    private static void check_Restriccio_Test() {
        out.println("Aquesta funcio, declarada com a abstracte per Restriccio, determina si dues Classes son compatibles segons el criteri de cada tipus de Restriccio.");
        out.println("Per probarla s'ha de crear dues Classes, que seran els parametres de la funcio.");
        out.println("Recorda que els corequisits guardats son de la assignatura de la primera classe que introdueixis.");
        out.println("Introdueix els seguents atributs, separats per un espai, per a crear la primera Classe: ");
        out.println("\tId_Assignatura<String> Id_Grup<String> Id_Aula<String>, Dia<DiaSetmana> HoraInicia<int> HoraFinal<int>");
        Classe a, b;
        try{
            String as = keyboard.next(), g = keyboard.next(), au = keyboard.next(), dia = keyboard.next();
            int ini = keyboard.nextInt(), fin = keyboard.nextInt();
            if(ini > fin){
                out.println("L'hora final ha de ser posterior a la inicial.");
                return;
            }
            a = new Classe(as, g, DiaSetmana.string_To_DiaSetmana(dia), ini, fin, au);
        }catch(IllegalArgumentException iae){
            out.println("Has introduit algun dels atributs incorrectament.");
            return;
        }
        out.println("Introdueix ara els atributs de la segona Classe, en el mateix ordre que abans: ");
        try{
            String as = keyboard.next(), g = keyboard.next(), au = keyboard.next(), dia = keyboard.next();
            int ini = keyboard.nextInt(), fin = keyboard.nextInt();
            if(ini > fin){
                out.println("L'hora final ha de ser posterior a la inicial.");
                return;
            }
            b = new Classe(as, g, DiaSetmana.string_To_DiaSetmana(dia), ini, fin, au);
        }catch(IllegalArgumentException iae){
            out.println("Has introduit algun dels atributs incorrectament.");
            return;
        }
        out.println("Resultat: " + corequisit.checkCorrecte(a, b));
    }
*/
    private static void es_Corequisit_Test() {
        out.println("Introdueix l'identificador de la asignatura per saber si es corequisit:");
        out.println("Identificador<String>");
        out.println("Resultat: " + corequisit.esCorrequisit(keyboard.next()));
    }

    private static void add_Assignatura_Test() {
        out.println("Introdueix l'identificador de la assignatura a afegir:");
        out.println("Identificador<String>");
        if(corequisit.addAssignatura(keyboard.next())) out.println("S'ha afegit la assignatura");
        else out.println("No s'ha pogut afegir la assignatura");
    }

    private static void get_Assignatures_Test() {
        out.println("Resultat: ");
        for(String s:corequisit.getAssignatures()) out.println("\t" + s);
    }

    private static void is_Empty_Test() {
        out.println("Resultat: " + corequisit.isEmpty());
    }

    private static void constructor_Test() {
        out.println("La classe RestriccioCorequisit nomes te una constructora per defecte, per tant no necessita parametres.");
        corequisit = new RestriccioCorequisit();
        out.println("Ja s'ha reiniciat el corequisit.");
    }
}