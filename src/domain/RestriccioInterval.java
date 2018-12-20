package domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class RestriccioInterval extends RestriccioFlexible {
    private DiaSetmana d;
    private int horaIni;
    private int horaFi;
    private String id;

    /**
     * Creador de la restriccio Interval
     * @param d
     * @param horaIni
     * @param horaFi
     */
    public RestriccioInterval (DiaSetmana d, int horaIni, int horaFi)
    {
        this.d = d;
        this.horaIni = horaIni;
        this.horaFi = horaFi;
    }

    public void setId(String id)
    {
        this.id = id;
    }


    /**
     * @return La informacio de la restriccio.
     */
    public String getInfo ()
    {
        return ("El grup " + id + " no es pot donar el " + d + " de " + horaIni + " a " + horaFi);
    }

    /**
     * Imprimeix per pantalla la informacio de la restriccio.
     */
    public void showInfo ()
    {
        System.out.println("El dia " + d + " a l'interval " + horaIni + " fins " + horaFi);
    }


    /**
     * Fa la poda de les possibilitats que ja no son possibles.
     * @param possibles_classes Tot el map amb totes les possibilitats.
     */
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


    /**
     * @return Retorna el identificador de la restriccio.
     */
    public String getAssignacioId ()
    {
        return id;
    }

}

