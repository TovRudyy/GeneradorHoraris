package persistencia;

import domain.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Lector_Pla {


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
            }
            Tipus_Lab tipus_lab = (lab)? Capa_Dades.string_to_Tipus_Lab(scanner.next()) : null;
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
            String metode = scanner.next();
            int n_auto = scanner.nextInt();
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
            }
            Tipus_Lab tipus_lab = (lab)? Capa_Dades.string_to_Tipus_Lab(scanner.next()) : null;
            if(metode.equals("N")) {
                for (int i = 1; i <= n; i += 1) {
                    for (int j = 1; j <= n_auto; ++j) {
                        if(prob) grups.put(String.valueOf(i * 10 + j), new grupProblemes(String.valueOf(i * 10 + j), cap / n_auto));
                        if(lab) grups.put(String.valueOf(i * 10 + j), new grupLaboratori(String.valueOf(i * 10 + j), cap / n_auto, tipus_lab));
                    }
                }
            } else {
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j * n_auto <= cap; ++j) {
                        if(prob) grups.put(String.valueOf(i * 10 + j), new grupProblemes(String.valueOf(i * 10 + j), n_auto));
                        if(lab) grups.put(String.valueOf(i * 10 + j), new grupLaboratori(String.valueOf(i * 10 + j), n_auto, tipus_lab));
                    }
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

    public static ArrayList<PlaEstudis> readFolderPlaEstudis() throws Exception {
        File PEfolder = new File("data/PlaEstudis");
        if (!PEfolder.isDirectory()) {
            throw new Exception("Error with data/PlaEstudis folder!");
        }

        ArrayList<PlaEstudis> plansEstudis = new ArrayList<PlaEstudis>();
        File[] PEs = PEfolder.listFiles();
        for (File pe_file : PEs) {
            System.out.println("The following PlaEstudis file is being readed: " + "'" + PEfolder.getAbsolutePath());
            PlaEstudis pe_aux = llegeixPlaEstudis(pe_file.getAbsolutePath());
            if (!plansEstudis.add(pe_aux)) {
                throw new Exception("Cannot read '" + pe_file.getName() + "' file");
            }
        }
        return plansEstudis;
    }

}
