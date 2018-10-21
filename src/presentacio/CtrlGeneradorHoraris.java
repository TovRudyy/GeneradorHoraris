package presentacio;

import domain.Aula;
import domain.PlaEstudis;
import persistencia.Lector_Aules;
import persistencia.Lector_Pla;

import java.util.TreeMap;
import java.util.Map;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class CtrlGeneradorHoraris {
    private static  ArrayList<PlaEstudis> plansEstudis = new ArrayList<PlaEstudis>();
    private static  Map<String, Aula> aules;

    private static final String welcome_msg = "####################################\nGenerador d'Horaris sGE. Ministeri d'Energia de Kazakstan |*|*|*|\n####################################";

    public void initialize() throws Exception {
        plansEstudis = Lector_Pla.readFolderPlaEstudis();
        aules = Lector_Aules.readFolderAules();
        System.out.println(welcome_msg+"\n");
        MenuPrincipal();
    }

    private void MenuPrincipal() {
        while (true) {
            printActions();
            Scanner reader = new Scanner(System.in);
            String cmd;
            while ((cmd = reader.nextLine()).equals(""));
            switch (cmd) {
                case "show aules":
                    printAules();
                    break;
                case "show plans":
                    printPlaEstudis();
                    break;
                default:
                    System.out.println("Error: bad command");
                    break;
            }
            System.out.print("\n\n");
        }
    }

    private void printAules() {
        for (Aula entry : aules.values()) {
            System.out.print(entry.getId()+" ");
        }
    }

    private void printPlaEstudis() {
        for (PlaEstudis entry : plansEstudis) {
            System.out.println(entry.getID());
        }
    }

    private void printActions() {
        System.out.println("Pots executar:\n - Consultar Aules (show aules)\n - Consultar pla Estudis (show plans)");
    }

}
