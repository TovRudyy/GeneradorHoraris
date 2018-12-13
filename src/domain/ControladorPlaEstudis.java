package domain;


import persistencia.ControladorPersistencia;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Controlador del pla d'estudis que permet tant llegir-lo com modificar-lo.
 * @author Olek
 */

public class ControladorPlaEstudis {

    static ControladorPersistencia CtrlDades = new ControladorPersistencia();
    static ArrayList<PlaEstudis> ConjuntPE;
    static final String EscenaPE = "/PlaEstudi.json";
    static String path = "";    //aquest path ens indica el ultim path guardat fins al moment

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

    private static class struct implements Serializable {
        PlaEstudis pla;
        Map<String, Aula> aules;

        public struct(PlaEstudis pla, Map<String, Aula> aules) {
            this.pla = pla;
            this.aules = aules;
        }
    }

    /**
     * Guarda el horari del pla d'estudis amb el identificador donat.
     * @param id Identificador del pla d'estudis.
     */
    public void guardaHorari(String id) {
        /*
        PlaEstudis pe = getPlaEstudi(id);
        String h = pe.getHorari();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        if (path.equals(""))
            h =  pe.getID() + "\n" + "Data creació: " + strDate + "\n" +
                    "L'identificador del pla d'estudis es : " + id + "\n\n" + h;
        else
            h =  pe.getID() + "\n" + "Data creació: " + strDate + "\n" +
                 "El path de l'escenari corresponent es : " + path + "\n\n" + h;


        System.out.println("GH: introdueix el nom del fitxer en el que es guardarà l'horari");
        Scanner reader = new Scanner(System.in);
        String arg;
        arg = reader.next();
        String aux;
        if ( (aux = CtrlDades.guardaHorari(h, arg)) != null) {
            System.out.println("INFO: s'ha guardat l'horari en " + aux);
        }*/

        System.out.println("GH: introdueix el nom del fitxer en el que es guardarà l'horari");
        Scanner reader = new Scanner(System.in);
        String arg;
        arg = reader.next();
        guardaHorari(id, arg);
    }

    public void guardaHorari(String id, String path){
        PlaEstudis plaEstudis = getPlaEstudi(id);
        Map<String, Aula> aules = ControladorAules.getAules();
        struct s = new struct(plaEstudis, aules);
        try{
            Serialitzador.guarda(s, path);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void carregaHorari(String path){
        try{
            struct s = Serialitzador.carrega(path);
            ControladorAules.Aulari = s.aules;
            ControladorPlaEstudis.ConjuntPE.clear();
            ControladorPlaEstudis.ConjuntPE.add(s.pla);
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Imprimeix per pantalla els horaris que tenim guardats i permet que l'usuari en trii un i l'imprimeixi.
     * A mes a mes, carrega el context corresponent a aquest horari.
     */
    public String visualitzaHorari() {
        System.out.print("INFO: tens guardats els següents horaris:\n");
        CtrlDades.mostraFitxersHoraris();
        System.out.print("INFO: indica l'horari que vols visualitzar:");
        Scanner reader = new Scanner(System.in);
        String arg;
        arg = reader.next();
        return CtrlDades.visualitzaHorari(arg);
        //pot ser que retornem una path per a carregar una escena, o el nom de un pe, que estara a la carpeta per defecte
    }

    /**
     * L'usuari introdueix una ruta de l'escena i es carreguen el seu pla d'estudis i el seu aulari.
     * @param dir Ruta del directori de la escena.
     */
    public void carregaEscena(String dir) {
        System.out.println(dir);
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

    /**
     * Ens permet modificar una entrada del horari generat per un pla d'estudi per una d'altre (sempre que les restriccions ho permetin).
     * @param id Identificador del pla d'estudi
     */
    public void modificaEntrada (String id) {
        PlaEstudis pe = getPlaEstudi(id);
        if (pe.hasHorari()) {
            Scanner reader = new Scanner(System.in);

            System.out.println("Modifica una entrada del horari per una d'altre: ");
            System.out.println("Introdueix el nom de la assignatura, el grup, el dia i la hora");
            String assig = reader.next();
            String grup = reader.next();
            DiaSetmana diaSetmana = DiaSetmana.string_To_DiaSetmana(reader.next());
            int hora = reader.nextInt();

            System.out.println("Introdueix el nou dia, hora i identificador de l'aula ");
            DiaSetmana diaNou = DiaSetmana.string_To_DiaSetmana(reader.next());
            int horaNova = reader.nextInt();
            String aulaNova = reader.next();

            boolean result = pe.modificaEntrada(assig, grup, diaSetmana, hora, diaNou, horaNova, aulaNova);
            if (result) System.out.println("S'ha modificat la entrada del horari indicada");
            else System.out.println("No hem pogut modifica la entrada a causa que incompleix alguna restriccio");
        }

        else
            System.out.println("Aquest pla d'estudis encara no conte cap horari");
    }

    /**
     * Afegeix aquest path al controlador de pla d'estudi.
     * @param path Path per a trobar una escena donada.
     */
    public void afegirPath (String path) {
        this.path = path;   //ens guardem el path per anar a l'escenari concret
    }


    public boolean generaHorari(String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.generaHorari(ControladorAules.getAules());
    }


    public LinkedList<LinkedList<Queue<String>>> getHorariSencer(String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getHorariSencer();
    }
}
