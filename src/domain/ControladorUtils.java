package domain;

import java.util.ArrayList;

public class ControladorUtils {
    private static ArrayList<String> diesHorari;
    private static ArrayList<String> hores;

    public ControladorUtils() {
        this.diesHorari = initDiesSetmana();
        this.hores = initHorresHorari();
    }

    /**
     * Inicialitza les hores del horari
     * @return Una string amb totes les hores.
     */
    private ArrayList<String> initHorresHorari() {
        ArrayList<String> hores = new ArrayList<String>();
        for (int i = 8; i < 20; i++) {
            int ii = i+1;
            hores.add(new String(i + ":00 - " + ii + ":00"));
        }
        return hores;
    }

    /**
     * Inicialitza tots els dies de la setmana
     * @return Una string amb tots els dies.
     */
    public ArrayList<String> initDiesSetmana() {
        ArrayList<String> dies = new ArrayList<String>();
        for (DiaSetmana dia : DiaSetmana.values()) {
            dies.add(dia.toString());
        }
        return dies;
    }

    /**
     * @return Una array amb tots els dies de la setmana.
     */
    public ArrayList<String> getDiesSetmana() {
        return diesHorari;
    }

    /**
     * @return Una array amb totes les hores del horari.
     */
    public ArrayList<String> getHoresHorari() {
        return hores;
    }
}
