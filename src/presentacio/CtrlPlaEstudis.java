package presentacio;


import java.util.Scanner;


/**
 * Controlador de la capa de presentacio. Responsable de gestionar dades
 * relacionades amb els Plans d'Estudis. Nomes util quan l'aplicacio s'executa
 * en mode CLI.
 * @author Oleksandr Rudyy
 */

public class CtrlPlaEstudis {

    private CtrlMnPrincipal master;
    private String id_plaEstudi;
    private boolean sortir;

    public CtrlPlaEstudis(String id_pe, CtrlMnPrincipal master) {
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
            case "get assig":
                getDetallsAssignatura();
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
                    System.out.println(master.CtrlPE.getHorari(id_plaEstudi));
                else System.err.println("ERROR: " + id_plaEstudi + " does not have any Horari");
                break;
            case "save horari":
                if (master.CtrlPE.hasHorari(id_plaEstudi))
                    master.CtrlPE.guardaHorari(id_plaEstudi);
                else System.err.println("ERROR: " + id_plaEstudi + " does not have any Horari");
                break;
            case "carrega horari":
                master.CtrlPE.carregaHorari("./data/Hey.ser");
                break;
            case "modifica entrada":
                master.CtrlPE.modificaEntrada(id_plaEstudi);
                break;
            case "exit":
                sortir = true;
                break;
            case "quit":
                master.endExecution();
                break;
            default:
                System.err.println("ERROR: bad command");
                break;
        }
    }
    private void printActionsMenuPrincipal() {
        System.out.println("GH: Pla Estudis: " + id_plaEstudi );
        System.out.println("GH: pots executar:\n " +
                "- Consultar Assignatures (show assig) | afegir una Assignatura (add assig) | eliminar una Assignatura (rm assig)\n " +
                "- Mostrar detalls d'una assignatura (get assig)\n " +
                "- Consultar Aules (show aules) | afegir una aula (add aula) | eliminar una aula (rm aula)\n " +
                "- Restaurar dades aules (reset aules)\n " +
                "- Generar Horari (gen horari) | consultar Horari (show horari) | guardar horari (save horari)\n " +
                "- Modificar una de les entrades del horari per una de diferent (modifica entrada)\n" +
                "- Sortir del pla d'estudis (exit)\n " +
                "- Sortir del Generador (quit)\n ");
    }

//    public void display(){
//        master.CtrlPE.generarHorari(id_plaEstudi);
//        if (master.CtrlPE.hasHorari(id_plaEstudi))
//            master.CtrlPE.printHorari(id_plaEstudi);
//        else System.err.println("ERROR: " + id_plaEstudi + " does not have any Horari");
//    }

    private void mostraAssignatures() {
        String aux = master.CtrlPE.toStringAssignatures(id_plaEstudi);
        if (aux.isEmpty())
            System.err.println("WARNING: " + id_plaEstudi + "no té assignatures");
        System.out.println(aux);
    }

    private void afegirAssignatura() {
        master.CtrlPE.afegirAssignatura(id_plaEstudi);
    }

    private void eliminarAssignatura() {
        System.out.println("INFO: pots eliminar les següents assignatures:\n");
        mostraAssignatures();
        master.CtrlPE.eliminarAssignatura(id_plaEstudi); }


    private void getDetallsAssignatura() {
        System.out.println("INFO: pots consultar les següents assignatures:");
        master.CtrlPE.llistatAssignatures(id_plaEstudi);
        System.out.println("GH: introdueix el id de l'assignatura:");
        Scanner reader = new Scanner(System.in);
        String arg = reader.next();
        String detalls = master.CtrlPE.getDetallAssignatura(id_plaEstudi, arg);
        if (detalls != null)
            System.out.println(detalls);
    }

}
