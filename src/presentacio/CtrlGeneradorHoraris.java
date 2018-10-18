package presentacio;

import domain.Aula;
import domain.PlaEstudis;
import persistencia.Lector_Aules;
import persistencia.Lector_Pla;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class CtrlGeneradorHoraris {
    private static final ArrayList<PlaEstudis> plansEstudis = new ArrayList<PlaEstudis>();
    private static final ArrayList<Map> aularis = new ArrayList<>();
    private static final Map<String, Aula> aules = new HashMap<>();

    private static final String welcome_msg = "####################################\nGenerador d'Horaris sGE. Ministeri d'Energia de Kazakstan |*|*|*|\n####################################";

    public void initialize() throws Exception {
        initializePlaEstudis();
        initializeAules();
        Menu();
    }

    private void Menu() {
        int action = -1;
        System.out.println(welcome_msg);
        while (true) {
            System.out.print("\n");
            printActions();
            action = read_command();
            System.out.println("Your action number is " +action+"\n");
            switch (action) {
                case 01:
                    printAules();
                    break;
                case 02:
                    printPlaEstudis();
                    break;
                case -1:
                    System.out.println("Unkwnonwn command");
                    break;
                default: System.out.println("Error");
            }
        }
    }

    private void printAules() {
        for (Aula entry : aules.values()) {
            System.out.print(entry.getId()+" ");
        }
        System.out.println("");
    }

    private void printPlaEstudis() {
        for (PlaEstudis entry : plansEstudis) {
            System.out.println(entry.getID());
        }
    }

    private int read_command() {
        int action = -1;
        Scanner reader = new Scanner(System.in);
        String cmd;
        while ((cmd= reader.nextLine()).equals(""));

        switch (cmd) {
            case "show aules":
                action = 01;
                break;
            case "show plans":
                action = 02;
                break;
            default:
                action = -1;
                break;
        }
        return action;
    }

    private void printActions() {
        System.out.println("Pots executar:\n - Consultar Aules (show aules)\n - Consultar pla Estudis (show plans)");
    }

    private void initializePlaEstudis() throws Exception {
        File PEfolder = new File("data/PlaEstudis");
        if (!PEfolder.isDirectory()) {
            throw new Exception("Error with data/PlaEstudis folder!");
        }

        File[] PEs = PEfolder.listFiles();
        for (File pe_file : PEs) {
            System.out.println("The following PlaEstudis file is being readed: " + "'" + PEfolder.getAbsolutePath());
            PlaEstudis pe_aux = Lector_Pla.llegeixPlaEstudis(pe_file.getAbsolutePath());
            if (!plansEstudis.add(pe_aux)) {
                throw new Exception("Cannot read '" + pe_file.getName() + "' file");
            }
        }
    }

    private void initializeAules() throws Exception {
        File AUfolder = new File("data/Aules");
        if (!AUfolder.isDirectory()) {
            throw new Exception("Error with data/Aules folder!");
        }

        File[] Aules = AUfolder.listFiles();
        for (File au_file : Aules) {
            System.out.println("The following Aules file is being readed: " + "'" + AUfolder.getAbsolutePath());
            Map<String, Aula> aula_aux = Lector_Aules.llegeixAules(au_file.getAbsolutePath());
            if (!aularis.add(aula_aux)) {
                throw new Exception("Cannot read '" + au_file.getName() + "' file");
            }
            aules.putAll(aula_aux);
        }
    }


}
