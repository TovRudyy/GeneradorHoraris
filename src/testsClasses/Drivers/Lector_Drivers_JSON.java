package testsClasses.Drivers;

import domain.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Victor
 */

class Lector_Drivers_JSON {

    /**
     * @return Un map amb totes les Restriccions de ocupacio llegides pel fitxer.
     * @throws ParseException
     * @throws IOException
     */
    static Map<String, Map<DiaSetmana, ArrayList<Classe>>> llegirFitxer_RestriccioOcupacio_InputMap() throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        Map<String, Map<DiaSetmana, ArrayList<Classe>>> classes = new TreeMap<>();
        JSONArray array = (JSONArray) parser.parse(new FileReader("data/Drivers_Input/RestriccioOcupacio_InputMap.json"));
        for(Object o : array){
            JSONObject obj = (JSONObject) o;
            String aula = (String) obj.get("key");
            classes.put(aula, new TreeMap<>());
            for(Object d : (JSONArray) obj.get("value")){
                JSONObject dia = (JSONObject) d;
                DiaSetmana diaSetmana = DiaSetmana.string_To_DiaSetmana((String) dia.get("key"));
                classes.get(aula).put(diaSetmana, new ArrayList<>());
                for(Object c : (JSONArray) dia.get("value")){
                    JSONObject classe = (JSONObject) c;
                    classes.get(aula).get(diaSetmana).add(new Classe((String) classe.get("id_assig"), String.valueOf(classe.get("id_grup")), diaSetmana, ((Long) classe.get("hora_inici")).intValue(), ((Long) classe.get("hora_fi")).intValue(), aula));
                }
            }
        }
        return classes;
    }

    /**
     * @return Un map amb totes les Restriccions de subgrup llegides pel fitxer.
     * @throws ParseException
     * @throws IOException
     */
    static Map<String, Map<DiaSetmana, ArrayList<Classe>>> llegirFitxer_RestriccioSubgrup_InputMap() throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        Map<String, Map<DiaSetmana, ArrayList<Classe>>> classes = new TreeMap<>();
        JSONArray array = (JSONArray) parser.parse(new FileReader("data/Drivers_Input/RestriccioSubgrup_InputMap.json"));
        for(Object o : array){
            JSONObject obj = (JSONObject) o;
            String aula = (String) obj.get("key");
            classes.put(aula, new TreeMap<>());
            for(Object d : (JSONArray) obj.get("value")){
                JSONObject dia = (JSONObject) d;
                DiaSetmana diaSetmana = DiaSetmana.string_To_DiaSetmana((String) dia.get("key"));
                classes.get(aula).put(diaSetmana, new ArrayList<>());
                for(Object c : (JSONArray) dia.get("value")){
                    JSONObject classe = (JSONObject) c;

                    classes.get(aula).get(diaSetmana).add(new Classe((String) classe.get("id_assig"), String.valueOf(classe.get("id_grup")), diaSetmana, ((Long) classe.get("hora_inici")).intValue(), ((Long) classe.get("hora_fi")).intValue(), aula));
                }
            }
        }
        return classes;
    }

    /**
     * @return Un map amb totes les Restriccions de correquisit llegides pel fitxer.
     * @throws ParseException
     * @throws IOException
     */
    static Map<String, Map<DiaSetmana, ArrayList<Classe>>> llegirFitxer_RestriccioCorequisit_InputMap() throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        Map<String, Map<DiaSetmana, ArrayList<Classe>>> classes = new TreeMap<>();
        JSONArray array = (JSONArray) parser.parse(new FileReader("data/Drivers_Input/RestriccioCorequisit_InputMap.json"));
        for(Object o : array){
            JSONObject obj = (JSONObject) o;
            String aula = (String) obj.get("key");
            classes.put(aula, new TreeMap<>());
            for(Object d : (JSONArray) obj.get("value")){
                JSONObject dia = (JSONObject) d;
                DiaSetmana diaSetmana = DiaSetmana.string_To_DiaSetmana((String) dia.get("key"));
                classes.get(aula).put(diaSetmana, new ArrayList<>());
                for(Object c : (JSONArray) dia.get("value")){
                    JSONObject classe = (JSONObject) c;

                    classes.get(aula).get(diaSetmana).add(new Classe((String) classe.get("id_assig"), String.valueOf(classe.get("id_grup")), diaSetmana, ((Long) classe.get("hora_inici")).intValue(), ((Long) classe.get("hora_fi")).intValue(), aula));
                }
            }
        }
        return classes;
    }

    /**
     * @return Una arrayList amb les assignacions resultants de processar un fitxer amb tota la informacio necessaria.
     * @throws ParseException
     * @throws IOException
     * @throws Aula_Exception
     */
    static ArrayList<assignacio> llegitFitxer_Horari_InputList() throws ParseException, IOException, Aula_Exception {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(new FileReader("data/Drivers_Input/Horari_InputList.json"));
        JSONArray assignatures = (JSONArray) obj.get("assignatures");
        Map<String, assignatura> assigs;
        if(assignatures == null) throw new IOException("No hi ha assignatures");
        assigs = afegeixAssignatures(assignatures);
        JSONArray corequisits = (JSONArray) obj.get("corequisits");
        if(corequisits != null) afegeixCorrequisits(corequisits, assigs);
        Map<String, Aula> aules;
        JSONArray au = (JSONArray) obj.get("aules");
        if(au == null)  throw new IOException("No hi ha aules");
        aules = crearAules(au);

        ArrayList<assignacio> assignacions = new ArrayList<>();
        for(assignatura a: assigs.values()){
            assignacions.addAll(a.getAssignacions(aules));
        }
        return assignacions;
    }

    /**
     * Crea una assignatura.
     * @param ass Un objecte JSON amb la informacio
     * @return La assignatura creada
     * @throws Aula_Exception
     */
    private static assignatura creaAssignatura(JSONObject ass) throws Aula_Exception {
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

    /**
     * Llegeix un fitxer amb assignatures i crea un map amb aquestes.
     * @param assignatures Un objecte JSON on conte les diferents assignatures llegides.
     * @return Un map amb les diferents assignatures creades.
     * @throws Aula_Exception
     */
    private static Map<String, assignatura> afegeixAssignatures(JSONArray assignatures) throws Aula_Exception{
        Map<String, assignatura> ass = new TreeMap<>();
        for(Object a: assignatures){
            assignatura as = creaAssignatura((JSONObject) a);
            as.noSolapis_Teoria_i_Problemes();
            ass.put(as.getId(), as);
        }
        return ass;
    }

    /**
     * Afegeix els correquisits corresponents a les assignatures pertinents.
     * @param corequisits Un objecte amb els correquisits llegits.
     * @param assigs El map amb les diferents assignatures.
     */
    private static void afegeixCorrequisits(JSONArray corequisits, Map<String, assignatura> assigs){
        for(Object c: corequisits){
            JSONObject cc = (JSONObject) c;
            String[] a = new String[2];
            a[0] = (String) cc.get("a1");
            a[1] = (String) cc .get("a2");
            assigs.get(a[0]).addCorrequisit(a[1]);
            assigs.get(a[1]).addCorrequisit(a[0]);
        }
    }

    /**
     * Llegeix la informacio de les aules i crea les instancies correctament.
     * @param obj Un objecte JSON amb la informacio de les aules.
     * @return Un map amb les diferents aules creades.
     * @throws Aula_Exception
     */
    private static Map<String, Aula> crearAules(JSONArray obj) throws Aula_Exception{
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

    /**
     * Afegeix un conjunt de correquisits a un pla d'estudis.
     * @param plaEstudis El pla d'estudis que tractem
     * @param corequisits La array amb tots els correquisits que volem afegir.
     */
    private static void afegeixCorrequisits2(PlaEstudis plaEstudis, JSONArray corequisits){
        for(Object c: corequisits){
            JSONObject cc = (JSONObject) c;
            String[] a = new String[2];
            a[0] = (String) cc.get("a1");
            a[1] = (String) cc .get("a2");
            plaEstudis.afegirCorrequisits(a);   //afegim aquests nous correquisits
        }
    }

    /**
     * Llegeix un pla d'estudis i crea la instancia corresponent.
     * @return El pla d'estudis corresponent.
     * @throws ParseException
     * @throws IOException
     * @throws Aula_Exception
     */
    static PlaEstudis llegirPlaEstudis() throws ParseException, IOException, Aula_Exception {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(new FileReader("data/Drivers_Input/PlaEstudis_InputPla.json"));
        PlaEstudis plaEstudis = new PlaEstudis((String) obj.get("nom"));
        JSONArray assignatures = (JSONArray) obj.get("assignatures");
        if(assignatures != null) for(assignatura a: afegeixAssignatures(assignatures).values()) plaEstudis.addAssignatura(a);
        JSONArray corequisits = (JSONArray) obj.get("corequisits");
        if(corequisits != null) afegeixCorrequisits2(plaEstudis, corequisits);

        return plaEstudis;
    }

    /**
     * Llegeix un conjunt d'aules i crea un map amb les diferents instancies.
     * @return El map amb les instancies creades corresponents.
     * @throws ParseException
     * @throws IOException
     * @throws Aula_Exception
     */
    static Map<String, Aula> llegirAules() throws ParseException, IOException, Aula_Exception{
        Map<String, Aula> aules = new TreeMap<>();
        JSONParser parser = new JSONParser();
        JSONArray obj = (JSONArray) parser.parse(new FileReader("data/Drivers_Input/PlaEstudis_InputAules.json"));
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
}
