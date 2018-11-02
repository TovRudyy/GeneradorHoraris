package domain;

import java.util.Stack;

public class RestriccioOcupacio extends Restriccio {

    /**
     * @param assignats
     * @return cert si la Classe escollida està disponible, fals si solapa amb una assignació existent
     */
    public boolean checkRestriccio(Stack<Classe> assignats) {
        Classe a = assignats.pop(); //ultim afegit
        while (!assignats.empty()) {
            Classe b = assignats.pop();
            if (a.getIdAula().equals(b.getIdAula()) && a.getDia().equals(b.getDia()) &&
                solapenHores(a.getHoraInici(), a.getHoraFi(), b.getHoraInici(), b.getHoraFi()) ) {
                return false;
            }
        }
        return true;

    }


    public boolean checkCorrecte (Classe c1, Classe c2 ) {    //hem de comprovar si la classe1 es possible si tenim classe2
        if (c1.getIdAula().equals(c2.getIdAula()) && c1.getDia().equals(c2.getDia()) &&
                (solapenHores(c1.getHoraInici(), c1.getHoraFi(), c2.getHoraInici(), c2.getHoraFi()) ) )
            return false;


        return true;
    }

}
