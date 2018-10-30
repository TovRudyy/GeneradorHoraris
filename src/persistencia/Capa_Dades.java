package persistencia;

import domain.Tipus_Lab;
import domain.Tipus_Aula;
import domain.Aula_Exception;

public class Capa_Dades {

    static Tipus_Aula string_to_Tipus_Aula(String s) throws Aula_Exception {
        s = s.toUpperCase();
        if (s.equals("T") | s.equals("TEO") | s.equals("TEORIA")) return Tipus_Aula.TEORIA;
        if (s.equals("L") | s.equals("LAB") | s.equals("LABORATORI")) return Tipus_Aula.LAB;
        if (s.equals("P") | s.equals("PROBLEMES")) return Tipus_Aula.PROBLEMES;
        throw new Aula_Exception("Format aula incorrecte");
    }

    static Tipus_Lab string_to_Tipus_Lab(String s) throws Aula_Exception {
        s = s.toUpperCase();
        if (s.equals("INFORMATICA") | s.equals("I")) return Tipus_Lab.INFORMATICA;
        if (s.equals("FISICA") | s.equals("F")) return Tipus_Lab.FISICA;
        if (s.equals("ELECTRONICA") | s.equals("E")) return Tipus_Lab.ELECTRONICA;
        throw new Aula_Exception("Format d'entrada del tipus lab incorrecte");
    }
}
