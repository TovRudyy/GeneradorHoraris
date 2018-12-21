package domain;


import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controlador del pla d'estudis que permet tant llegir-lo com modificar-lo.
 * @author Olek
 */

public class ControladorPlaEstudis {

    static ArrayList<PlaEstudis> ConjuntPE;
    static final String EscenaPE = "/PlaEstudi.json";
    static String path = "";    //aquest path ens indica el ultim path guardat fins al moment

    /**
     * Crea un nou ControladorPlaEstudis amb les dades que llegeix.
     */
    public ControladorPlaEstudis() {
        ConjuntPE = ControladorDades.llegeixDadesPE();
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
        ArrayList<assignatura> noves = new ArrayList<>();//ControladorDades.llegeixAssignatura(arg);
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
        ConjuntPE = ControladorDades.llegeixDadesPE();
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
     * Mostra si existeix un pla d'estudis amb el identificador id
     * @param id
     * @return True si cert, false altrament.
     */
    public boolean existsHorariPlaEstudi(String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.existsHorari();
    }


    public void borrarPlansEstudis() {
        ConjuntPE.clear();
    }

    public void carregarFitxerPlaEstudis(String absolutePath) {
        try {
            ConjuntPE.addAll(ControladorDades.llegeixPE(absolutePath));
        }catch (IOException | ClassNotFoundException | Aula_Exception | ParseException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public boolean existsPlaEstudi(String pe) {
        return (getPlaEstudi(pe) != null);
    }

    /**
     * Mostra si existeix una assignatura amb el identificador assig del pla d'estudis id
     * @param id Identificador pla d'estudis
     * @param assig Identificador de la assignatura.
     * @return True si cert, false altrament.
     */
    public boolean existsAssignaturaPE(String id, String assig) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.hasAssignatura(assig);
    }

    /**
     * Esborra la assignatura de un pla d'estudis.
     * @param id Identificador del pla d'estudis
     * @param assig Identificador de la assignatura.
     */
    public void esborrarAssignaturaPE(String id, String assig) {
        PlaEstudis pe = getPlaEstudi(id);
        pe.removeAssig(assig);
    }

    /**
     * @param id Id del pla
     * @param assig Id de la assignatura
     * @param grup Id del grup
     * @return True si existeix un grup "grup" en una assignatura assig dins del pla d'estudis indicat.
     */
    public boolean existsGrupAssignatura(String id, String assig, String grup) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.existsGrupAssignatura(assig, grup);
    }

    /**
     * Esborra un grup d'una assignatura concreta d'un pla d'estudis.
     * @param id
     * @param assig
     * @param grup
     */
    public void esborrarGrupAssignatura(String id, String assig, String grup) {
        PlaEstudis pe = getPlaEstudi(id);
        pe.esborrarGrupAssignatura(assig, grup);
    }

    /**
     * Modifica el nom d'una assignatura
     * @param id Id del pla d'estudis
     * @param assig Nom assignatura
     * @param newValue Nou nom
     */
    public void setNomAssignatrua(String id, String assig, String newValue) {
        PlaEstudis pe = getPlaEstudi(id);
        pe.setNomAssigantura(assig, newValue);
    }

    /**
     * Modifica el nivell d'una assignatura
     * @param id Identificador del pla d'estudis
     * @param assig Identificador assignatura
     * @param nivell Nou nivell
     */
    public void setNivellAssignatura(String id, String assig, int nivell) {
        PlaEstudis pe = getPlaEstudi(id);
        pe.setNivellAssigantura(assig, nivell);
    }

    /**
     * Modifica el numero de classes de teoria d'una assignatura d'un pla d'estudis.
     * @param id Identificador pla d'estudis
     * @param assig Nom de la assignatura
     * @param qt Nova quantitat de classes
     */
    public void setQtClassesTeoriaAssignatura(String id, String assig, int qt) {
        PlaEstudis pe = getPlaEstudi(id);
        pe.setQtClassesTeoriaAssigantura(assig, qt);
    }

    /**
     * Modifica la durada de les classes de teoria d'una assignatura d'un pla d'estudis.
     * @param id Identificador pla d'estudis
     * @param assig Nom de la assignatura
     * @param qt Nova durada de les classes
     */
    public void setDuradaClassesTeoriaAssignatura(String id, String assig, int qt) {
        PlaEstudis pe = getPlaEstudi(id);
        pe.setDuradaClassesTeoriaAssigantura(assig, qt);
    }

    /**
     * Modifica el numero de classes de problemes d'una assignatura d'un pla d'estudis.
     * @param id Identificador pla d'estudis
     * @param assig Nom de la assignatura
     * @param qt Nova quantitat de classes
     */
    public void setQtClassesProblemesAssignatura(String id, String assig, int qt) {
        PlaEstudis pe = getPlaEstudi(id);
        pe.setQtClassesProblemesAssigantura(assig, qt);
    }

    /**
     * Modifica la durada de les classes de problemes d'una assignatura d'un pla d'estudis.
     * @param id Identificador pla d'estudis
     * @param assig Nom de la assignatura
     * @param qt Nova durada de les classes
     */
    public void setDuradaClassesProblemesAssigantura(String id, String assig, int qt) {
        PlaEstudis pe = getPlaEstudi(id);
        pe.setDuradaClassesProblemesAssigantura(assig, qt);
    }

    /**
     * Modifica el numero de classes de laboratori d'una assignatura d'un pla d'estudis.
     * @param id Identificador pla d'estudis
     * @param assig Nom de la assignatura
     * @param qt Nova quantitat de classes
     */
    public void setQtClassesLaboratoriAssigantura(String id, String assig, int qt) {
        PlaEstudis pe = getPlaEstudi(id);
        pe.setQtClassesLaboratoriAssigantura(assig, qt);
    }

    /**
     * Modifica la durada de les classes de laboratori d'una assignatura d'un pla d'estudis.
     * @param id Identificador pla d'estudis
     * @param assig Nom de la assignatura
     * @param qt Nova durada de les classes
     */
    public void setDuradaClassesLaboratoriAssigantura(String id, String assig, int qt) {
        PlaEstudis pe = getPlaEstudi(id);
        pe.setDuradaClassesLaboratoriAssigantura(assig, qt);
    }

    /**
     * Guarda el horari del pla d'estudis amb el identificador donat.
     * @param id Identificador del pla d'estudis.
     */
    public void guardaHorari(String id) {
        System.out.println("GH: introdueix el nom del fitxer en el que es guardarà l'horari");
        Scanner reader = new Scanner(System.in);
        String arg;
        arg = reader.next();
        guardaHorari(id, arg);
    }

    /**
     * Guarda el horari del pla d'estudis amb el identificador donat a un path.
     * @param id Identificador del pla d'estudis.
     * @param path Path per guardar el horari
     */
    public void guardaHorari(String id, String path){
        PlaEstudis plaEstudis = getPlaEstudi(id);
        Map<String, Aula> aules = ControladorAules.getAules();
        try{
            ControladorDades.guarda(path, plaEstudis, aules);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Carrega el horari de un path donat
     * @param path path de on buscar el horari.
     */
    public void carregaHorari(String path){
        try{
            Object obj[] = (Object[])  ControladorDades.carrega(path);
            ControladorAules.Aulari = (Map<String, Aula>) obj[1];
            ControladorPlaEstudis.ConjuntPE.clear();
            ControladorPlaEstudis.ConjuntPE.add((PlaEstudis) obj[0]);
        }catch(IOException | ClassNotFoundException | ClassCastException e){
            e.printStackTrace();
        }
    }

    /**
     * Imprimeix per pantalla els horaris que tenim guardats i permet que l'usuari en trii un i l'imprimeixi.
     * A mes a mes, carrega el context corresponent a aquest horari.
     */
    public String visualitzaHorari() {
        System.out.print("INFO: tens guardats els següents horaris:\n");
        //ControladorDades.mostraFitxersHoraris();
        System.out.print("INFO: indica l'horari que vols visualitzar:");
        Scanner reader = new Scanner(System.in);
        String arg;
        arg = reader.next();
        return "Hey";  //ControladorDades.visualitzaHorari(arg);
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
        try {
            ConjuntPE.addAll(ControladorDades.llegeixPE(file));
        }catch (IOException | ClassNotFoundException | Aula_Exception | ParseException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param assig Identificador de la asignatura.
     * @param id Identificador del pla d'estudis
     * @return Nom de la assignatura
     */
    public String getNomAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getNomAssignatura(assig);
    }

    /**
     * @param assig Identificador de la asignatura.
     * @param id Identificador del pla d'estudis
     * @return Nivell de la assignatura
     */
    public int getNivellAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getNivellAssignatura(assig);
    }

    /**
     * @param assig Identificador de la asignatura.
     * @param id Identificador del pla d'estudis
     * @return Numero de classes de teoria de la assignatura
     */
    public int getQtClassesTeoriaAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getQtClassesTeoriaAssignatura(assig);
    }

    /**
     * @param assig Identificador de la asignatura.
     * @param id Identificador del pla d'estudis
     * @return Durada de classes de teoria de la assignatura
     */
    public int getDuradaClassesTeoriaAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getDuradaClassesTeoriaAssignatura(assig);
    }

    /**
     * @param assig Identificador de la asignatura.
     * @param id Identificador del pla d'estudis
     * @return Numero de classes de problemes de la assignatura
     */
    public int getQtClassesProblemesAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getQtClassesProblemesAssignatura(assig);
    }

