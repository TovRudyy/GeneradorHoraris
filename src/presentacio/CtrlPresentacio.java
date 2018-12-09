package presentacio;

import domain.ControladorAules;
import domain.ControladorPlaEstudis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;

public class CtrlPresentacio {
    static ControladorPlaEstudis CtrlPE;
    static ControladorAules CtrlAUS;

    static ArrayList<AulaFX> aules;

    CtrlPlaEstudis PresentacioPE;

    public CtrlPresentacio() {
        this.CtrlPE = new ControladorPlaEstudis();
        this.CtrlAUS = new ControladorAules();
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
}
