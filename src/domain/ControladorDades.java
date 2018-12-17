package domain;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import persistencia.ControladorPersistencia;

import java.io.IOException;
import java.util.*;

public final class ControladorDades {

    private ControladorDades() {
    }

    /**
     * Llegeix de un fitxer diversos plans d'estudis.
     * @return Retorna una array amb tots els plans d'estudis llegits.
     */
    public static ArrayList<PlaEstudis> llegeixDadesPE() {
        ArrayList<PlaEstudis> plaEstudisArray = new ArrayList<>(llegirCarpetaPEJSON());
        plaEstudisArray.addAll(llegirCarpetaPESerialized());
        return plaEstudisArray;
    }

    /**
     * Llegeix de un fitxer un pla d'estudis concret.
     * @param path Ruta del fitxer.
     * @return La instancia del pla d'estudis creat.
     */
    public static PlaEstudis llegeixPE(String path) throws IOException, ClassNotFoundException, ParseException, Aula_Exception {
        PlaEstudis plaEstudis;
        switch (path.substring(path.lastIndexOf('.') + 1)) {
            case "ser":
                plaEstudis = (PlaEstudis) ControladorPersistencia.carrega(path);
                break;
            case "json":
                plaEstudis = JSONToPlaEstudis((JSONObject) ControladorPersistencia.llegirJSON(path));
                break;
            default:
                throw new IOException("Tipus de fitxer no suportat");
        }
        return plaEstudis;
    }

    /**
     * Llegeix de un fitxer un conjunt d'aules.
     * @return Un map amb la informacio de les diferents aules.
     */
    public static Map<String, Aula> llegeixDadesAules() {
       Map<String, Aula> aules = new TreeMap<>(llegirCarpetaAulesJSON());
       aules.putAll(llegirCarpetaAulesSerialized());
       return aules;

    }

