package presentacio;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Controlador de la capa de presentacio mode GUI.
 * @author Oleksandr Rudyy
 */
public class CtrlPresentacio {
    static ControladorPlaEstudis CtrlPE;
    static ControladorAules CtrlAUS;
    ControladorUtils CtrlUtils;

    static ArrayList<AulaFX> aules;

    CtrlPlaEstudis PresentacioPE;

    public CtrlPresentacio() {
        this.CtrlPE = new ControladorPlaEstudis();
        this.CtrlAUS = new ControladorAules();
        this.CtrlUtils = new ControladorUtils();
        this.aules = new ArrayList<AulaFX>();

        populate_Aules(aules);
    }

    /**
     * Crea tantes instancies AulaFX com Aules hi hagin i les afegeix en l'ArrayList arr
     * @param arr ArrayList d'objectes AulaFX
     */
    private void populate_Aules(ArrayList<AulaFX> arr) {
        arr.clear();
        ArrayList<String> dades = CtrlAUS.getInfoAulari();
        for (String s : dades) {
            String[] camps = s.split(":");
            System.err.println("DEBUG: id_aula: " + camps[0] + " capacitat_aula: " + camps[1] + " tipus_aula: " + camps[2]);
            arr.add(new AulaFX(camps[0], Integer.parseInt(camps[1]), camps[2]));
        }
    }

    /**
     * @return un conjunt d'objectes AulaFX que representa totes les Aules carregades
     */
    public ObservableList<AulaFX> getDataAula() {
        populate_Aules(aules);
        ObservableList<AulaFX> ret = FXCollections.observableArrayList(aules);
        return ret;
    }

    /**
     *
     * @return L'identificador dels pla d'estudis carregats
     */
    public ArrayList<String> getPlans() {
        return CtrlPE.getInfoPlans();
    }

    /**
     *
     * @param id identificador d'un pla d'estudis
     * @return els identificadors de totes les assignatures pertanyents al pla d'estudi
     */
    public ArrayList<String> getAssignatures(String id) {
        return CtrlPE.getAssignatures(id);
    }

    /**
     *
     * @param assig identificador de l'assignatura
     * @param pla identificador del pla d'estudi
     * @return el nom de l'assignatura
     */
    public String getNomAssignatura(String assig, String pla) {
        return CtrlPE.getNomAssignatura(assig, pla);
    }

    /**
     *
     * @param assig identificador de l'assignatura
     * @param pla identificador del pla d'estudi
     * @return el nivell de l'assignatura
     */
    public int getNivellAssignatura(String assig, String pla) {
        return CtrlPE.getNivellAssignatura(assig, pla);

    }

    /**
     *
     * @param assig identificador de l'assignatura
     * @param pla identificador del pla d'estudi
     * @return la quantitat de classes de teoria que imparteix l'assignatura a la setmana
     */
    public int getQtClassesTeoriaAssignatura(String assig, String pla) {
        return CtrlPE.getQtClassesTeoriaAssignatura(assig, pla);

    }

    /**
     *
     * @param assig identificador de l'assignatura
     * @param pla identificador del pla d'estudi
     * @return la durada de les classes de teoria que imparteix l'assignatura a la setmana
     */
    public int getDuradaClassesTeoriaAssignatura(String assig, String pla) {
        return CtrlPE.getDuradaClassesTeoriaAssignatura(assig, pla);

    }

    /**
     * @param assig identificador de l'assignatura
     * @param pla identificador del pla d'estudi
     * @return la quantitat de classes de problemes que imparteix l'assignatura a la setmana
     */
    public int getQtClassesProblemesAssignatura(String assig, String pla) {
        return CtrlPE.getQtClassesProblemesAssignatura(assig, pla);

    }

    /**
     * @param assig identificador de l'assignatura
     * @param pla identificador del pla d'estudi
     * @return la durada de les classes de problemes que imparteix l'assignatura a la setmana
     */
    public int getDuradaClassesProblemesAssignatura(String assig, String pla) {
        return CtrlPE.getDuradaClassesProblemesAssignatura(assig, pla);

    }

    /**
     * @param assig identificador de l'assignatura
     * @param pla identificador del pla d'estudi
     * @return la quantitat de classes de laboratori que imparteix l'assignatura a la setmana
     */
    public int getQtClassesLaboratoriAssignatura(String assig, String pla) {
        return CtrlPE.getQtClassesLaboratoriAssignatura(assig, pla);

    }

    /**
     * @param assig identificador de l'assignatura
     * @param pla identificador del pla d'estudi
     * @return la durada de les classes de laboratori que imparteix l'assignatura a la setmana
     */
    public int getDuradaClassesLaboratoriAssignatura(String assig, String pla) {
        return CtrlPE.getDuradaClassesLaboratoriAssignatura(assig, pla);
    }

