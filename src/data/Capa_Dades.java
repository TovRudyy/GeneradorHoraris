package data;

import domain.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Capa_Dades {


    private static Tipus_Aula string_to_Tipus_Aula(String s) throws Exception{
        if(s.equals("T") | s.equals("TEO") | s.equals("TEORIA")) return Tipus_Aula.TEORIA;
        if(s.equals("L") | s.equals("LAB") | s.equals("LABORATORI")) return Tipus_Aula.LAB;
        if(s.equals("P") | s.equals("PROBLEMES")) return Tipus_Aula.PROBLEMES;
        throw new Exception("format aula incorrecte");
    }

    private static Tipus_Lab string_to_Tipus_Lab(String s) throws Exception{
        if(s.equals("INFORMATICA") | s.equals("I")) return Tipus_Lab.INFORMATICA;
        if(s.equals("FISICA") | s.equals("F")) return Tipus_Lab.FISICA;
        if(s.equals("ELECTRONICA") | s.equals("E")) return Tipus_Lab.ELECTRONICA;
        throw new Exception("Format d'entrada del tipus lab incorrecte");
    }


    public static Map<String, Aula> llegeixAules(String fitxer) throws Exception {
        File file = new File(fitxer);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\\n|:|\\r\\n");
        Map<String, Aula> aules = new TreeMap<>();

        while(scanner.hasNext()){
            String codi = scanner.next();
            int capacitat = scanner.nextInt();
            Tipus_Aula tipus_aula = string_to_Tipus_Aula(scanner.next());
            if(tipus_aula.equals(Tipus_Aula.LAB)) aules.put(codi, new Laboratori(codi, capacitat, string_to_Tipus_Lab(scanner.next())));
            else aules.put(codi, new Aula(codi, capacitat, tipus_aula));
        }
        return aules;
    }

    public static void afegeixAules(Map<String, Aula> aules, String fitxer) throws Exception {
        aules.putAll(llegeixAules(fitxer));
    }

    private static assignatura llegeixAssignatura(Scanner scanner) throws Exception{
        assignatura ass = new assignatura(scanner.next(), scanner.next(), scanner.nextInt());
        scanner.next(); //ToDO fer M T Assignatura
        TreeMap<String, grup> grups = new TreeMap<>();
        if(scanner.next().equals("GM")){
            int n_grups = scanner.nextInt();
                boolean lab=false, prob=false;
                switch(scanner.next()){
                    case "P":
                        prob = true;
                        break;
                    case "L":
                        lab = true;
                        break;
                    case "LP":
                    case "PL":
                        lab = prob = true;
                        break;
                    default:
                }
                Tipus_Lab tipus_lab = (lab)? string_to_Tipus_Lab(scanner.next()) : null;
                for(int i=0; i<n_grups; ++i){
                    int nom = scanner.nextInt();
                    if(nom % 10 != 0) throw new Exception();    //comprovem que tingui format de grup de teoria
                    int cap = scanner.nextInt();
                    scanner.next(); //ToDo fer M T Grup
                    grups.put(String.valueOf(nom), new grup(String.valueOf(nom), cap));
                    if(scanner.next().equals("N")){
                        int n_subs = scanner.nextInt();
                        for(int j=1; j<=n_subs; ++j){
                            if(prob) grups.put(String.valueOf(nom+j), new grupProblemes(String.valueOf(nom+j), cap/n_subs));
                            if(lab) grups.put(String.valueOf(nom+j), new grupLaboratori(String.valueOf(nom+j), cap/n_subs, tipus_lab));
                        }
                    }
                    else{
                        int n_als = scanner.nextInt();
                        for(int j=1; j*n_als<=cap; ++j){
                            if(prob) grups.put(String.valueOf(nom+j), new grupProblemes(String.valueOf(nom+j), n_als));
                            if(lab) grups.put(String.valueOf(nom+j), new grupLaboratori(String.valueOf(nom+j), n_als, tipus_lab));
                        }

                    }
                }
            }
        else {
            int n = scanner.nextInt();
            int cap = scanner.nextInt();
            for (int i = 1; i <= n; i += 1) grups.put(String.valueOf(i*10), new grup(String.valueOf(i*10), cap));
            if (scanner.next().equals("N")) {
                int n_subs = scanner.nextInt();
                switch (scanner.next()) {
                    case "P":
                        for (int i = 1; i <= n; i++) {
                            for (int j = 1; j < n_subs; ++j) {
                                grups.put(String.valueOf(i*10 + j), new grupProblemes(String.valueOf(i*10 + j), cap / n_subs));
                            }
                        }
                        break;
                    case "L":
                        Tipus_Lab tipus_lab = string_to_Tipus_Lab(scanner.next());
                        for (int i = 1; i <= n; i++) {
                            for (int j = 1; j < n_subs; ++j) {
                                grups.put(String.valueOf(i*10 + j), new grupLaboratori(String.valueOf(i*10 + j), cap / n_subs, tipus_lab));
                            }
                        }
                        break;
                    case "LP":
                    case "PL":
                        tipus_lab = string_to_Tipus_Lab(scanner.next());
                        for (int i = 1; i <= n; i += 1) {
                            for (int j = 1; j < n_subs; ++j) {
                                grups.put(String.valueOf(i*10 + j), new grupProblemes(String.valueOf(i*10 + j), cap / n_subs));
                                grups.put(String.valueOf(i*10 + j), new grupLaboratori(String.valueOf(i*10 + j), cap / n_subs, tipus_lab));
                            }
                        }
                        break;
                }
            } else {
                int n_alumnes = scanner.nextInt();
                switch (scanner.next()) {
                    case "P":
                        for (int i = 1; i <= n; i++) {
                            for (int j = 1; j * n_alumnes <= cap; ++j) {
                                grups.put(String.valueOf(i*10 + j), new grupProblemes(String.valueOf(i*10 + j), n_alumnes));
                            }
                        }
                        break;
                    case "L":
                        Tipus_Lab tipus_lab = string_to_Tipus_Lab(scanner.next());
                        for (int i = 1; i <= n; i++) {
                            for (int j = 1; j * n_alumnes <= cap; ++j) {
                                grups.put(String.valueOf(i*10 + j), new grupLaboratori(String.valueOf(i*10 + j), n_alumnes, tipus_lab));
                            }
                        }
                        break;
                    case "LP":
                    case "PL":
                        tipus_lab = string_to_Tipus_Lab(scanner.next());
                        for (int i = 1; i <= n; i++) {
                            for (int j = 1; j * n_alumnes <= cap; ++j) {
                                grups.put(String.valueOf(i*10 + j), new grupProblemes(String.valueOf(i*10 + j), n_alumnes));
                                grups.put(String.valueOf(i*10 + j), new grupLaboratori(String.valueOf(i*10 + j), n_alumnes, tipus_lab));
                            }
                        }
                        break;
                }
            }
        }
        ass.creaGrups(grups);
        ass.setClasses(scanner.nextInt(), scanner.nextDouble(), scanner.nextInt(), scanner.nextDouble(), scanner.nextInt(), scanner.nextDouble());
        return ass;
    }


    private static Corequisit llegeixCorequisit(Scanner scanner){
        return new Corequisit(scanner.next(), scanner.next());
    }


    public static PlaEstudis llegeixPlaEstudis(String fitxer) throws Exception {
        File file = new File(fitxer);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\n|:|\\r\\n");
        PlaEstudis pla = new PlaEstudis(scanner.next());
        String codi;
        while(!(codi = scanner.next()).equals("FI")){
            switch(codi){
                case "A":
                    pla.addAssignatura(llegeixAssignatura(scanner));
                    break;
                case "C":
                    llegeixCorequisit(scanner);
                    break;
                default:
                    throw new Exception("codi incorrecte");
            }
        }
        return pla;
    }

}
