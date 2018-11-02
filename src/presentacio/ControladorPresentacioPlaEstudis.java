package presentacio;

import domain.ControladorAules;
import domain.ControladorPlaEstudis;
import domain.PlaEstudis;

import java.util.Scanner;

public class ControladorPresentacioPlaEstudis {

    private ControladorPresentacioMenuPrincipal master;
    private String id_plaEstudi;
    private boolean sortir;

    public ControladorPresentacioPlaEstudis(String id_pe, ControladorPresentacioMenuPrincipal master) {
        this.master = master;
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
                mostraAssignatures();
                break;
            case "add assig":
                    afegirAssignatura();
                break;
            case "rm assig":
                eliminarAssignatura();
                break;
            case "show aules":
                master.printAules();
                break;
            case "add aula":
                master.afegirAula();
                break;
            case "rm aula":
                master.eliminarAula();
                break;
            case "reset aules":
                master.restaurarDadesAules();
                break;
            case "gen horari":
                master.CtrlPE.generarHorari(id_plaEstudi);
                break;
            case "show horari":
                if (master.CtrlPE.hasHorari(id_plaEstudi))
                    master.CtrlPE.printHorari(id_plaEstudi);
                else System.out.println("ERROR: " + id_plaEstudi + " does not have any Horari");
                break;
            case "exit":
                sortir = true;
                break;
            case "quit":
                master.endExecution();
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
                "- Afegir una Assignatura (add assig)\n " +
                "- Eliminar una Assignatura (rm assig)\n " +
                "- Consultar Aules (show aules)\n " +
                "- Afegir una aula (add aula)\n " +
                "- Eliminar una aula (rm aula)\n " +
                "- Restaurar dades aules (reset aules)\n " +
                "- Generar Horari (gen horari)\n " +
                "- Consultar Horari (show horari)\n " +
                "- Sortir del pla d'estudis (exit)\n " +
                "- Sortir del Generador (quit)\n ");
    }

    public void display(){
        master.CtrlPE.generarHorari(id_plaEstudi);
        if (master.CtrlPE.hasHorari(id_plaEstudi))
            master.CtrlPE.printHorari(id_plaEstudi);
        else System.out.println("ERROR: " + id_plaEstudi + " does not have any Horari");
    }

    private void mostraAssignatures() {
        String aux = master.CtrlPE.toStringAssignatures(id_plaEstudi);
        if (aux.isEmpty())
            System.out.println(id_plaEstudi + "no té assignatures");
        System.out.println(aux);
    }

    private void afegirAssignatura() {
        master.CtrlPE.afegirAssignatura(id_plaEstudi);
    }

    private void eliminarAssignatura() {
        System.out.println("*Pots eliminar les següents assignatures:\n");
        mostraAssignatures();
        master.CtrlPE.eliminarAssignatura(id_plaEstudi); }

}
