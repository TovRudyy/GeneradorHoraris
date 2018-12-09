package domain;

import com.sun.deploy.util.ArrayUtil;

import java.util.ArrayList;

public class ControladorUtils {
    private static ArrayList<String> diesHorari;
    private static ArrayList<String> hores;

    public ControladorUtils() {
        this.diesHorari = initDiesSetmana();
        this.hores = initHorresHorari();
    }

    private ArrayList<String> initHorresHorari() {
        ArrayList<String> hores = new ArrayList<String>();
        for (int i = 8; i < 20; i++) {
            int ii = i+1;
            hores.add(new String(i + ":00 - " + ii + ":00"));
        }
        return hores;
    }

    public ArrayList<String> initDiesSetmana() {
        ArrayList<String> dies = new ArrayList<String>();
        for (DiaSetmana dia : DiaSetmana.values()) {
            dies.add(dia.toString());
        }
        return dies;
    }

    public ArrayList<String> getDiesSetmana() {
        return diesHorari;
    }

    public ArrayList<String> getHoresHorari() {
        return hores;
    }
}
