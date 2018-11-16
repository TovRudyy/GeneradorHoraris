package testsClasses.Drivers;

import domain.Aula;
import domain.PlaEstudis;
import domain.assignatura;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.*;

/**
 * @author Victor
 */

class driverPlaEstudis {

    private static PlaEstudis pla = new PlaEstudis("Pla_Test");
    private static Map<String, Aula> aules;

    static void main() {
        printMenu();
        executar();
    }

    /**
     * Imprimeix un menu amb les opcions disponibles per a l'usuari.
     */
    private static void printMenu() {
        out.println("Driver de la Classe Pla d'Estudis");
        out.println("Selecciona la funcio que vols provar introduint el seu codi associat: ");
        printCodis();
        out.println("Aquest driver mante una unica instancia de Pla d'Estudis, que va sobreescribint cada cop que proves la constructora.");
        out.println("Al principi, aquesta instancia esta inicialitzada amb uns valors trivials.");
        out.println("Per a crear el pla tens dues opcions, crear-lo i introduir totes les assignatures a ma, o llegint un fitxer JSON (o les dues a la vegada).");
        out.println("Aquest fitxer esta situat a la carpeta \"Data\", subcarpeta \"Drivers_Input\", i s'anomena \"PlaEstudis_InputPla.json\"");
        out.println("Les Aules amb les que es fara l'horari son les que es llegeixen de l'arxiu \"PlaEstudis_InputAules.json\", situat a la mateixa carpeta.");
    }

    /**
     * Imprimeix els codis per a accedir a les diferents funcionalitats.
     */
    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Consultar assignatura");
        out.println("\t3) Eliminar assignatura");
        out.println("\t4) Getter Id");
        out.println("\t5) Mostrar assignatures");
        out.println("\t6) Assignatures -> String");
        out.println("\t7) Generar Horari");
        out.println("\t8) Consultora Horari");
        out.println("\t9) Horari -> String");
        out.println("\t10) Executar Joc de Proves");
        out.println("\t11) Sortir");
    }

    /**
     * Executa la opcio que hagi elegit el usuari.
     */
    private static void executar() {
        try{
            int codi;
            out.println("Introdueix un codi: ");
            while((codi = keyboard.nextInt()) != 11){
                switch (codi){
                    case -1:
                        printCodis();
                        break;
                    case 1:
                        constructor_Test();
                        break;
                    case 2:
                        exists_Assignatura_Test();
                        break;
                    case 3:
                        eliminar_Assignatura_Test();
                        break;
                    case 4:
                        get_Id_Test();
                        break;
                    case 5:
                        show_Assignatures_Test();
                        break;
                    case 6:
                        assigs_String_Test();
                        break;
                    case 7:
                        generar_Horari_Test();
                        break;
                    case 8:
                        has_Horari_Test();
                        break;
                    case 9:
                        horari_To_String_Test();
                        break;
                    case 10:
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
     * Executa el joc de proves associat al driver.
     */
    private static void executar_Joc_de_Proves() {
        try{
            keyboard = new Scanner(new FileReader("data/Jocs_de_Prova_Drivers/11.DriverPlaEstudis"));
            output = new PrintStream(new File("data/Jocs_de_Prova_Drivers/Sortida_Jocs"));
        }catch(FileNotFoundException fnfe){
            out.println(fnfe.getMessage());
        }
    }

    /**
     * Imprimeix una string amb el horari del pla d'estudi (si n'hi ha).
     */
    private static void horari_To_String_Test() {
        captura();
        InputStream old = System.in;
        try{
            System.setIn(new FileInputStream("data/Jocs_de_Prova_Drivers/11.DriverPlaEstudis"));
        }catch(FileNotFoundException fnfe){
            out.println(fnfe.getMessage());
        }
        if(pla.hasHorari()) out.println(pla.getHorari());
        else out.println("No s'ha generat cap horari.");
        allibera();
        System.setIn(old);
    }

    /**
     * Comprova si el pla d'estudis test te un horari o no.
     */
    private static void has_Horari_Test() {
        out.println("S'indica si el Pla d'Estudis te un horari ja calculat");
        output.println("Resultat: " + pla.hasHorari());
    }

    /**
     * Genera un horari per al test donat.
     */
    private static void generar_Horari_Test() {
        pla.generaHorari(aules);
    }

    /**
     * Imprimeix la informacio concreta de una assignatura.
     */
    private static void assigs_String_Test() {
        out.println("Tira la opcio que vulguis:");
        out.println("\t1) Noms (Totes)");
        out.println("\t2) Detalls basics (Totes)");
        out.println("\t3) Detalls complets (Una)");
        switch(keyboard.next()){
            case "1":
                for(String n: pla.toStringNomAssignatures()) output.println(n);
                break;
            case "2":
                output.println(pla.toStringAssignatures());
                break;
            case "3":
                out.println("Introdueix l'Identificador de la assignatura a consultar:");
                output.println(pla.detallsAssignatura(keyboard.next()));
                break;
            default:
                out.println("Opcio no valida.");
        }
    }

    /**
     * Mostra les assignatures del pla d'estudis.
     */
    private static void show_Assignatures_Test() {
        captura();
        pla.showAssignatures();
        allibera();
    }

    /**
     * Retorna el identificador del pla d'estudis.
     */
    private static void get_Id_Test() {
        output.println("Identificador: " + pla.getID());
    }

    /**
     * Elimina una assignatura.
     */
    private static void eliminar_Assignatura_Test() {
        out.println("Introdueix l'Identificador de la assignatura a eliminar:");
        pla.eliminarAssignatura(keyboard.next());
    }

    /**
     * Comprova si una assignatura existeix.
     */
    private static void exists_Assignatura_Test() {
        out.println("Introdueix l'Identificador de la assignatura a consultar si existeix:");
        String id = keyboard.next();
        output.println("Resultat: " + pla.existsAssignatura(id));

    }

    /**
     * Construeix dinamicament un test.
     */
    private static void constructor_Test() {
        output.println("Es procedeix a llegir les Aules:");
        try{
            aules = Lector_Drivers_JSON.llegirAules();
            output.println("Aules llegides");
        }catch(Exception e){
            output.println("Hi ha algun problema amb el fitxer d'Aules");
            output.println(e.getMessage());
        }
        output.println("Es continua ara amb la creadora del Pla:");
        output.println("Vols crear(C) o importar(I) el pla:");
        String id = keyboard.next();
        switch (id.toUpperCase()) {
            case "C":
                out.println("Introdueix el nom del Pla:");
                pla = new PlaEstudis(keyboard.next());
                break;
            case "I":
                try {
                    pla = Lector_Drivers_JSON.llegirPlaEstudis();
                    output.println("Pla llegit");
                }catch(Exception e){
                    output.println("Hi ha algun error amb el fitxer");
                    output.println(e.getMessage());
                }
                break;
            default:
                out.println("Opcio no valida.");
                break;
        }
    }

}
