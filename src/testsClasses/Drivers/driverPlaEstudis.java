package testsClasses.Drivers;

import domain.Aula;
import domain.PlaEstudis;
import domain.assignatura;

import java.util.InputMismatchException;
import java.util.Map;

import static java.lang.System.out;
import static testsClasses.Drivers.MainDriver.keyboard;

class driverPlaEstudis {

    private static PlaEstudis pla = new PlaEstudis("Pla_Test");
    private static Map<String, Aula> aules;

    static void main() {
        printMenu();
        executar();
    }

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

    private static void printCodis() {
        out.println("\t1) Constructora");
        out.println("\t2) Afegir assignatura");
        out.println("\t3) Consultar assignatura");
        out.println("\t4) Eliminar assignatura");
        out.println("\t5) Getter Id");
        out.println("\t6) Mostrar assignatures");
        out.println("\t7) Assignatures -> String");
        out.println("\t8) Generar Horari");
        out.println("\t9) Consultora Horari");
        out.println("\t10) Horari -> String");
        out.println("\t11) Sortir");
    }

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
                        afegir_Assignatura_Test();
                        break;
                    case 3:
                        exists_Assignatura_Test();
                        break;
                    case 4:
                        eliminar_Assignatura_Test();
                        break;
                    case 5:
                        get_Id_Test();
                        break;
                    case 6:
                        show_Assignatures_Test();
                        break;
                    case 7:
                        assigs_String_Test();
                        break;
                    case 8:
                        generar_Horari_Test();
                        break;
                    case 9:
                        has_Horari_Test();
                        break;
                    case 10:
                        horari_To_String_Test();
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

    private static void horari_To_String_Test() {
        if(pla.hasHorari()) out.println(pla.getHorari());
        else out.println("No s'ha generat cap horari.");
    }

    private static void has_Horari_Test() {
        out.println("S'indica si el Pla d'Estudis te un horari ja calculat");
        out.println("Resultat: " + pla.hasHorari());
    }

    private static void generar_Horari_Test() {
        pla.generaHorari(aules);

    }

    private static void assigs_String_Test() {
        out.println("Tira la opcio que vulguis:");
        out.println("\t1) Noms (Totes)");
        out.println("\t2) Detalls basics (Totes)");
        out.println("\t3) Detalls complets (Una)");
        switch(keyboard.next()){
            case "1":
                for(String n: pla.toStringNomAssignatures()) out.println(n);
                break;
            case "2":
                out.println(pla.toStringAssignatures());
                break;
            case "3":
                out.println("Introdueix l'Identificador de la assignatura a consultar:");
                out.println(pla.detallsAssignatura(keyboard.next()));
                break;
            default:
                out.println("Opcio no valida.");
        }
    }

    private static void show_Assignatures_Test() {
        pla.showAssignatures();
    }

    private static void get_Id_Test() {
        out.println("Identificador: " + pla.getID());
    }

    private static void eliminar_Assignatura_Test() {
        out.println("Introdueix l'Identificador de la assignatura a eliminar:");
        pla.eliminarAssignatura(keyboard.next());
    }

    private static void exists_Assignatura_Test() {
        out.println("Introdueix l'Identificador de la assignatura a consultar si existeix:");
        String id = keyboard.next();
        out.println("Resultat: " + pla.existsAssignatura(id));

    }

    private static void afegir_Assignatura_Test() {
        out.println("Introdueix els seguent atributs, separats per un espai:");
        out.println("\tIdentificador<String> Nom<String(sense espais)> Nivell<int>");
        try{
            pla.addAssignatura(new assignatura(keyboard.next(), keyboard.next(), keyboard.nextInt()));
        }catch(IllegalArgumentException iae){
            out.println("Has introduit algun dels atributs incorrectament.");
        }

    }

    private static void constructor_Test() {
        out.println("Es procedeix a llegir les Aules:");
        try{
            aules = Lector_Drivers_JSON.llegirAules();
            out.println("Aules llegides");
        }catch(Exception e){
            out.println("Hi ha algun problema amb el fitxer d'Aules");
            out.println(e.getMessage());
        }
        out.println("Es continua ara amb la creadora del Pla:");
        out.println("Vols crear(C) o importar(I) el pla:");
        String id = keyboard.next();
        switch (id.toUpperCase()) {
            case "C":
                out.println("Introdueix el nom del Pla:");
                pla = new PlaEstudis(keyboard.next());
                break;
            case "I":
                try {
                    pla = Lector_Drivers_JSON.llegirPlaEstudis();
                    out.println("Pla llegit");
                }catch(Exception e){
                    out.println("Hi ha algun error amb el fitxer");
                    out.println(e.getMessage());
                }
                break;
            default:
                out.println("Opcio no valida.");
                break;
        }
    }

}
