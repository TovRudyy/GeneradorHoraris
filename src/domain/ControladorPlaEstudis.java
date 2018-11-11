package domain;


import persistencia.ControladorPersistencia;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class ControladorPlaEstudis {

    static ControladorPersistencia CtrlDades = new ControladorPersistencia();
    static ArrayList<PlaEstudis> ConjuntPE;
    static final String EscenaPE = "/PlaEstudi.json";

    /**
     * Crea un nou ControladorPlaEstudis amb les dades que llegeix.
     */
    public ControladorPlaEstudis() {
        ConjuntPE = CtrlDades.llegeixDadesPE();
    }


    //???
    /**
     *
     * @return
     */
    public ArrayList<String> getInfoPlans(){
        ArrayList<String> ret = new ArrayList<String>();
        for (PlaEstudis pe : ConjuntPE) {
            ret.add(pe.getID());
        }
        return ret;
    }



    /**
     * @param id Identificador del pla d'estudis
     * @return Un booleà que indica si hi ha un pla d'estudis amb aquest identificador.
     */
    public boolean exists(String id) {
        return (getPlaEstudi(id) != null);
    }


    /**
     * @param id Identificador del pla d'estudis.
     * @return Una string amb tota la informació de totes les assignatures d'aquest pla d'estudis.
     */
    public String toStringAssignatures(String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.toStringAssignatures();
    }

    /**
     * Genera un horari pel pla d'estudis amb el id del paràmetre.
     * @param id Identificador del pla d'estudis.
     */
    public void generarHorari(String id) {
        getPlaEstudi(id).generaHorari();
    }

    /**
     * Indica si el pla d'estudis amb aquest identificador té un horari.
     * @param id Identificador del pla d'estudis.
     */
    public boolean hasHorari(String id) {
        return (getPlaEstudi(id).hasHorari());
    }


//    /**
//     * Imprimeix per pantalla el horari del pla d'estudis amb el identificador passat per paràmetre
//     * @param id Identificador del pla d'estudis.
//     */
//    public void printHorari(String id) {
//        getPlaEstudi(id).printHorari();
//    }


    /**
     * Una string amb el horari corresponent a aquest pla d'estudis.
     * @param id Identificador del pla d'estudis.
     */
    public String getHorari(String id) {
        return getPlaEstudi(id).getHorari();
    }


    /**
     * Retorna el pla d'estudis que té com a identificador el valor passat per paràmetre.
     * @param id Identificador del pla d'estudis.
     */
    private PlaEstudis getPlaEstudi(String id) {
        for (PlaEstudis pe : ConjuntPE) {
            if (pe.getID().equals(id))
                return pe;
        }
        return null;
    }


    /**
     * Llegeix una assignatura, i l'afegeix al pla d'estudis seleccionat.
     * @param id Identificador el pla d'estudis
     */
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

    /**
     * Llegeix el identificador de la assignatura, i elimina aquesta assignatura del pla d'estudis seleccionat.
     * @param id Identificador el pla d'estudis
     */
    public void eliminarAssignatura(String id) {
        Scanner reader = new Scanner(System.in);
        String arg;
        PlaEstudis pe = getPlaEstudi(id);
        System.out.print("GH: Indica l'assignatura que vols eliminar: ");
        arg = reader.next();
        pe.eliminarAssignatura(arg);
    }

    /**
     * Reinicia totes les dades del pla d'estudis amb la informació llegida directament dels fitxers.
     */
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
        System.out.println("GH: introdueix el nom del fitxer on vols guardar l'horari");
        Scanner reader = new Scanner(System.in);
        String arg;
        arg = reader.next();
        String aux;
        if ( (aux = CtrlDades.guardaHorari(h, arg)) != null) {
            System.out.println("INFO: s'ha guardat l'horari en " + aux);
        }
    }

    public void visualitzaHorari() {
        System.out.print("INFO: tens guardats els següents horaris:\n");
        CtrlDades.mostraFitxersHoraris();
        System.out.print("INFO: indica l'horari que vols visualitzar:");
        Scanner reader = new Scanner(System.in);
        String arg;
        arg = reader.next();
        CtrlDades.visualitzaHorari(arg);
    }

    public void carregaEscena(String dir) {
        ConjuntPE.clear();
        String file = dir + EscenaPE;
        PlaEstudis pe = CtrlDades.llegeixPE(file);
        if (pe != null)
            ConjuntPE.add(pe);
    }

}
