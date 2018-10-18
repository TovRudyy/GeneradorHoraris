package persistencia;

import domain.Aula;
import domain.Laboratori;
import domain.Tipus_Aula;

import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Lector_Aules {

    public static Map<String, Aula> llegeixAules(String fitxer) throws Exception {
        File file = new File(fitxer);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\\n|:|\\r\\n");
        Map<String, Aula> aules = new TreeMap<>();

        while(scanner.hasNext()){
            String codi = scanner.next();
            int capacitat = scanner.nextInt();
            Tipus_Aula tipus_aula = Capa_Dades.string_to_Tipus_Aula(scanner.next());
            if(tipus_aula.equals(Tipus_Aula.LAB)) aules.put(codi, new Laboratori(codi, capacitat, Capa_Dades.string_to_Tipus_Lab(scanner.next())));
            else aules.put(codi, new Aula(codi, capacitat, tipus_aula));
        }
        return aules;
    }

    public static void afegeixAules(Map<String, Aula> aules, String fitxer) throws Exception {
        aules.putAll(llegeixAules(fitxer));
    }
}