    /**
     *
     * @param assig identificador de l'assignatura
     * @param pla identificador del pla d'estudi
     * @return els identificadors dels grups que conté l'assignatura
     */
    public ArrayList<String> getGrupsAssignatura(String assig, String pla) {
        return CtrlPE.getGrupsAssignatura(assig, pla);
    }

    /**
     *
     * @param grup l'identificador d'un grup
     * @param assig identificador d'una assignatura
     * @param pla identificador d'un pla d'estudi
     * @return capacitat del grup
     */
    public int getCapacitatGrup(String grup, String assig, String pla) {
        return CtrlPE.getCapacitatGrup(grup, assig, pla);
    }

    /**
     *
     * @param grup l'identificador d'un grup
     * @param assig identificador d'una assignatura
     * @param pla identificador d'un pla d'estudi
     * @return horari del grup
     */
    public String getHorariGrup(String grup, String assig, String pla) {
        return CtrlPE.getHorariGrup(grup, assig, pla);

    }


    /**
     *
     * @param grup l'identificador d'un grup
     * @param assig identificador d'una assignatura
     * @param pla identificador d'un pla d'estudi
     * @return el tipus d'aula que necessita el grup
     */
    public String getTipusAulaGrup(String grup, String assig, String pla) {
        return CtrlPE.getTipusAulaGrup(grup, assig, pla);
    }

    /**
     *
     * @param grup l'identificador d'un grup
     * @param assig identificador d'una assignatura
     * @param pla identificador d'un pla d'estudi
     * @return els identificadors dels subgrups de grup
     */
    public ArrayList<String> getSubgrupsGrup(String grup, String assig, String pla) {
        return CtrlPE.getSubgrupsGrup(grup, assig, pla);

    }

    /**
     *
     * @param assig identificador d'una assignatura
     * @param pla identificador d'un pla d'estudo
     * @return el nom de les assignatures que son correquisit
     */
    public ArrayList<String> getCorrequisitsAssignatura(String assig, String pla) {
        return CtrlPE.getCorrequisitsAssignatura(assig, pla);
    }

    /**
     *
     * @param id identificador d'una aula
     * @param nou noca capacitat de l'aula
     */
    public void setCapacitatAula(String id, int nou) {
        CtrlAUS.setCapacitatAula(id, nou);
    }

    /**
     *
     * @param id identificador d'una aula
     * @param newValue nou identificador de l'aula
     * @return cert si s'ha pogut modificar l'identificador
     */
    public boolean setIdentificadorAula(String id, String newValue) {
        return CtrlAUS.setIdentificadorAula(id, newValue);
    }

    /**
     *
     * @param id identificador d'una aula
     * @param tipus nou tipus d'una aula
     */
    public void setTipusAula(String id, String tipus) {
        CtrlAUS.setTipusAula(id, tipus);
    }

    /**
     *
     * @param id identificador de l'aula
     * @param capacitat capacitat de l'aula
     * @param tipus tipus de l'aula
     * @return cert si s'ha pogut crear l'aula
     */
    public boolean afegirAula(String id, int capacitat, String tipus) {
        return CtrlAUS.afegirAula(id, capacitat, tipus);
    }

    /**
     *
     * @param id identificador de l'aula
     */
    public void removeAula(String id) {
        CtrlAUS.removeAula(id);
    }

    /**
     * Borra totes les aules carregades en memoria
     */
    public void borrarAulari() {
        CtrlAUS.borrarAulari();
    }

    /**
     * Carrega en memoria totes les aules contingudes en un fitxer
     * @param fitxer ruta cap a un fitxer
     */
    public void CarregarFitxerAules(String fitxer) {
        CtrlAUS.carregarFitxerAules(fitxer);
    }

    /**
     * Borra tots els plans d'estudis carregats en memoria
     */
    public void borrarPlansEstudis() {
        CtrlPE.borrarPlansEstudis();
    }

