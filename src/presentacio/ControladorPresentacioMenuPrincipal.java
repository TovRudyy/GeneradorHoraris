package presentacio;

import domain.ControladorAules;
import domain.ControladorPlaEstudis;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Olek
 */

public class ControladorPresentacioMenuPrincipal {
    static ControladorPlaEstudis CtrlPE;
    static ControladorAules CtrlAUS;

    ControladorPresentacioPlaEstudis PresentacioPE;


    private static final String welcome_msg = "####################################" +
            "\nGenerador d'Horaris v1.0 | @David Pujol @Víctor Diví @Oleksandr Rudyy\n" +
            "####################################";

    public ControladorPresentacioMenuPrincipal() {
        this.CtrlPE = new ControladorPlaEstudis();
        this.CtrlAUS = new ControladorAules();
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            System.out.println("INFO: No hi ha aules");
        for (String atributs : infoAulari) System.out.println(atributs);
    }

    private void printPlaEstudis() {
        ArrayList<String> infoPlans;
        infoPlans = CtrlPE.getInfoPlans();

        if (infoPlans.isEmpty())
            System.out.println("INFO: No hi ha Plans d'Estudi");
        for (String atributs : infoPlans) System.out.println(atributs);
    }

    private void printActionsMenuPrincipal() {
        System.out.println("GH: Pots executar:\n " +
                "- Consultar Aules (show aules) | afegir aules (add aula) | eliminar aules (rm aula)\n " +
                "- Consultar Plans Estudis (show plans) | seleccionar un Pla Estudis (select pe)\n " +
                "- Visualitzar un horari guardat (show horari)\n " +
                "- Carregar un escenari (load scene)\n " +
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
                System.out.println("GH: plans d'estudis actuals:");
                printPlaEstudis();
                System.out.print("GH: seleccio: ");
                arg = reader.next();
                System.err.println("DEBUG msg: arg = " + arg);
                if (CtrlPE.exists(arg)) {
                    System.out.println("INFO: has seleccionat " + arg);
                    PresentacioPE = new
                            ControladorPresentacioPlaEstudis(arg, this);
                    PresentacioPE.MenuPrincipal();
                }
                else System.err.println("ERROR: " + arg + " does not exists");
                break;
            case "show horari":
                CtrlPE.visualitzaHorari();
                break;
            case "load scene":
                carregaEscena();
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
                System.err.println("ERROR: bad command");
                break;
        }
    }

    protected void endExecution() {
        System.exit(0);
    }

    protected void afegirAula() {
        Scanner reader = new Scanner(System.in);
        String arg;
        System.out.println("GH: per llegir un fitxer amb aules introdueix (file)\n" +
                "GH: per afegir una aula interactivament introdueix (manual)\n");
        arg = reader.next();
        System.err.println("DEBUG msg: arg = " + arg);
        switch (arg) {
            case "file":
                CtrlAUS.llegeixFitxerAula();
                break;
            case "manual":
                CtrlAUS.afegirNovaAula();
                break;
            default:
                System.err.println("ERROR: bad option");
                break;
        }
    }

    protected void eliminarAula(){
        Scanner reader = new Scanner(System.in);
        String arg;
        System.out.print("GH: vols eliminar totes les aules (all) o una en concret (single)?:");
        arg = reader.next();
        switch (arg) {
            case "all":
                CtrlAUS.eliminarTotesAules();
                break;
            case "single":
                System.out.println("GH: pots eliminar les següents aules:");
                printAules();
                System.out.print("GH: introdueix el id de l'aula a eliminar:");
                arg = reader.next();
                CtrlAUS.eliminarAula(arg);
        }
    }

    protected void restaurarDadesAules() {
        CtrlAUS.resetData();
    }

    protected void restaurarDadesPE() {
        CtrlPE.resetData();
    }

    protected void carregaEscena() {
        Scanner reader = new Scanner(System.in);
        String arg;
        System.out.print("GH: introdueix el PATH on es troba el directori amb l'escenari:");
        arg = reader.next();    //aquest es el PATH de l'escenari
        CtrlAUS.carregaEscena(arg);
        CtrlPE.carregaEscena(arg);
        CtrlPE.afegirPath (arg);
    }

}
