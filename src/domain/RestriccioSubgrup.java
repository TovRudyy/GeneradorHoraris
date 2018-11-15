package domain;

import java.util.Stack;

public class RestriccioSubgrup extends Restriccio {

    private grup pare;

    /**
     * @param pare és l'objecte grup de l'assignacio
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
        if ( c1.getId_assig().equals(c2.getId_assig())
                &&  (checkSub(c2.getId_grup(), c1.getId_grup()) || checkSub (c1.getId_grup(), c2.getId_grup()) ) &&    //son subgrup
                (c1.getDia().equals(c2.getDia()))) {
            return false;
        }

        return true;
    }


    /**
     * @param g1 id d'un grup que ja té horari assignat
     * @param g2 id del grup que s'està comprovant la restricció
     * @return Booleà que indica si el grup g2 és subgrup del grup g1.
     */
    public boolean checkSub (String g1, String g2) {
        if(g1.equals(g2)) return true;
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
