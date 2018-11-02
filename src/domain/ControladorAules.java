package domain;

import persistencia.ControladorPersistencia;

import java.util.ArrayList;
import java.util.Map;

public class ControladorAules {

    ControladorPersistencia CtrlDades = new ControladorPersistencia();
    Map<String, Aula> Aulari;

    public ControladorAules(){
            Aulari = CtrlDades.llegeixDadesAules();
    }

    public ArrayList<String> getInfoAulari(){
        ArrayList<String> ret = new ArrayList<String>();
        for (Aula aula : Aulari.values()) {
            ret.add(aula.toString());
        }
        return ret;
    }
}
