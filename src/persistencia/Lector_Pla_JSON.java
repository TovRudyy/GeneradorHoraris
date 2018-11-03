package persistencia;

import domain.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Lector_Pla_JSON {

    private static void afegeixAssignatures(PlaEstudis plaEstudis, JSONArray assignatures) throws Aula_Exception{
        for(Object a: assignatures){
            JSONObject ass = (JSONObject) a;
            String codi = (String) ass.get("codi");
            String nom = (String) ass.get("nom");
            int nivell = ((Long) ass.get("nivell")).intValue();

            assignatura assig = new assignatura(codi, nom, nivell);

            JSONObject classes = (JSONObject) ass.get("classes");
            int nTeoria = ((Long) classes.get("teoria")).intValue();
            int dTeoria = ((Long) classes.get("duracio_t")).intValue();
            int nProblemes = ((Long) classes.get("problemes")).intValue();
            int dProblemes = ((Long) classes.get("duracio_p")).intValue();
            int nLaboratori = ((Long) classes.get("laboratori")).intValue();
            int dLaboratori = ((Long) classes.get("duracio_l")).intValue();
            assig.setClasses(nTeoria, dTeoria, nLaboratori, dLaboratori, nProblemes, dProblemes);

            JSONObject grups = (JSONObject) ass.get("grups");

            String tipus = (String) grups.get("tipus_subgrup");
            ArrayList<Tipus_Aula> tipus_aules = new ArrayList<>();
            for(String t:tipus.split(";")){
                tipus_aules.add(Tipus_Aula.string_to_Tipus_Aula(t));
            }
            TreeMap<String, grup> grupMap = new TreeMap<>();
            if((boolean) grups.get("automatic")){
                String horari = (String) grups.get("horari");
                int n_grups = ((Long) grups.get("nombre")).intValue();
                int capacitat = ((Long) grups.get("capacitat")).intValue();

                for(int i=1; i<=n_grups; ++i) grupMap.put(String.valueOf(i*10), new grup(String.valueOf(i*10), capacitat, horari, Tipus_Aula.TEORIA));

                if((boolean) grups.get("subgrup_per_alumnes")) {
                    int alumnes_subgrup = ((Long) grups.get("valor")).intValue();
                    for (int i = 1; i <= n_grups; i++) {
                        for (int j = 1; j * alumnes_subgrup <= capacitat; ++j) {
                            for (Tipus_Aula t:tipus_aules) grupMap.put(String.valueOf(i*10 + j), new grup(String.valueOf(i*10+j), alumnes_subgrup, horari, t));
                        }
                    }
                }else{
                    int n_subgrups = ((Long) grups.get("valor")).intValue();
                    for (int i = 1; i <= n_grups; i++) {
                        for (int j = 1; j <= n_subgrups; ++j) {
                            for (Tipus_Aula t:tipus_aules) grupMap.put(String.valueOf(i*10 + j), new grup(String.valueOf(i*10+j), capacitat/n_subgrups, horari, t));
                        }
                    }
                }
            }
            else{
                JSONArray gs = (JSONArray) grups.get("grups");
                for(Object o:gs){
                    JSONObject g = (JSONObject) o;
                    int nom_grup = ((Long) g.get("nom")).intValue();
                    if(nom_grup % 10 != 0 ) throw new Aula_Exception("Nom de grup no valid. Pla:" + plaEstudis.getID() +  ". Assignatura: " + nom);
                    String horari = (String) g.get("horari");
                    int capacitat = ((Long) g.get("capacitat")).intValue();
                    grupMap.put(String.valueOf(nom_grup), new grup(String.valueOf(nom_grup), capacitat, horari, Tipus_Aula.TEORIA));
                    if((boolean) g.get("subgrup_per_alumnes")) {
                        int alumnes_subgrup = ((Long) g.get("valor")).intValue();
                        for (int j = 1; j * alumnes_subgrup <= capacitat; ++j) {
                            for (Tipus_Aula t:tipus_aules) grupMap.put(String.valueOf(nom_grup + j), new grup(String.valueOf(nom_grup+j), alumnes_subgrup, horari, t));
                        }
                    }else{
                        int n_subgrups = ((Long) g.get("valor")).intValue();
                        for (int j = 1; j <= n_subgrups; ++j) {
                            for (Tipus_Aula t:tipus_aules) grupMap.put(String.valueOf(nom_grup + j), new grup(String.valueOf(nom_grup+j), capacitat/n_subgrups, horari, t));
                        }
                    }
                }
            }
            assig.creaGrups(grupMap);
            plaEstudis.addAssignatura(assig);
        }
    }

    private static void afegeixCorrequisits(PlaEstudis plaEstudis, JSONArray corequisits){
        for(Object c: corequisits){
            JSONObject cc = (JSONObject) c;
            String[] a = new String[2];
            a[0] = (String) cc.get("a1");
            a[1] = (String) cc .get("a2");
            plaEstudis.afegirCorrequisits(a);
        }
    }

    public static PlaEstudis llegirPlaEstudis(String fitxer) throws ParseException, IOException, Aula_Exception {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(new FileReader(fitxer));
        PlaEstudis plaEstudis = new PlaEstudis((String) obj.get("nom"));
        JSONArray assignatures = (JSONArray) obj.get("assignatures");
        afegeixAssignatures(plaEstudis, assignatures);
        JSONArray corequisits = (JSONArray) obj.get("corequisits");
        afegeixCorrequisits(plaEstudis, corequisits);

        return plaEstudis;
    }

    public static ArrayList<PlaEstudis> llegirCarpetaPlans() throws Exception{
        File PEfolder = new File("data/PlaEstudis");
        if (!PEfolder.isDirectory()) {
            throw new Exception("Error with data/PlaEstudis folder!");
        }

        ArrayList<PlaEstudis> plansEstudis = new ArrayList<>();
        for (File pe_file : Objects.requireNonNull(PEfolder.listFiles())) {
            System.out.println("The following PlaEstudis file is being readed: " + "'" + PEfolder.getAbsolutePath());
            try{
                plansEstudis.add(llegirPlaEstudis(pe_file.getAbsolutePath()));
            }catch(Exception e){
                System.out.println("El fitxer " + pe_file.toString() + " no s'ha pogut llegir");
                System.out.println("Error: " + e.getMessage());
            }
        }
        return plansEstudis;
    }

    public static assignatura llegeixAssignatura(String path) {
        return null;
    }

}