package domain;

/**
 * Enumeració del tipus d'Aula possibles
 */
public enum Tipus_Aula{
    TEORIA, PROBLEMES, LAB_INFORMATICA, LAB_ELECTRONICA, LAB_FISICA;

    public static Tipus_Aula string_to_Tipus_Aula(String s) throws Aula_Exception {
        s = s.toUpperCase();
        if (s.equals("T") | s.equals("TEO") | s.equals("TEORIA")) return Tipus_Aula.TEORIA;
        if (s.equals("LF") | s.equals("LAB_FISICA") | s.equals("LABORATORI_FISICA") | s.equals("F") | s.equals("FISICA") | s.equals("L FISICA")) return Tipus_Aula.LAB_FISICA;
        if (s.equals("LE") | s.equals("LAB_ELECTRONICA") | s.equals("LABORATORI_ELECTRONICA") | s.equals("E") | s.equals("ELECTRONICA") | s.equals("L ELECTRONICA")) return Tipus_Aula.LAB_ELECTRONICA;
        if (s.equals("LI") | s.equals("LAB_INFORMATICA") | s.equals("LABORATORI_INFORMATICA") | s.equals("I") | s.equals("INFORMATICA") | s.equals("L INFORMATICA")) return Tipus_Aula.LAB_INFORMATICA;
        if (s.equals("P") | s.equals("PROBLEMES")) return Tipus_Aula.PROBLEMES;
        throw new Aula_Exception("Format aula incorrecte");
    }

    public static boolean es_Laboratori(Tipus_Aula tipus){
        return tipus == LAB_ELECTRONICA || tipus == LAB_INFORMATICA || tipus == LAB_FISICA;
    }
}
