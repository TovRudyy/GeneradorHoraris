package domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class RestriccioInterval extends RestriccioFlexible {
    private DiaSetmana d;
    private int horaIni;
    private int horaFi;

    public RestriccioInterval (DiaSetmana d, int horaIni, int horaFi)
    {
        this.d = d;
        this.horaIni = horaIni;
        this.horaFi = horaFi;
    }

    public DiaSetmana getDia ()
    {
        return d;
    }

    public int getHoraIni ()
    {
        return horaIni;
    }

    public int getHoraFi ()
    {
        return horaFi;
    }

    public void showInfo ()
    {
        System.out.println("El dia " + d + " a l'interval " + horaIni + " fins " + horaFi);
    }

    //Elimina les opcions de un map que no poden passar en el interval que te definit
    public void podaPossibilitats (Map<String, Map<DiaSetmana, LinkedList<Classe>>> possibles_classes)
    {
        ArrayList<Classe> eliminades = new ArrayList<>();
        //recorro totes les aules
        for (Map.Entry<String, Map<DiaSetmana, LinkedList<Classe>>> a : possibles_classes.entrySet())
        {
            LinkedList<Classe> classes = a.getValue().get(d);
            for (Classe c : classes)
            {
                if (solapenHores(horaIni, horaFi, c.getHoraInici(), c.getHoraFi()))
                    eliminades.add(c);
            }
        }

        for (Classe c_aux: eliminades)  //eliminem les classes amb les que hi ha conflicte
            possibles_classes.get(c_aux.getIdAula()).get(c_aux.getDia()).remove (c_aux);

    }
}

