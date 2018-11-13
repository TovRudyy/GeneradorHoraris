package domain;

import java.util.Stack;

public class RestriccioSubgrup extends Restriccio {

    private grup pare;

    /**
     * @param pare és l'objecte grup de l'assignacio
     * @return cert si el grup de l'assignacio solapa Classe amb amb el seu grup de teoria, fals si no
     */
    public RestriccioSubgrup(grup pare) {
        this.pare = pare;
    }

    /**
     * @param c1 Primera possibilitat.
     * @param c2 Segona possibilitat.
     * @return Retorna un booleà que indica si el grup de c2 és subgrup del grup de c1.
     */
    public boolean checkCorrecte (Classe c1, Classe c2 ) {
        if ( c1.getId_assig().equals(c2.getId_assig()) &&  (checkSub (c2.getId_grup(), c1.getId_grup()) ) &&    //son subgrup
                (c1.getDia().equals(c2.getDia())) &&    //mateix dia i les hores es solapen amb el de teoria
                (solapenHores(c1.getHoraInici(), c1.getHoraFi(), c2.getHoraInici(), c2.getHoraFi()))) {
            return false;
        }

        return true;
    }


    /**
     * @return Booleà que indica si el grup g2 és subgrup del grup g1.
     */
    public boolean checkSub (String g1, String g2) {
        int other = Integer.parseInt(g1);
        if (other%10 != 0) return false;
        int me = Integer.parseInt(g2);
        return (other/10 == me/10);
    }

    @Override
    public String toString() {
        return "Pare: " + pare.toString();
    }
}
