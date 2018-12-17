package presentacio;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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

    public ObservableList<AulaFX> getDataAula() {
        populate_Aules(aules);
        ObservableList<AulaFX> ret = FXCollections.observableArrayList(aules);
        return ret;
    }

    public ArrayList<String> getPlans() {
        return CtrlPE.getInfoPlans();
    }

    public ArrayList<String> getAssignatures(String id) {
        return CtrlPE.getAssignatures(id);
    }

    public String getNomAssignatura(String assig, String pla) {
        return CtrlPE.getNomAssignatura(assig, pla);
    }

    public int getNivellAssignatura(String assig, String pla) {
        return CtrlPE.getNivellAssignatura(assig, pla);

    }

    public int getQtClassesTeoriaAssignatura(String assig, String pla) {
        return CtrlPE.getQtClassesTeoriaAssignatura(assig, pla);

    }

    public int getDuradaClassesTeoriaAssignatura(String assig, String pla) {
        return CtrlPE.getDuradaClassesTeoriaAssignatura(assig, pla);

    }

    public int getQtClassesProblemesAssignatura(String assig, String pla) {
        return CtrlPE.getQtClassesProblemesAssignatura(assig, pla);

    }

    public int getDuradaClassesProblemesAssignatura(String assig, String pla) {
        return CtrlPE.getDuradaClassesProblemesAssignatura(assig, pla);

    }

    public int getQtClassesLaboratoriAssignatura(String assig, String pla) {
        return CtrlPE.getQtClassesLaboratoriAssignatura(assig, pla);

    }

    public int getDuradaClassesLaboratoriAssignatura(String assig, String pla) {
        return CtrlPE.getDuradaClassesLaboratoriAssignatura(assig, pla);
    }

    public ArrayList<String> getGrupsAssignatura(String assig, String pla) {
        return CtrlPE.getGrupsAssignatura(assig, pla);
    }

    public int getCapacitatGrup(String grup, String assig, String pla) {
        return CtrlPE.getCapacitatGrup(grup, assig, pla);
    }

    public String getHorariGrup(String grup, String assig, String pla) {
        return CtrlPE.getHorariGrup(grup, assig, pla);

    }

    public String getTipusAulaGrup(String grup, String assig, String pla) {
        return CtrlPE.getTipusAulaGrup(grup, assig, pla);
    }

    public ArrayList<String> getSubgrupsGrup(String grup, String assig, String pla) {
        return CtrlPE.getSubgrupsGrup(grup, assig, pla);

    }

    public ArrayList<String> getCorrequisitsAssignatura(String assig, String pla) {
        return CtrlPE.getCorrequisitsAssignatura(assig, pla);
    }

    public void setCapacitatAula(String id, int nou) {
        CtrlAUS.setCapacitatAula(id, nou);
    }

    public boolean setIdentificadorAula(String id, String newValue) {
        return CtrlAUS.setIdentificadorAula(id, newValue);
    }

    public void setTipusAula(String id, String tipus) {
        CtrlAUS.setTipusAula(id, tipus);
    }

    public boolean afegirAula(String id, int capacitat, String tipus) {
        return CtrlAUS.afegirAula(id, capacitat, tipus);
    }

    public void removeAula(String id) {
        CtrlAUS.removeAula(id);
    }


    public void borrarAulari() {
        CtrlAUS.borrarAulari();
    }


    public void CarregarFitxerAules(String fitxer) {
        CtrlAUS.carregarFitxerAules(fitxer);
    }


    public void borrarPlansEstudis() {
        CtrlPE.borrarPlansEstudis();
    }


    public void CarregarFitxerPlaEstudis(String fitxer) {
        CtrlPE.carregarFitxerPlaEstudis(fitxer);
    }


    public boolean existsPlaEstudi(String pe) {
        return CtrlPE.existsPlaEstudi(pe);
    }


    public ArrayList<String> getDiesSetmana() {
        return CtrlUtils.getDiesSetmana();
    }


    public ArrayList<String> getHoresHorari() {
        return CtrlUtils.getHoresHorari();
    }


    public boolean generaHorari(String pe) {
        return CtrlPE.generaHorari(pe);
    }


    public LinkedList<LinkedList<Queue<String>>> getHorariSencer(String pe) {
        return CtrlPE.getHorariSencer(pe);
    }


    public void exportarHorariTXT(String absolutePath, String pe) {
        CtrlPE.exportarHorariTXT(absolutePath, pe);
    }


    public void guardarHorariSencer(String plaEstudi, String absolutePath) {
        CtrlPE.guardaHorari(plaEstudi, absolutePath);
    }

    public boolean existsHorariPlaEstudi(String pe) {
        return CtrlPE.existsHorariPlaEstudi(pe);
    }

    public void carregaFitxerHorari(String absolutePath) {
        CtrlPE.carregaHorari(absolutePath);
    }

    public boolean existsAssignaturaPE(String pe, String assig) {
        return CtrlPE.existsAssignaturaPE(pe, assig);
    }

    public void esborrarAssignaturaPE(String pe, String assig) {
        CtrlPE.esborrarAssignaturaPE(pe, assig);
    }

    public boolean existsGrupAssignatura(String pe, String assig, String grup) {
        return CtrlPE.existsGrupAssignatura(pe, assig, grup);
    }

    public void esborrarGrupAssignatura(String pe, String assig, String grup) {
        CtrlPE.esborrarGrupAssignatura(pe, assig, grup);
    }

    public void setNomAssignatura(String pe, String assig, String newValue) {
        CtrlPE.setNomAssignatrua(pe, assig, newValue);
    }

    public void setNivellAssignatura(String pe, String assig, int nivell) {
        CtrlPE.setNivellAssignatura(pe, assig, nivell);
    }

    public void setQtClassesTeoriaAssignatura(String pe, String assig, int qt) {
        CtrlPE.setQtClassesTeoriaAssignatura(pe, assig, qt);
    }

    public void setDuradaClassesTeoriaAssignatura(String pe, String assig, int qt) {
        CtrlPE.setDuradaClassesTeoriaAssignatura(pe, assig, qt);

    }

    public void setQtClassesProblemesAssignatura(String pe, String assig, int qt) {
        CtrlPE.setQtClassesProblemesAssignatura(pe, assig, qt);
    }

    public void setDuradaClassesProblemesAssignatura(String pe, String assig, int qt) {
        CtrlPE.setDuradaClassesProblemesAssigantura(pe, assig, qt);
    }

    public void setQtClassesLaboratoriAssignatura(String pe, String assig, int qt) {
        CtrlPE.setQtClassesLaboratoriAssigantura(pe, assig, qt);
    }

    public void setDuradaClassesLaboratoriAssignatura(String pe, String assig, int qt) {
        CtrlPE.setDuradaClassesLaboratoriAssigantura(pe, assig, qt);
    }

    public void setCapacitatGrupAssignatura(String pe, String assig, String grup, int qt) {
        CtrlPE.setCapacitatGrupAssignatura(pe, assig, grup, qt);
    }

    public void setHorariGrupAssignatura(String pe, String assig, String grup, String valor) {
        CtrlPE.setHorariGrupAssignatura(pe, assig, grup, valor);
    }

    public void setTipusGrupAssignatura(String pe, String assig, String grup, String newValue) {
        CtrlPE.setTipusGrupAssignatura(pe, assig, grup, newValue);
    }

    public boolean modificarHorari(String plaEstudi, String assig, String grup, String oldDia, int oldHora, String newDia, int newHora, String aula) {
        return CtrlPE.modificaHorari(plaEstudi,assig,grup,oldDia,oldHora,newDia,newHora,aula);
    }


    //NO FUNCIONA PERQUE CAL RECARREGAR LES ASSIGNATURES
    public void afegirAssignatura (String plaEstudi, String id_assig, String nom, int nivell, int n_classes_T, int dur_T, int n_classes_P, int dur_P, int n_classesL, int dur_L)
    {
        CtrlPE.addAssignatura(plaEstudi, id_assig, nom, nivell, n_classes_T, dur_T, n_classes_P, dur_P, n_classesL, dur_L);
    }

    public void afegirCorrequisit (String plaEstudi, String assig, ArrayList<String> nous_correquisits)
    {
        CtrlPE.afegirCorrequisit(plaEstudi, assig, nous_correquisits);
    }

//public void eliminarCorrequisit (String plaEstudi, String assignatura, ArrayList<String> aEliminar)
    public void afegirGrup (String plaEstudi, String assignatura, String id, int capacitat, String horariGrup, Tipus_Aula t)
    {
        CtrlPE.afegirGrup(plaEstudi, assignatura, id, capacitat, horariGrup, t);

    }


    public void eliminarCorrequisit (String plaEstudi, String assignatura, ArrayList<String> aEliminar) {
        CtrlPE.eliminarCorrequisit(plaEstudi, assignatura, aEliminar);
    }

}
