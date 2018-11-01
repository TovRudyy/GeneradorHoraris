package testsClasses;

import domain.Aula;
import domain.Tipus_Aula;

public class test_aula {
    public static void main(String[] args){
        Aula aula = new Aula("A5001", 30, Tipus_Aula.TEORIA);
        System.out.println(aula.toString());
    }
}
