package persistencia;

import domain.Aula;
import domain.Aula_Exception;
import domain.Tipus_Aula;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.naming.AuthenticationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author Victor Divi
 */

public final class Lector_Aules_JSON {

    private Lector_Aules_JSON() {
    }

    private static Map<String, Aula> aules_dades = new TreeMap<>();

    public static Map<String, Aula> getAules() {
        return aules_dades;
    }

    /**
     * Llegeix de un fitxer un conjunt d'aules i les instancia en format map.
     * @param fitxer Ruta del fitxer
     * @return Un map amb el conjunt de aules llegit.
     * @throws ParseException
     * @throws IOException
     * @throws Aula_Exception
     */
    public static Map<String, Aula> llegirAules(String fitxer) throws ParseException, IOException, Aula_Exception{
        Map<String, Aula> aules = new TreeMap<>();
        JSONParser parser = new JSONParser();
        JSONArray obj = (JSONArray) parser.parse(new FileReader(fitxer));
        for (Object anObj : obj) {
            JSONObject aulaJ = (JSONObject) anObj;
            String nom = (String) aulaJ.get("nom");
            int capacitat = ((Long) aulaJ.get("capacitat")).intValue();
            if(capacitat < 0) throw new Aula_Exception("La capacitat ha de ser un natural");
            Tipus_Aula tipus = Tipus_Aula.string_to_Tipus_Aula((String) aulaJ.get("tipus"));
            aules.put(nom, new Aula(nom, capacitat, tipus));
        }
        aules_dades.putAll(aules);
        return aules;
    }

    /**
     * Afegeix les aules del fitxer al map passat per parametre.
     * @param aules Conjunt preexistent d'aules
     * @param fitxer Ruta al fitxer
     * @throws ParseException Excepcio de Parse.
     * @throws IOException Excepcio input/output (a la lectura)
     * @throws Aula_Exception Excepcio de aula.
     */
    public static void afegirAules(Map<String, Aula>aules, String fitxer) throws  ParseException, IOException, Aula_Exception{
        aules.putAll(llegirAules(fitxer));
    }

    /**
     * Llegeix la carpeta preestablerta d'aules i les instancia en un map.
     * @return Un map amb les instancies de aula creades.
     * @throws Aula_Exception
     */
    public static Map<String, Aula> llegirCarpetaAules()  throws Aula_Exception{
        File AUfolder = new File("data/Aules");
        if (!AUfolder.isDirectory()) throw new Aula_Exception("Error with data/Aules folder!");
        Map<String, Aula> aules = new TreeMap<>();
        for (File file : Objects.requireNonNull(AUfolder.listFiles())) {
            System.err.println("DEGUB: The following Aules file is being read: " + "'" + file.getAbsolutePath());
            try{
                afegirAules(aules,file.getAbsolutePath());
            }catch(Exception e){
                System.err.println("DEGUB: El fitxer " + file.toString() + " no s'ha pogut llegir");
                System.err.println("Error: " + e.getMessage());
            }
        }
        return aules;
    }

}
