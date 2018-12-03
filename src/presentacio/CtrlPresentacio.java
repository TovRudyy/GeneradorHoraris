package presentacio;

import domain.ControladorAules;
import domain.ControladorPlaEstudis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        ArrayList<String> dades = CtrlAUS.getInfoAulari();
        for (String s : dades) {
            String[] camps = s.split(":");
            System.err.println("DEBUG: id_aula: " + camps[0] + " capacitat_aula: " + camps[1] + " tipus_aula: " + camps[2]);
            arr.add(new AulaFX(camps[0], Integer.parseInt(camps[1]), camps[2]));
        }
    }

    public ObservableList<AulaFX> getDataAula() {
        ObservableList<AulaFX> ret = FXCollections.observableArrayList(aules);
        return ret;
    }

    public ArrayList<String> getPlans() {
        return CtrlPE.getInfoPlans();
    }

}