    /**
     * Llegeix de un fitxer un conjunt d'aules.
     * @param path Ruta al fitxer.
     * @return Les instancies de aules creades.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Aula> llegeixAules(String path) throws IOException, ClassNotFoundException, ParseException, Aula_Exception {
        Map<String, Aula> aules;
        switch (path.substring(path.lastIndexOf('.') + 1)) {
            case "ser":
                aules = (Map<String, Aula>) ControladorPersistencia.carrega(path);
                break;
            case "json":
                aules = JSONToAules((JSONArray) ControladorPersistencia.llegirJSON(path));
                break;
            default:
                throw new IOException("Tipus de fitxer no suportat");
        }
        return aules;
    }

    public static String guardaHorariGUI(String horari, String path){
        return ControladorPersistencia.guardaHorariGUI(horari, path);
    }



    private static ArrayList<PlaEstudis> llegirCarpetaPEJSON() {
        ArrayList<PlaEstudis> plaEstudisArray = new ArrayList<>();
        ArrayList<Object> plans = ControladorPersistencia.llegirCarpetaPlansJSON();
        for(Object object: plans) {
            try{
                plaEstudisArray.add(JSONToPlaEstudis((JSONObject) object));
            }catch(Aula_Exception ae) {
                System.err.println(ae.getMessage());
                ae.printStackTrace();
            }
        }
        return plaEstudisArray;
    }

    private static ArrayList<PlaEstudis> llegirCarpetaPESerialized() {
        ArrayList<PlaEstudis> plans = new ArrayList<>();
        for(Object o: ControladorPersistencia.llegirCarpetaPlansSerialized()) {
            plans.add((PlaEstudis) o);
        }
        return plans;
    }

    private static PlaEstudis JSONToPlaEstudis(JSONObject jsonObject) throws Aula_Exception {
        PlaEstudis plaEstudis = new PlaEstudis((String) jsonObject.get("nom"));
        JSONArray assignatures = (JSONArray) jsonObject.get("assignatures");
        if(assignatures != null) afegeixAssignatures(plaEstudis, assignatures);
        JSONArray corequisits = (JSONArray) jsonObject.get("corequisits");
        if(corequisits != null) afegeixCorrequisits(plaEstudis, corequisits);
        return plaEstudis;
    }

    private static assignatura creaAssignatura(JSONObject ass) throws Aula_Exception{
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
        assig.setClasses(nTeoria, (dTeoria>=0)? dTeoria:0, nLaboratori, (dLaboratori>=0)? dLaboratori:0, nProblemes, (dProblemes>=0)? dProblemes:0);

        JSONObject grups = (JSONObject) ass.get("grups");

        String tipus = (String) grups.get("tipus_subgrup");
        Set<Tipus_Aula> tipus_aules = new HashSet<>();
        for(String t:tipus.split("[;:,.]")){
            tipus_aules.add(Tipus_Aula.string_to_Tipus_Aula(t));
        }
        TreeMap<String, grup> grupMap = new TreeMap<>();
        if((boolean) grups.get("automatic")){
            int n_grups = ((Long) grups.get("nombre")).intValue();
            int capacitat = ((Long) grups.get("capacitat")).intValue();
            int n_matins = ((Long) grups.get("matins")).intValue();

            for(int i=1; i<=n_grups; ++i) grupMap.put(String.valueOf(i*10), new grup(String.valueOf(i*10), capacitat, (i<=n_matins)? "M" : "T", Tipus_Aula.TEORIA));

            if((boolean) grups.get("subgrup_per_alumnes")) {
                int alumnes_subgrup = ((Long) grups.get("valor")).intValue();
                for (int i = 1; i <= n_grups; i++) {
                    for (int j = 1; j * alumnes_subgrup <= capacitat; ++j) {
                        for (Tipus_Aula t:tipus_aules) grupMap.put(String.valueOf(i*10 + j), new grup(String.valueOf(i*10+j), alumnes_subgrup, (i<=n_matins)? "M" : "T", t));
                    }
                }
            }else{
                int n_subgrups = ((Long) grups.get("valor")).intValue();
                for (int i = 1; i <= n_grups; i++) {
                    for (int j = 1; j <= n_subgrups; ++j) {
                        for (Tipus_Aula t:tipus_aules) grupMap.put(String.valueOf(i*10 + j), new grup(String.valueOf(i*10+j), capacitat/n_subgrups, (i<=n_matins)? "M" : "T", t));
                    }
                }
            }
        }
        else{
            JSONArray gs = (JSONArray) grups.get("grups");
            for(Object o:gs){
                JSONObject g = (JSONObject) o;
                int nom_grup = ((Long) g.get("nom")).intValue();
                if(nom_grup % 10 != 0 ) throw new Aula_Exception("Nom de grup no valid. Assignatura: " + nom);
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
        return assig;
    }

    private static void afegeixAssignatures(PlaEstudis plaEstudis, JSONArray assignatures) throws Aula_Exception{
        for(Object a: assignatures){
            plaEstudis.addAssignatura(creaAssignatura((JSONObject) a));
        }
    }

    private static void afegeixCorrequisits(PlaEstudis plaEstudis, JSONArray corequisits){
        for(Object c: corequisits){
            JSONObject cc = (JSONObject) c;
            String[] a = new String[2];
            a[0] = (String) cc.get("a1");
            a[1] = (String) cc .get("a2");
            plaEstudis.afegirCorrequisits(a);   //afegim aquests nous correquisits
        }
    }


    private static Map<String, Aula> JSONToAules(JSONArray obj) throws Aula_Exception{
        Map<String, Aula> aules = new TreeMap<>();
        for (Object anObj : obj) {
            JSONObject aulaJ = (JSONObject) anObj;
            String nom = (String) aulaJ.get("nom");
            int capacitat = ((Long) aulaJ.get("capacitat")).intValue();
            if(capacitat < 0) throw new Aula_Exception("La capacitat ha de ser un natural");
            Tipus_Aula tipus = Tipus_Aula.string_to_Tipus_Aula((String) aulaJ.get("tipus"));
            aules.put(nom, new Aula(nom, capacitat, tipus));
        }
        return aules;
    }

    private static Map<String, Aula> llegirCarpetaAulesJSON() {
        Map<String, Aula> aules = new TreeMap<>();
        ArrayList<Object> aularis = ControladorPersistencia.llegirCarpetaAulesJSON();
        for(Object object: aularis){
            try{
                aules.putAll(JSONToAules((JSONArray) object));
            }catch(Aula_Exception ae) {
                System.err.println(ae.getMessage());
                ae.printStackTrace();
            }
        }
        return aules;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Aula> llegirCarpetaAulesSerialized() {
        Map<String, Aula> aules = new TreeMap<>();
        for(Object o: ControladorPersistencia.llegirCarpetaAulesSerialized()) {
            aules.putAll((Map<String, Aula>) o);
        }
        return aules;
    }


    public static Object carrega(String path) throws IOException, ClassNotFoundException {
        return ControladorPersistencia.carrega(path);
    }

    public static void guarda(Object object, String path) throws IOException {
        ControladorPersistencia.guarda(object, path);
    }

    public static void guarda(String path, Object... objects) throws IOException {
        ControladorPersistencia.guarda(objects, path);
    }
}