    /**
     * Carrega en memoria tots els pla d'estudis continuts en un fitxer
     * @param fitxer ruta cap a un fitxer
     */
    public void CarregarFitxerPlaEstudis(String fitxer) {
        CtrlPE.carregarFitxerPlaEstudis(fitxer);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @return retorna cert si el pla d'estudis existeix
     */
    public boolean existsPlaEstudi(String pe) {
        return CtrlPE.existsPlaEstudi(pe);
    }

    /**
     *
     * @return el conjunt de dies de la setmana amb que treballa l'aplicacio
     */
    public ArrayList<String> getDiesSetmana() {
        return CtrlUtils.getDiesSetmana();
    }

    /**
     *
     * @return el conjunt d'hores amb que treballa l'aplicacio
     */
    public ArrayList<String> getHoresHorari() {
        return CtrlUtils.getHoresHorari();
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @return cert si s'ha pogut generar un horari
     */
    public boolean generaHorari(String pe) {
        return CtrlPE.generaHorari(pe);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @return una LinkedList representant l'horari del pla d'estudis per dies i hores. Null si pla d'estudis no te cap horari generat
     */
    public LinkedList<LinkedList<Queue<String>>> getHorariSencer(String pe) {
        return CtrlPE.getHorariSencer(pe);
    }


    /**
     *
     * @param absolutePath ruta cap al fiter on guardar l'horari en format txt
     * @param pe identificador d'un pla d'estudis
     */
    public void exportarHorariTXT(String absolutePath, String pe) {
        CtrlPE.exportarHorariTXT(absolutePath, pe);
    }


    /**
     * Guarda un horari i tot el seu contexte (aulai, plans d'estudis) en un fitxer binari
     * @param plaEstudi identificador d'un pla d'estudi
     * @param absolutePath ruta cap al fitxer on guardar les dades
     */
    public void guardarHorariSencer(String plaEstudi, String absolutePath) {
        CtrlPE.guardaHorari(plaEstudi, absolutePath);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @return cert si el pla d'estudis conté un horari valid
     */
    public boolean existsHorariPlaEstudi(String pe) {
        return CtrlPE.existsHorariPlaEstudi(pe);
    }

    /**
     * Carrega en memoria un horari i el seu contexte (aulari i plans d'estudis)
     * @param absolutePath ruta cap al fitxer on esta emmagatzemat l'horari
     */
    public void carregaFitxerHorari(String absolutePath) {
        CtrlPE.carregaHorari(absolutePath);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @return cert si el pla d'estudis conte l'assignatura
     */
    public boolean existsAssignaturaPE(String pe, String assig) {
        return CtrlPE.existsAssignaturaPE(pe, assig);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     */
    public void esborrarAssignaturaPE(String pe, String assig) {
        CtrlPE.esborrarAssignaturaPE(pe, assig);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param grup identificador d'un grup
     * @return cert si existeix el grup en l'assignatura del pla d'estudis
     */
    public boolean existsGrupAssignatura(String pe, String assig, String grup) {
        return CtrlPE.existsGrupAssignatura(pe, assig, grup);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param grup identificador d'un grup
     */
    public void esborrarGrupAssignatura(String pe, String assig, String grup) {
        CtrlPE.esborrarGrupAssignatura(pe, assig, grup);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param newValue nou nom de l'assignatura
     */
    public void setNomAssignatura(String pe, String assig, String newValue) {
        CtrlPE.setNomAssignatrua(pe, assig, newValue);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param nivell nou nivell de l'assignatura
     */
    public void setNivellAssignatura(String pe, String assig, int nivell) {
        CtrlPE.setNivellAssignatura(pe, assig, nivell);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param qt nova quantitat de classes de teoria de l'assignatura
     */
    public void setQtClassesTeoriaAssignatura(String pe, String assig, int qt) {
        CtrlPE.setQtClassesTeoriaAssignatura(pe, assig, qt);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param qt nova durada de les classes de teoria de l'assignatura
     */
    public void setDuradaClassesTeoriaAssignatura(String pe, String assig, int qt) {
        CtrlPE.setDuradaClassesTeoriaAssignatura(pe, assig, qt);

    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param qt nova quantitat de classes de problemes de l'assignatura
     */
    public void setQtClassesProblemesAssignatura(String pe, String assig, int qt) {
        CtrlPE.setQtClassesProblemesAssignatura(pe, assig, qt);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param qt nova durada de les classes de problemes de l'assignatura
     */
    public void setDuradaClassesProblemesAssignatura(String pe, String assig, int qt) {
        CtrlPE.setDuradaClassesProblemesAssigantura(pe, assig, qt);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param qt nova quantitat de classes de laboratori de l'assignatura
     */
    public void setQtClassesLaboratoriAssignatura(String pe, String assig, int qt) {
        CtrlPE.setQtClassesLaboratoriAssigantura(pe, assig, qt);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param qt nova durada de les classes de laboratori de l'assignatura
     */
    public void setDuradaClassesLaboratoriAssignatura(String pe, String assig, int qt) {
        CtrlPE.setDuradaClassesLaboratoriAssigantura(pe, assig, qt);
    }


    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param grup identificador d'un grup
     * @param qt nova capacitat d'un grup
     */
    public void setCapacitatGrupAssignatura(String pe, String assig, String grup, int qt) {
        CtrlPE.setCapacitatGrupAssignatura(pe, assig, grup, qt);
    }

    /**
     *
     * @param pe identificador d'un grup
     * @param assig identificador d'una assignatura
     * @param grup identificador d'un grup
     * @param valor nou horari del grup
     */
    public void setHorariGrupAssignatura(String pe, String assig, String grup, String valor) {
        CtrlPE.setHorariGrupAssignatura(pe, assig, grup, valor);
    }

    /**
     *
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param grup identificador d'un grup
     * @param newValue nou tipus del grup
     */
    public void setTipusGrupAssignatura(String pe, String assig, String grup, String newValue) {
        CtrlPE.setTipusGrupAssignatura(pe, assig, grup, newValue);
    }

    /**
     *
     * @param plaEstudi identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     * @param grup identificador d'un grup
     * @param oldDia dia original de la classe
     * @param oldHora hora original de la classe
     * @param newDia dia nou de la classe
     * @param newHora hora nova de la classe
     * @param aula aula on s'impartira la classe
     * @return cert si la modificacio del dia i hora de la classe no infringeix cap restriccio
     */
    public boolean modificarHorari(String plaEstudi, String assig, String grup, String oldDia, int oldHora, String newDia, int newHora, String aula) {
        return CtrlPE.modificaHorari(plaEstudi,assig,grup,oldDia,oldHora,newDia,newHora,aula);
    }

    /**
     *
     * @param plaEstudi identificador d'un pla d'estudis
     * @param id_assig identificador de l'assignatura
     * @param nom nom de l'assignatura
     * @param nivell nivell de l'assignatura
     * @param n_classes_T nombre de classes de teoria de l'assignatura
     * @param dur_T duracio de les classes de teoria de l'assignatura
     * @param n_classes_P nombre de classes de problemes de l'assignatura
     * @param dur_P duracio de les classes de problemes de l'assignatura
     * @param n_classesL nombre de classes de laboratori de l'assignatura
     * @param dur_L duracio de les classes de laboratori de l'assignatura
     */
    public void afegirAssignatura (String plaEstudi, String id_assig, String nom, int nivell, int n_classes_T, int dur_T, int n_classes_P, int dur_P, int n_classesL, int dur_L)
    {
        CtrlPE.addAssignatura(plaEstudi, id_assig, nom, nivell, n_classes_T, dur_T, n_classes_P, dur_P, n_classesL, dur_L);
    }

    /**
     *
     * @param plaEstudi identificador d'un pla d'estudi
     * @param assig identificador d'una assignatura
     * @param nous_correquisits identificadors de les assignatares que definir com a correquisit
     */
    public void afegirCorrequisit (String plaEstudi, String assig, ArrayList<String> nous_correquisits)
    {
        CtrlPE.afegirCorrequisit(plaEstudi, assig, nous_correquisits);
    }


    /**
     *
     * @param plaEstudi identificador d'un pla d'estudis
     * @param assignatura identificador d'una assignatura
     * @param id identificador del grup
     * @param capacitat capcacitat del grup
     * @param horariGrup horari del grup
     * @param t tipus d'aula del gruo
     */
    public void afegirGrup (String plaEstudi, String assignatura, String id, int capacitat, String horariGrup, Tipus_Aula t)
    {
        CtrlPE.afegirGrup(plaEstudi, assignatura, id, capacitat, horariGrup, t);

    }

    /**
     *
     * @param plaEstudi identificador d'un pla d'estudis
     * @param assignatura identificador d'una assignatura
     * @param aEliminar identificador de l'aassignatura a eliminar dels correquisits
     */
    public void eliminarCorrequisit (String plaEstudi, String assignatura, ArrayList<String> aEliminar) {
        CtrlPE.eliminarCorrequisit(plaEstudi, assignatura, aEliminar);
    }

    /**
     * Guarda l'aulari carregat en format binari en un fitxer
     * @param path ruta cap al fitxer on es vol guardar l'aulari carregat
     */
    public void guardarAulari(String path) {
        CtrlAUS.guardaAulari(path);
    }

    /**
     * Guarda els plans d'estudis carregats en format binari en un fitxer
     * @param path ruta cap al fitxer on es vol guardar els pla d'estudis carregats
     */
    public void guardarPlaEstudis(String path) {
        CtrlPE.guardarPlaEstudis(path);
    }

    /**
     *
     * @param plaEstudi identificador d'un pla d'estudi
     * @return un array de strings amb totes les restriccions flexibles
     */
    public ArrayList<String> getRestriccionsFlexibles(String plaEstudi) {
        return CtrlPE.getRestriccionsFlexibles(plaEstudi);
    }
}
