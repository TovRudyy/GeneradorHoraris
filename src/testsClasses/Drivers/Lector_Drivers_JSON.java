package testsClasses.Drivers;

import domain.Classe;
import domain.DiaSetmana;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class Lector_Drivers_JSON {

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
}
