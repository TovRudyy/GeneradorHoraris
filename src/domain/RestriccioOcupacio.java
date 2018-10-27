package domain;

import java.util.Stack;

public class RestriccioOcupacio extends Restriccio{

    /**
     *
     * @param assignats pila que conté els identificadors de les assignacions i les seves classes assignades.
     *                  Com a mínim ha de contenir un element.
     * @return cert si la Classe escollida està disponible, fals si solapa amb una assignació existent
     */
    public boolean checkRestriccio(Stack<Classe> assignats) {
        Classe a = assignats.pop();
        while (!assignats.empty()) {
            Classe b = assignats.pop();
            if ( a.getId_assig().equals(b.getId_assig()) && a.getDia().equals(b.getDia()) ) {
                return solapenHores(a.getHoraInici(), a.getHoraFi(), b.getHoraInici(), b.getHoraFi());
            }
        }
        return true;
    }
}
