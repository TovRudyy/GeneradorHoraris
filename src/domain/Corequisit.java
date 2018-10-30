package domain;

import java.util.ArrayList;
import java.util.Stack;

public class Corequisit extends Restriccio {

    private ArrayList<String> assignatures = new ArrayList<>();

    public Corequisit(){
    }

    /**
     *
     * @param id_assignatura
     * @return cert si id_assignatura s'ha afegit al Corequisit
     */
    public boolean addAssignatura(String id_assignatura) {
        return assignatures.add(id_assignatura);
    }


    /**
     *
     * @param assignats
     * @return cert si els corequisits de l'assignació es compleixen amb l'horari actuals, fals si no
     */
    @Override
    public boolean checkRestriccio(Stack<Classe> assignats) {
        Classe a = assignats.pop();
        while(!assignats.empty()) {
            Classe b = assignats.pop();
            for (String assignatura : assignatures) {
                if (assignatura.equals(b.getId_assig()))
                    if(a.getId_grup().equals(b.getId_grup()) && a.getDia().equals(b.getDia())) {
                        if (solapenHores(a.getHoraInici(), a.getHoraFi(), b.getHoraInici(), b.getHoraFi()))
                            return false;
                    }
            }
        }
        return true;
    }
}
