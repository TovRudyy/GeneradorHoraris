package presentacio;

import domain.ControladorAules;
import domain.ControladorPlaEstudis;

import java.util.ArrayList;
import java.util.Scanner;


public class ControladorPresentacioMenuPrincipal {
    static ControladorPlaEstudis CtrlPE;
    static ControladorAules CtrlAUS;

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
                "- Afegir una aula (add aula)\n " +
                "- Eliminar una aula (rm aula)\n " +
                "- Consultar Pla Estudis (show plans)\n " +
                "- Seleccionar un Pla Estudis (select pe)\n " +
                "- Restaurar les dades (reset dades, reset aules, reset pe)\n " +
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
            case "add aula":
                afegirAula();
                break;
            case "rm aula":
                eliminarAula();
                break;
            case "show plans":
                printPlaEstudis();
                break;
            case "select pe":
                System.out.println("Plans d'estudis actuals:");
                printPlaEstudis();
                System.out.print("Seleccio: ");
                arg = reader.next();
                System.out.println("DEBUG msg: arg = " + arg);
                if (CtrlPE.exists(arg)) {
                    System.out.println("Has seleccionat " + arg);
                    PresentacioPE = new
                            ControladorPresentacioPlaEstudis(arg, this);
                    PresentacioPE.MenuPrincipal();
                }
                else System.out.println("ERROR: " + arg + " does not exists");
                break;
            case "reset dades":
                restaurarDadesAules();
                restaurarDadesPE();
                break;
            case "reset aules":
                restaurarDadesAules();
                break;
            case "reset pe":
                restaurarDadesPE();
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

    protected void afegirAula() {
        Scanner reader = new Scanner(System.in);
        String arg;
        System.out.println("*Per llegir un fitxer amb aules introdueix (file)\n" +
                "*Per afegir una aula interactivament introdueix (manual)\n");
        arg = reader.next();
        System.out.println("DEBUG msg: arg = " + arg);
        switch (arg) {
            case "file":
                CtrlAUS.llegeixFitxerAula();
                break;
            case "manual":
                CtrlAUS.afegirNovaAula();
                break;
        }
    }

    protected void eliminarAula(){
        Scanner reader = new Scanner(System.in);
        String arg;
        System.out.println("*Pots eliminar les següents aules:");
        printAules();
        System.out.print("Introdueix el id de l'aula a eliminar:");
        arg = reader.next();
        CtrlAUS.eliminarAula(arg);
    }

    protected void restaurarDadesAules() {
        CtrlAUS.resetData();
    }

    protected void restaurarDadesPE() {
        CtrlPE.resetData();
    }


}
