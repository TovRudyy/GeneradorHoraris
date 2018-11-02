package domain;

import java.util.ArrayList;
import java.util.Stack;

public class Corequisit extends Restriccio {

    //conte les assignatures amb les que es correquisit
    private ArrayList<String> assignatures = new ArrayList<>();

    public Corequisit(){
    }


    public ArrayList<String> getAssignatures () {
        return assignatures;
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
                if (assignatura.equals(b.getId_assig()))    //es un correquisit
                    if(a.getId_grup().equals(b.getId_grup()) && a.getDia().equals(b.getDia())) {
                        if (solapenHores(a.getHoraInici(), a.getHoraFi(), b.getHoraInici(), b.getHoraFi()))
                            return false;

                    }
            }
        }
        return true;
    }


    public boolean checkCorrecte (Classe c1, Classe c2 ) {    //hem de comprovar si la c2 es un correquisit
        if (esCorrequisit (c2.getId_assig()) && (c1.getDia().equals(c2.getDia())) &&
                (solapenHores(c1.getHoraInici(), c1.getHoraFi(), c2.getHoraInici(), c2.getHoraFi())) )
            return false;

        return true;
    }

    private boolean esCorrequisit (String id_assig) {
        for (String a: assignatures)
            if (a.equals(id_assig))  return true;


        return false;
    }





}
