package domain;


import persistencia.ControladorPersistencia;
import presentacio.ControladorPresentacioMenuPrincipal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        System.out.print("GH: Introdueix el path al fitxer de l'assignatura: ");
        arg = reader.next();
        ArrayList<assignatura> noves = CtrlDades.llegeixAssignatura(arg);
        if (noves.isEmpty()) {
            System.err.println("DEBUG: no s'han pogut afegir assignatures");
            return;
        }
        for (assignatura a: noves) {
            if (pe.existsAssignatura(a.getId())) {
                System.err.println("ERROR: l'assignatura " + a.getId() + " ja existeix!");
            }
            else {
                pe.addAssignatura(a);
                System.err.println("DEBUG: l'assignatura " + a.getId() + " s'ha afegit");
            }
        }

    }

    public void eliminarAssignatura(String id) {
        Scanner reader = new Scanner(System.in);
        String arg;
        PlaEstudis pe = getPlaEstudi(id);
        System.out.print("GH: Indica l'assignatura que vols eliminar: ");
        arg = reader.next();
        pe.eliminarAssignatura(arg);
    }

    public void resetData() {
        ConjuntPE.clear();
        ConjuntPE = CtrlDades.llegeixDadesPE();
        System.err.println("DEBUG: s'han restaurat les dades dels Plans d'Estudis");
    }

    public void llistatAssignatures(String id) {
        PlaEstudis pe = getPlaEstudi(id);
        String[] noms = pe.toStringNomAssignatures();
        int i;
        for (i = 0; i < noms.length-1; i++) {
            System.out.print(noms[i] + ",");
        }
        System.out.println(noms[i]);
    }

    public String getDetallAssignatura(String id, String idAssig) {
        PlaEstudis pe = getPlaEstudi(id);
        if (pe.existsAssignatura(idAssig)) {
            return pe.detallsAssignatura(idAssig);
        }
        System.err.println("ERROR: no existeix l'assignatura " + idAssig);
        return null;
    }

    public void guardaHorari(String id) {
        PlaEstudis pe = getPlaEstudi(id);
        String h = pe.getHorari();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        h =  pe.getID() + "\n" + "Data creació: " + strDate + "\n" + h;
        System.out.println("GH: introdueix el pathname del fitxer on vols guardar l'horari");
        Scanner reader = new Scanner(System.in);
        String arg;
        arg = reader.next();
        if (CtrlDades.guardaHorari(h, arg)) {
            System.out.println("INFO: s'ha guardat l'horari en " + arg);
        }
    }

}
