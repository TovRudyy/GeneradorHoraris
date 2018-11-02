package domain;

import java.util.Stack;

public class RestriccioSubgrup extends Restriccio {

    private grup pare;

    /**
     *
     * @param pare és l'objecte grup de l'assignacio
     * @return cert si el grup de l'assignacio solapa Classe amb amb el seu grup de teoria, fals si no
     */
    public RestriccioSubgrup(grup pare) {
        this.pare = pare;
    }

    @Override
    public boolean checkRestriccio(Stack<Classe> assignats) {
        Classe a = assignats.pop();
        while (!assignats.empty()) {
            Classe b = assignats.pop();
            if ( b.getId_assig().equals(a.getId_assig()))
                if (pare.esSubgrup(b.getId_grup()) && a.getDia().equals(b.getDia())) {
                        if (solapenHores(a.getHoraInici(), a.getHoraFi(), b.getHoraInici(), b.getHoraFi()))
                            return false;
                }
        }
        return true;
    }


    public boolean checkCorrecte (Classe c1, Classe c2 ) {
        if ( c1.getId_assig().equals(c2.getId_assig()) &&  (checkSub (c2.getId_grup(), c1.getId_grup()) ) &&    //son subgrup
                (c1.getDia().equals(c2.getDia())) &&    //mateix dia i les hores es solapen amb el de teoria
                (solapenHores(c1.getHoraInici(), c1.getHoraFi(), c2.getHoraInici(), c2.getHoraFi()))) {
            return false;
        }

        return true;
    }


    public boolean checkSub (String g1, String g2) {
        int other = Integer.parseInt(g1);
        if (other%10 != 0) return false;
        int me = Integer.parseInt(g2);
        return (other/10 == me/10);
    }


}
