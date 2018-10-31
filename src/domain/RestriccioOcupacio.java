package domain;

import java.util.HashMap;
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
                solapenHores(a.getHoraInici(), a.getHoraFi(), b.getHoraInici(), b.getHoraFi()) ) return false;


        }
        return true;

    }
}
