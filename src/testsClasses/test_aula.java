package testsClasses;

import domain.Aula;
import domain.Tipus_Aula;

import java.util.Arrays;

public class test_aula {
    public static void main(String[] args) throws Exception {
        Aula aula = new Aula("A5001", 30, Tipus_Aula.TEORIA);
        System.out.println(aula.toString());
        aula.reservar(2, 8, 2);
        aula.reservar(0, 14, 3);
        aula.reservar(4, 10, 4);
        System.out.println(Arrays.deepToString(aula.getOcupacio()));
        System.out.println();
        System.out.println(aula.ocupacioToString());
    }
}
