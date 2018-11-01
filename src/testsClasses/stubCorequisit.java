package testsClasses;

import java.util.Stack;
import domain.Classe;
import domain.DiaSetmana;
import java.util.ArrayList;

public class stubCorequisit {

    public stubCorequisit () {}

    public String getCorequisit () {
        return "M1";
    }

    public Stack<Classe> getResultatParcialFals () {
        //Classe ja esta testejada previament per tant ja sabem que funciona be
        Classe a = new Classe ("M1", "10", DiaSetmana.DILLUNS, 8, 10, "A5001");
        Classe b = new Classe ("M2", "20", DiaSetmana.DIMARTS, 8, 10, "A5001");
        Classe c = new Classe ("F", "10", DiaSetmana.DILLUNS, 9, 10, "A6001");

        Stack<Classe> result = new Stack();
        result.push(a);
        result.push(b);
        result.push(c);

        return result;
    }

    public Stack<Classe> getResultatParcialCert () {
        //Classe ja esta testejada previament per tant ja sabem que funciona be
        Classe a = new Classe ("M1", "10", DiaSetmana.DILLUNS, 8, 10, "A5001");
        Classe b = new Classe ("M2", "20", DiaSetmana.DIMARTS, 8, 10, "A5001");
        Classe c = new Classe ("F", "10", DiaSetmana.DILLUNS, 10, 12, "A5001");

        Stack<Classe> result = new Stack();
        result.push(a);
        result.push(b);
        result.push(c);

        return result;
    }

}
