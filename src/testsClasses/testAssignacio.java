package testsClasses;

import domain.GrupConcret;
import domain.Tipus_Aula;
import domain.grup;
import persistencia.Lector_Aules;
import persistencia.Lector_Pla;

public class testGrupConcret {
    public static void main(String [] argv) throws Exception {
        Lector_Aules.readFolderAules();
        GrupConcret b = new GrupConcret("10", 20, Tipus_Aula.TEORIA, "M1", 1, 1, 2, "M");
        b.printPossiblesClasses();
    }
}
