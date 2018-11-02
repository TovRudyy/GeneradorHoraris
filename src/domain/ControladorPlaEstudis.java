package domain;


import persistencia.ControladorPersistencia;
import presentacio.ControladorPresentacioMenuPrincipal;

import java.util.ArrayList;
import java.util.Scanner;

public class ControladorPlaEstudis {

    static ControladorPersistencia CtrlDades = new ControladorPersistencia();
    static ArrayList<PlaEstudis> ConjuntPE;

    public ControladorPlaEstudis() {
        ConjuntPE = CtrlDades.llegeixDadesPE();
    }

    public ArrayList<String> getInfoPlans(){
        ArrayList<String> ret = new ArrayList<String>();
        for (PlaEstudis pe : ConjuntPE) {
            ret.add(pe.getID());
        }
        return ret;
    }

    public boolean exists(String id) {
        return (getPlaEstudi(id) != null);
    }

    public String toStringAssignatures(String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.toStringAssignatures();
    }

    public void generarHorari(String id) {
        getPlaEstudi(id).generaHorari();
    }

    public boolean hasHorari(String id) {
        return (getPlaEstudi(id).hasHorari());
    }

    public void printHorari(String id) {
        getPlaEstudi(id).printHorari();
    }

    private PlaEstudis getPlaEstudi(String id) {
        for (PlaEstudis pe : ConjuntPE) {
            if (pe.getID().equals(id))
                return pe;
        }
        return null;
    }

    public void afegirAssignatura(String id) {
        Scanner reader = new Scanner(System.in);
        String arg;
        PlaEstudis pe = getPlaEstudi(id);
        System.out.print("*Introdueix el path al fitxer de l'assignatura: ");
        arg = reader.next();
        assignatura nova = CtrlDades.llegeixAssignatura(arg);
        if (pe.existsAssignatura(nova.getId())) {
            System.out.println("ERROR: l'assignatura " + nova.getId() + " ja existeix!");
            return;
        }
        pe.addAssignatura(nova);
        System.out.println("DEBUG: l'assignatura " + nova.getId() + " s'ha afegit");
    }

    public void eliminarAssignatura(String id) {
        Scanner reader = new Scanner(System.in);
        String arg;
        PlaEstudis pe = getPlaEstudi(id);
        System.out.print("Indica l'assignatura que vols eliminar: ");
        arg = reader.next();
        pe.eliminarAssignatura(arg);
    }

    public void resetData() {
        ConjuntPE.clear();
        ConjuntPE = CtrlDades.llegeixDadesPE();
        System.out.println("DEBUG: s'han restaurat les dades dels Plans d'Estudis");
    }

}
