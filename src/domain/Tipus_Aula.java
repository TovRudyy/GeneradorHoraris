package domain;

import java.io.PrintStream;

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
        if (s.equals("P") | s.equals("PROB") | s.equals("PROBLEMES")) return Tipus_Aula.PROBLEMES;
        throw new Aula_Exception("Format aula incorrecte");
    }

    public static boolean es_Laboratori(Tipus_Aula tipus){
        return tipus == LAB_ELECTRONICA || tipus == LAB_INFORMATICA || tipus == LAB_FISICA;
    }

    public static void escriure_codis_valids(PrintStream out){
        out.println("Tots els seguents codis son case insensitive.");

        out.println("Codis valids per " + TEORIA + ":");
        out.println("\t T | TEO | TEORIA");

        out.println("Codis valids per " + PROBLEMES + ":");
        out.println("\t P | PROB | PROBLEMES");

        out.println("Codis valids per " + LAB_FISICA + ":");
        out.println("\t LF | L FISICA | LAB_FISICA | LABORATORI_FISICA | F | FISICA");

        out.println("Codis valids per " + LAB_ELECTRONICA + ":");
        out.println("\t LE | L ELECTRONICA | LAB_ELECTRONICA | LABORATORI_ELECTRONICA | E | ELECTRONICA");

        out.println("Codis valids per " + LAB_INFORMATICA + ":");
        out.println("\t LI | L INFORMATICA | LAB_INFORMATICA | LABORATORI_INFORMATICA | I | INFORMATICA");
    }

    public static void escriure_codis_valids(){escriure_codis_valids(System.out);}
}
