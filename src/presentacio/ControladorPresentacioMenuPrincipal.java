package presentacio;

import domain.ControladorAules;
import domain.ControladorPlaEstudis;

import java.util.ArrayList;
import java.util.Scanner;


public class ControladorPresentacioMenuPrincipal {
    ControladorPlaEstudis CtrlPE;
    ControladorAules CtrlAUS;

    ControladorPresentacioPlaEstudis PresentacioPE;

    private static final String welcome_msg = "####################################" +
            "\nGenerador d'Horaris sGE. Ministeri d'Energia de Kazakstan |*|*|*|\n" +
            "####################################";

    public ControladorPresentacioMenuPrincipal() {
        this.CtrlPE = new ControladorPlaEstudis();
        this.CtrlAUS = new ControladorAules();
    }

    public void runGeneradorHoraris() throws Exception {
        System.out.println(welcome_msg+"\n");
        MenuPrincipal();
    }

    private void MenuPrincipal() {
        while (true) {
            printActionsMenuPrincipal();
            executeActionsMenuPrincipal();
        }
    }

    protected void printAules() {
        ArrayList<String> infoAulari;
        infoAulari = CtrlAUS.getInfoAulari();

        if (infoAulari.isEmpty())
            System.out.println("No hi ha aules");
        for (String atributs : infoAulari) System.out.println(atributs);
    }

    private void printPlaEstudis() {
        ArrayList<String> infoPlans;
        infoPlans = CtrlPE.getInfoPlans();

        if (infoPlans.isEmpty())
            System.out.println("~No hi ha Plans d'Estudi");
        for (String atributs : infoPlans) System.out.println(atributs);
    }

    private void printActionsMenuPrincipal() {
        System.out.println("#Pots executar:\n " +
                "- Consultar Aules (show aules)\n " +
                "- Consultar Pla Estudis (show plans)\n " +
                "- Seleccionar un Pla Estudis (select pe)\n " +
                "- Sortir del Generador (quit)\n ");
    }

    private void executeActionsMenuPrincipal() {
        Scanner reader = new Scanner(System.in);
        String cmd;
        String arg;
        while ((cmd = reader.nextLine()).equals("")) { /*No llegeix si no s'introdueix res per teclat*/ }
        switch (cmd) {
            case "show aules":
                printAules();
                break;
            case "show plans":
                printPlaEstudis();
                break;
            case "select pe":
                System.out.println("Plans d'estudis actuals:");
                printPlaEstudis();
                System.out.print("Seleccio: ");
                arg = reader.next();
                System.out.println("Debug msg! arg = " + arg);
                if (CtrlPE.exists(arg)) {
                    System.out.println("Has seleccionat " + arg);
                    PresentacioPE = new
                            ControladorPresentacioPlaEstudis(arg);
                    PresentacioPE.MenuPrincipal();
                }
                else System.out.println("ERROR: " + arg + " does not exists");
                break;
            case "quit":
                endExecution();
                break;
            default:
                System.out.println("ERROR: bad command");
                break;
        }
    }

    protected void endExecution() {
        System.exit(0);
    }


}