    /**
     * @param assig Identificador de la asignatura.
     * @param id Identificador del pla d'estudis
     * @return Durada de classes de teoria de la assignatura
     */
    public int getDuradaClassesProblemesAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getDuradaClassesProblemesAssignatura(assig);
    }

    /**
     * @param assig Identificador de la asignatura.
     * @param id Identificador del pla d'estudis
     * @return Numero de classes de laboratori de la assignatura
     */
    public int getQtClassesLaboratoriAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getQtClassesLaboratoriAssignatura(assig);
    }

    /**
     * @param assig Identificador de la asignatura.
     * @param id Identificador del pla d'estudis
     * @return Durada de classes de teoria de la assignatura
     */
    public int getDuradaClassesLaboratoriAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getDuradaClassesLaboratoriAssignatura(assig);
    }

    /**
     * Obte els grup d'una assignatura donada.
     * @param assig Identificador de la assignatura
     * @param id Identificador del pla
     * @return Una arrayList amb els grups.
     */
    public ArrayList<String> getGrupsAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getGrupsAssignatura(assig);
    }

    /**
     * @param grup Identificador del grup
     * @param assig Identificador de la assignatura
     * @param id Identificador del pla d'estudis
     * @return La seva capacitat
     */
    public int getCapacitatGrup(String grup, String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getCapacitatGrup(grup, assig);
    }

    /**
     * @param grup Identificador del grup
     * @param assig Identificador de la assignatura
     * @param id Identificador del pla d'estudis
     * @return El horari del grup.
     */
    public String getHorariGrup(String grup, String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getHorariGrup(grup, assig);
    }

    /**
     * @param grup Identificador del grup
     * @param assig Identificador de la assignatura
     * @param id Identificador del pla d'estudis
     * @return El tipus d'aula que necessita.
     */
    public String getTipusAulaGrup(String grup, String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getTipusAulaGrup(grup, assig);
    }

    /**
     * @param grup Identificador del grup
     * @param assig Identificador de la assignatura
     * @param id Identificador del pla d'estudis
     * @return La arrayList amb els subgrups del grup.
     */
    public ArrayList<String> getSubgrupsGrup(String grup, String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getSubgrupsGrup(grup, assig);
    }

    /**
     * @param assig Identificador de la assignatura
     * @param id Identificador del pla d'estudis
     * @return Conjunt de correquisits de la assignatura.
     */
    public ArrayList<String> getCorrequisitsAssignatura(String assig, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getCorrequisitsAssignatura(assig);
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
     * Ens permet modificar el horari obtingut
     * @param plaEstudi Identificador del horari
     * @param assig Id de la assignatura
     * @param grup Grup
     * @param dia Dia
     * @param hora Hora
     * @param newDia Nou dia
     * @param newHora Nova hora
     * @param aula Nova aula
     * @return Retorna true si s'ha pogut realitzar la operacio o no.
     */
    public boolean modificaHorari (String plaEstudi, String assig, String grup, String dia, int hora, String newDia, int newHora, String aula) {
        PlaEstudis pe = getPlaEstudi(plaEstudi);
        if (pe.hasHorari()) {

            DiaSetmana diaSetmana = DiaSetmana.string_To_DiaSetmana(dia);

            DiaSetmana diaNou = DiaSetmana.string_To_DiaSetmana(newDia);

            System.err.println("DEBUG:"+plaEstudi+" "+assig+" "+grup+" "+aula+" "+diaSetmana+" "+hora+" "+diaNou+" "+newHora);
            boolean result = pe.modificaEntrada(assig, grup, diaSetmana, hora, diaNou, newHora, aula);
            if (result) {
                System.err.println("DEBUG: S'ha modificat la entrada del horari indicada");
                return true;
            }
            else {
                System.err.println("DEBUG: No hem pogut modifica la entrada a causa que incompleix alguna restriccio");
                return false;
            }
        }
        else {
            System.out.println("Aquest pla d'estudis encara no conte cap horari");
            return false;
        }
    }

     /**
     * Afegeix aquest path al controlador de pla d'estudi.
     * @param path Path per a trobar una escena donada.
     */
    public void afegirPath (String path) {
        this.path = path;   //ens guardem el path per anar a l'escenari concret
    }


    /**
     * Genera el horari del pla d'estudis
     * @param id Identificador del pla.
     * @return True si l'hem generat o false altrament.
     */
    public boolean generaHorari(String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.generaHorari(ControladorAules.getAules());
    }


    /**
     * @param id Identificador del pla d'estudis.
     * @return Una matriu amb tot el contingut del horari
     */
    public LinkedList<LinkedList<Queue<String>>> getHorariSencer(String id) {
        PlaEstudis pe = getPlaEstudi(id);
        return pe.getHorariSencer();
    }

    /**
     * Exporta el horari a format txt.
     * @param path Path a on guardarlo
     * @param id Identificador del pla d'estudis.
     */
    public void exportarHorariTXT(String path, String id) {
        PlaEstudis pe = getPlaEstudi(id);
        String h = pe.toStringSencer();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        if (path.equals(""))
            h =  pe.getID() + "\n" + "Data creació: " + strDate + "\n" +
                    "L'identificador del pla d'estudis es : " + id + "\n\n" + h;
        else
            h =  pe.getID() + "\n" + "Data creació: " + strDate + "\n" +
                    "El path de l'escenari corresponent es : " + path + "\n\n" + h;

        String aux;
        if ( (aux = ControladorDades.guardaHorariGUI(h, path)) != null) {
            System.out.println("INFO: s'ha guardat l'horari en " + aux);
        }
    }


    //CAL FICAR EL CAS APART EN QUE EL GRUP SIGUI DE TEORIA, PEL QUE HEM DE REPERTIR TOTS ELS QUE HEM AFEGIT EN ELS SEUS SUBGRUPS.

    /**
     * Modifica la capacitat de un grup
     * @param id Identificador del pla
     * @param assig Idetificador de la assig
     * @param grup Identificador del grup
     * @param qt Nova quantitat
     */
    public void setCapacitatGrupAssignatura(String id, String assig, String grup, int qt) {
        PlaEstudis pe = getPlaEstudi(id);

        if (grup.charAt(grup.length()-1) == '0')
        {
            int previous = pe.getCapacitatGrup(grup, assig);
            pe.recalculaCapacitatSubgrups(assig, grup, (qt - previous));
        }

        pe.setCapacitatGrupAssignatura(assig, grup, qt);
        pe.recalculaCapacitat(assig);
    }


    /**
     * Modifica el horar de un grup concret
     * @param id Identificador del pla
     * @param assig Identificador de la assignatura
     * @param grup Id del grup.
     * @param valor Nou valor del horari
     */
    public void setHorariGrupAssignatura(String id, String assig, String grup, String valor) {
        PlaEstudis pe = getPlaEstudi(id);
        pe.setHorariGrupAssignatura(assig, grup, valor);
    }


    public void setTipusGrupAssignatura(String id, String assig, String grup, String newValue) {
        PlaEstudis pe = getPlaEstudi(id);
        try {
            pe.setTipusGrupAssignatura(assig, grup, Tipus_Aula.string_to_Tipus_Aula(newValue));
        } catch (Aula_Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Afegeix una assignatura al pla d'estudi, sempre que el identificador no estigui repetit.
     * @param plaEstudi
     * @param id_assig
     * @param nom
     * @param nivell
     * @param n_classes_T
     * @param dur_T
     * @param n_classes_P
     * @param dur_P
     * @param n_classesL
     * @param dur_L
     */
    public void addAssignatura (String plaEstudi, String id_assig, String nom, int nivell, int n_classes_T, int dur_T, int n_classes_P, int dur_P, int n_classesL, int dur_L)
    {
        assignatura a = new assignatura (id_assig, nom, nivell);
        a.setClasses(n_classes_T, dur_T, n_classes_P, dur_P, n_classesL, dur_L);

        PlaEstudis pe = getPlaEstudi(plaEstudi);
        pe.afegirAssignatura (a);

    }



    /**
     * @param plaEstudi
     * @param assig
     * @param nous_correquisits
     */
    public void afegirCorrequisit (String plaEstudi, String assig, ArrayList<String> nous_correquisits)
    {
        PlaEstudis pe = getPlaEstudi(plaEstudi);
        for (String n : nous_correquisits)
        {
            String[] a = {assig, n};
            pe.afegirCorrequisits(a);
        }
    }

    /**
     * Afegeix un grup
     * @param plaEstudi El pla d'estudis
     * @param assignatura Id de la assignatura
     * @param id Id del pla d'estudi.
     * @param capacitat
     * @param horariGrup
     * @param t
     */
    public void afegirGrup (String plaEstudi, String assignatura, String id, int capacitat, String horariGrup, Tipus_Aula t)
    {
        PlaEstudis pe = getPlaEstudi(plaEstudi);
        grup g = new grup (id, capacitat, horariGrup, t);
        pe.afegirGrupAssignatura (assignatura, g);

        //mirem si no es de teoria pel que ens cal recalcular les capacitats
        int x = Integer.parseInt(id);
        x = x%10;

        if (x != 0) //no es de teoria
            pe.recalculaCapacitat (assignatura);    //recalculem les capacitats dels grups de teoria d'aquestes assignatures.

    }

    /**
     * Elimina un correquisit
     * @param plaEstudi Id del pla d'estudis.
     * @param assignatura Id de la assignatura.
     * @param aEliminar Id del correquisit a eliminar
     */
    public void eliminarCorrequisit (String plaEstudi, String assignatura, ArrayList<String> aEliminar)
    {
        PlaEstudis pe = getPlaEstudi(plaEstudi);
        for (String n : aEliminar)
        {
            String[] a = {assignatura, n};
            pe.eliminarCorrequisit(a);
        }
    }

    /**
     * Guarda un pla d'estudis a un pla
     * @param path Path a on guardarlo.
     */
    public void guardarPlaEstudis(String path) {
        try {
            ControladorDades.guarda(this.ConjuntPE, path);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: error al guardar pla estudis!");
        }
    }

    /**
     * Obtenim les restriccions flexibles del pla
     * @param plaEstudi Identificador del pla
     * @return Una array amb totes les restriccions
     */
    public ArrayList<String> getRestriccionsFlexibles(String plaEstudi) {
        PlaEstudis pe = getPlaEstudi(plaEstudi);
        return pe.getRestriccionsFlexibles();
    }

    /**
     *
     * @param plaEstudi Identificador del pla d'estudis.
     * @return Una array amb totes les restriccions flexibles que estan actives
     */
    public ArrayList<String> getRestriccionsFlexiblesActives(String plaEstudi) {
        PlaEstudis pe = getPlaEstudi(plaEstudi);
        return pe.getRestriccionsFlexiblesActives();
    }

    /**
     * Elimina una restriccio
     * @param plaEstudi Id del pla d'estudis
     * @param restriccio Identificador de la restriccio
     */
    public void eliminarRestriccio(String plaEstudi, String restriccio) {
        PlaEstudis pe = getPlaEstudi(plaEstudi);
        pe.eliminarRestriccioFlexible(restriccio);
    }

    /**
     * @param plaEstudi Id del pla d'estudis
     * @param restriccio Identificador de la restriccio
     */
    public void suavitzarRestriccio(String plaEstudi, String restriccio) {
        PlaEstudis pe = getPlaEstudi(plaEstudi);
        pe.suavitzarRestriccioFlexible(restriccio);
    }

    /**
     * Reinicia les restriccions actives a totes les que existeixen.
     * @param plaEstudi Identificador del pla d'estudis.
     */
    public void reiniciarRestriccions(String plaEstudi) {
        PlaEstudis pe = getPlaEstudi(plaEstudi);
        pe.reiniciarRestriccions();
    }

    /**
     * Activa una restriccio.
     * @param plaEstudi Identificador del pla.
     * @param restriccio Identificador de la restriccio.
     */
    public void activarRestriccio(String plaEstudi, String restriccio) {
        PlaEstudis pe = getPlaEstudi(plaEstudi);
        pe.activarRestriccioFlexible(restriccio);
    }

    /**
     * Afegeix una restriccio negociable.
     * @param plaEstudi Identificador del pla d'estudis.
     * @param dades Informacio necessaria per guardar la restriccio de interval
     */
    public void afegirRestriccioNegociable(String plaEstudi, String[] dades) {
        PlaEstudis pe = getPlaEstudi(plaEstudi);
        pe.afegirRestriccioFlexible(dades);
    }
}
