package domain;

import java.util.Stack;

public class RestriccioSubgrup extends Restriccio {

    private grup pare;

    /**
     *
     * @param pare és l'objecte grup de l'assignacio
     * @return cert si el grup de l'assignacio solapa Classe amb amb el seu grup de teoria, fals si no
     */
    RestriccioSubgrup(grup pare) {
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
}
