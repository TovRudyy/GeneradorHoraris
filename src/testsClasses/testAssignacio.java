package testsClasses;

import domain.assignacio;
import domain.Tipus_Aula;
import persistencia.Lector_Aules_JSON;

public class testAssignacio {
    public static void main(String [] argv) throws Exception {
        Lector_Aules_JSON.llegirCarpetaAules();
        assignacio b = new assignacio("10", 20, Tipus_Aula.TEORIA, "M1", 1, 1, 2, "M");
        b.printPossiblesClasses();
    }
}
