package domain;

import java.util.Stack;

public class RestriccioOcupacio extends Restriccio {

    /**
     * @param c1 Primera possibilitat.
     * @param c2 Segona possibilitat.
     * @return Retorna un booleà que indica si c2 es solapa amb l'aula, en un moment concret, de la Classe c1.
     */
    public boolean checkCorrecte (Classe c1, Classe c2 ) {    //hem de comprovar si la classe1 es possible si tenim classe2
        if (c1.getIdAula().equals(c2.getIdAula()) && c1.getDia().equals(c2.getDia()) &&
                (solapenHores(c1.getHoraInici(), c1.getHoraFi(), c2.getHoraInici(), c2.getHoraFi()) ) )
            return false;


        return true;
    }

}
