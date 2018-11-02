package presentacio;

import domain.ControladorAules;
import domain.ControladorPlaEstudis;
import domain.PlaEstudis;

import java.util.Scanner;

public class ControladorPresentacioPlaEstudis extends ControladorPresentacioMenuPrincipal {


    private String id_plaEstudi;
    private boolean sortir;

    public ControladorPresentacioPlaEstudis(String id_pe) {
        id_plaEstudi = id_pe;
        sortir = false;
    }

    public void MenuPrincipal(){
        while (!sortir) {
            printActionsMenuPrincipal();
            executeActionsMenuPrincipal();
        }
    }

    private void executeActionsMenuPrincipal() {
        Scanner reader = new Scanner(System.in);
        String cmd;
        String arg;
        while ((cmd = reader.nextLine()).equals("")) { /*No llegeix si no s'introdueix res per teclat*/ }
        switch (cmd) {
            case "show assig":
                String aux = super.CtrlPE.toStringAssignatures(id_plaEstudi);
                if (aux.isEmpty())
                    System.out.println(id_plaEstudi + "no té assignatures");
                System.out.println(aux);
                break;
            case "show aules":
                super.printAules();
                break;
            case "gen horari":
                super.CtrlPE.generarHorari(id_plaEstudi);
                break;
            case "show horari":
                if (super.CtrlPE.hasHorari(id_plaEstudi))
                    super.CtrlPE.printHorari(id_plaEstudi);
                else System.out.println("ERROR: " + id_plaEstudi + " does not have any Horari");
                break;
            case "exit":
                sortir = true;
                break;
            case "quit":
                super.endExecution();
                break;
            default:
                System.out.println("ERROR: bad command");
                break;
        }
    }
    private void printActionsMenuPrincipal() {
        System.out.println("** Pla Estudis: " + id_plaEstudi + " **");
        System.out.println("#Pots executar:\n " +
                "- Consultar Assignatures (show assig)\n " +
                "- Consultar Aules (show aules)\n " +
                "- Generar Horari (gen horari)\n " +
                "- Consultar Horari (show horari)\n " +
                "- Sortir del pla d'estudis (exit)\n " +
                "- Sortir del Generador (quit)\n ");
    }

    public void display(){
        super.CtrlPE.generarHorari(id_plaEstudi);
        if (super.CtrlPE.hasHorari(id_plaEstudi))
            super.CtrlPE.printHorari(id_plaEstudi);
        else System.out.println("ERROR: " + id_plaEstudi + " does not have any Horari");
    }

}
