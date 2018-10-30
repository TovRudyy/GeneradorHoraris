package persistencia;

import domain.Aula;
import domain.Aula_Exception;
import domain.Laboratori;
import domain.Tipus_Aula;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Lector_Aules_JSON {

    public static Map<String, Aula> llegirAules(String fitxer) throws ParseException, IOException, Aula_Exception{
        Map<String, Aula> aules = new TreeMap<>();
        JSONParser parser = new JSONParser();
        JSONArray obj = (JSONArray) parser.parse(new FileReader(fitxer));
        for (Object anObj : obj) {
            JSONObject aulaJ = (JSONObject) anObj;
            String nom = (String) aulaJ.get("nom");
            int capacitat = ((Long) aulaJ.get("capacitat")).intValue();
            Tipus_Aula tipus = Capa_Dades.string_to_Tipus_Aula((String) aulaJ.get("tipus"));
            if(tipus.equals(Tipus_Aula.LAB)) aules.put(nom, new Laboratori(nom, capacitat, Capa_Dades.string_to_Tipus_Lab((String) aulaJ.get("tipus_lab"))));
            else aules.put(nom, new Aula(nom, capacitat, tipus));
        }
        return aules;
    }

    public static void afegirAules(Map<String, Aula>aules, String fitxer) throws  ParseException, IOException, Aula_Exception{
        aules.putAll(llegirAules(fitxer));
    }

    public static Map<String, Aula> llegirCarpetaAules()  throws  ParseException, IOException, Aula_Exception{
        File AUfolder = new File("data/Aules");
        if (!AUfolder.isDirectory()) throw new Aula_Exception("Error with data/Aules folder!");
        Map<String, Aula> aules = new TreeMap<>();
        for (File file : Objects.requireNonNull(AUfolder.listFiles())) {
            System.out.println("The following Aules file is being read: " + "'" + file.getAbsolutePath());
            try{
                afegirAules(aules,file.getAbsolutePath());
            }catch(Exception e){
                System.out.println("El fitxer " + file.toString() + " no s'ha pogut llegir");
                System.out.println("Error: " + e.getMessage());
            }
        }
        return aules;
    }

}
