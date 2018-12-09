package domain;


import persistencia.ControladorPersistencia;
import sun.awt.SunHints;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Controlador del pla d'estudis que permet tant llegir-lo com modificar-lo.
 * @author Olek
 */

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


    /**
     * @return Una arrayList amb la informacio de els plans d'estudis disponibles.
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
     * @return Un boolea que indica si hi ha un pla d'estudis amb aquest identificador.
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
     *
     * @param id Identificador del pla d'Estudis
     * @return un ArrayList amb l'identificador de totes les assignatures del pla
     */
    public ArrayList<String> getAssignatures(String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getAssignatures();
    }

    /**
     * Genera un horari pel pla d'estudis amb el id del parametre.
     * @param id Identificador del pla d'estudis.
     */
    public void generarHorari(String id) {
        getPlaEstudi(id).generaHorari(ControladorAules.getAules());
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
     * Retorna el pla d'estudis que té com a identificador el valor passat per parametre.
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
     * Reinicia totes les dades del pla d'estudis amb la informacio llegida directament dels fitxers.
     */
    public void resetData() {
        ConjuntPE.clear();
        ConjuntPE = CtrlDades.llegeixDadesPE();
        System.err.println("DEBUG: s'han restaurat les dades dels Plans d'Estudis");
    }

    /**
     * Imprimeix per pantalla el llistat de assignatures del pla d'estudis.
     * @param id Identificador del pla d'estudis.
     */
    public void llistatAssignatures(String id) {
        PlaEstudis pe = getPlaEstudi(id);
        String[] noms = pe.toStringNomAssignatures();
        int i;
        for (i = 0; i < noms.length-1; i++) {
            System.out.print(noms[i] + ",");
        }
        System.out.println(noms[i]);
    }

    /**
     * Retorna els detalls de una assignatura concreta d'un pla d'estudis.
     * @param id Identificador del pla d'estudis
     * @param idAssig Identificador de la assignatura.
     * @return
     */
    public String getDetallAssignatura(String id, String idAssig) {
        PlaEstudis pe = getPlaEstudi(id);
        if (pe.existsAssignatura(idAssig)) {
            return pe.detallsAssignatura(idAssig);
        }
        System.err.println("ERROR: no existeix l'assignatura " + idAssig);
        return null;
    }

    /**
     * Guarda el horari del pla d'estudis amb el identificador donat.
     * @param id Identificador del pla d'estudis.
     */
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

    /**
     * Imprimeix per pantalla els horaris que tenim guardats i permet que l'usuari en trii un i l'imprimeixi.
     */
    public void visualitzaHorari() {
        System.out.print("INFO: tens guardats els següents horaris:\n");
        CtrlDades.mostraFitxersHoraris();
        System.out.print("INFO: indica l'horari que vols visualitzar:");
        Scanner reader = new Scanner(System.in);
        String arg;
        arg = reader.next();
        CtrlDades.visualitzaHorari(arg);
    }

    /**
     * L'usuari introdueix una ruta de l'escena i es carreguen el seu pla d'estudis i el seu aulari.
     * @param dir Ruta del directori de la escena.
     */
    public void carregaEscena(String dir) {
        ConjuntPE.clear();
        String file = dir + EscenaPE;
        PlaEstudis pe = CtrlDades.llegeixPE(file);
        if (pe != null)
            ConjuntPE.add(pe);
    }

    public String getNomAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getNomAssignatura(assig);
    }

    public int getNivellAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getNivellAssignatura(assig);
    }

    public int getQtClassesTeoriaAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getQtClassesTeoriaAssignatura(assig);
    }

    public int getDuradaClassesTeoriaAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getDuradaClassesTeoriaAssignatura(assig);
    }

    public int getQtClassesProblemesAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getQtClassesProblemesAssignatura(assig);
    }

    public int getDuradaClassesProblemesAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getDuradaClassesProblemesAssignatura(assig);
    }

    public int getQtClassesLaboratoriAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getQtClassesLaboratoriAssignatura(assig);
    }

    public int getDuradaClassesLaboratoriAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getDuradaClassesLaboratoriAssignatura(assig);
    }

    public ArrayList<String> getGrupsAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getGrupsAssignatura(assig);
    }

    public int getCapacitatGrup(String grup, String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getCapacitatGrup(grup, assig);
    }

    public String getHorariGrup(String grup, String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getHorariGrup(grup, assig);
    }

    public String getTipusAulaGrup(String grup, String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getTipusAulaGrup(grup, assig);
    }

    public ArrayList<String> getSubgrupsGrup(String grup, String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getSubgrupsGrup(grup, assig);
    }

    public ArrayList<String> getCorrequisitsAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getCorrequisitsAssignatura(assig);
    }

    public void borrarPlansEstudis() {
        ConjuntPE.clear();
    }

    public void carregarFitxerPlaEstudis(String absolutePath) {
        PlaEstudis pe = CtrlDades.llegeixPE(absolutePath);
        if (pe != null)
            ConjuntPE.add(pe);
    }

    public boolean existsPlaEstudi(String pe) {
        return (getPlaEstudi(pe) != null);
    }
}
