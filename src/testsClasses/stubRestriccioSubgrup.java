package testsClasses;

import java.util.Stack;
import domain.Classe;
import domain.DiaSetmana;
import domain.Tipus_Aula;
import domain.grup;

public class stubRestriccioSubgrup {

    public stubRestriccioSubgrup () {}

    public grup getGrupTeoria () {
        grup g = new grup("10", 30, "M", Tipus_Aula.TEORIA);
        return g;
    }

    public Stack<Classe> getResultatParcialFals () {
        //Classe ja esta testejada previament per tant ja sabem que funciona be
        Classe a = new Classe ("M1", "10", DiaSetmana.DILLUNS, 8, 10, "A5001");
        Classe b = new Classe ("M2", "20", DiaSetmana.DIMARTS, 8, 10, "A5001");
        Classe c = new Classe ("M1", "11", DiaSetmana.DILLUNS, 9, 10, "A5001");

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
        Classe c = new Classe ("M1", "12", DiaSetmana.DILLUNS, 12, 13, "A5001");
        Classe d = new Classe ("M1", "11", DiaSetmana.DILLUNS, 10, 11, "A5001");

        Stack<Classe> result = new Stack();
        result.push(a);
        result.push(b);
        result.push(c);
        result.push(d);

        return result;
    }

}