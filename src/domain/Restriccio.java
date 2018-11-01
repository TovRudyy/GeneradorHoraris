package domain;

import java.util.Stack;

/**
 * @author Rudyy, Oleksandr
 * Date: 07/10/2018
 */
public abstract class Restriccio {

    /**
     *
     * @param assignats pila que conté els identificadors de les assignacions i les seves classes assignades. Com a mínim ha de contenir un element.
     * @return certs si la nova assignacio de Classe compleix amb la restricció, fals si no.
     */
    public abstract boolean checkRestriccio(Stack<Classe> assignats);


    protected boolean solapenHores(int ai, int af, int bi, int bf) {
        if ((bi >= ai &&  bi < af) || (bf > ai && bf < af) ||
            (ai >= bi &&  ai < bf) || (af > bi && af < bf)) return true;

        return false;
    }

}
