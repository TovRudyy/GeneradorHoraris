package testsClasses;

import domain.Assignacio;
import domain.Tipus_Aula;
import persistencia.Lector_Aules;

public class testAssignacio {
    public static void main(String [] argv) throws Exception {
        Lector_Aules.readFolderAules();
        Assignacio b = new Assignacio("10", 20, Tipus_Aula.TEORIA, "M1", 1);
        b.printPossiblesClasses();
    }
}
