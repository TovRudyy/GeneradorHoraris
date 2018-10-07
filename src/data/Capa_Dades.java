package data;

import domain.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class Capa_Dades {


    private static Tipus_Aula string_to_Tipus_Aula(String s) throws Exception{
        if(s.equals("T")) return Tipus_Aula.TEORIA;
        if(s.equals("L")) return Tipus_Aula.LAB;
        if(s.equals("P")) return Tipus_Aula.PROBLEMES;
        throw new Exception();
    }

    private static Tipus_Lab string_to_Tipus_Lab(String s) throws Exception{
        if(s.equals("INFORMATICA")) return Tipus_Lab.INFORMATICA;
        if(s.equals("FISICA")) return Tipus_Lab.FISICA;
        if(s.equals("ELECTRONICA")) return Tipus_Lab.ELECTRONICA;
        throw new Exception();
    }

    public static PlaEstudis llegeixPlaEstudis(Map<String, Aula> aules, String fitxer) throws Exception {
        File file = new File(fitxer);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(":|\\r\\n");
        PlaEstudis pla = new PlaEstudis(scanner.next());
        String codi = scanner.next();
        if(!codi.equals("AULES")){
            System.out.println("Format incorrecte");
            throw new Exception();
        }
        codi = scanner.next();
        while(!codi.equals("ASSIGN")){
            int capacitat = scanner.nextInt();
            Tipus_Aula tipus = string_to_Tipus_Aula(scanner.next());
            if(tipus.equals(Tipus_Aula.LAB)){
                Tipus_Lab t_lab = string_to_Tipus_Lab(scanner.next());
                aules.put(codi, new Aula_Laboratori(codi, capacitat, t_lab));
            }
            else{
                aules.put(codi, new Aula(codi, capacitat, tipus));
            }
            codi = scanner.next();
        }

        codi = scanner.next();
        while(!codi.equals("GRUP")){
            String nom = scanner.next();
            int nivell = scanner.nextInt();
            pla.addAssignatura(new assignatura(codi, nom, nivell));
            codi = scanner.next();
        }
        return pla;
    }

}
