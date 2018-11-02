package domain;


import persistencia.ControladorPersistencia;

import java.util.ArrayList;

public class ControladorPlaEstudis {

    ControladorPersistencia CtrlDades = new ControladorPersistencia();
    ArrayList<PlaEstudis> ConjuntPE;

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

}
