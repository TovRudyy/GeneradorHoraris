package persistencia;

import domain.Aula;
import domain.Laboratori;
import domain.Tipus_Aula;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Lector_Aules {

    private static ArrayList<Map<String, Aula>> aularis = new ArrayList<Map<String, Aula>>();

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

    public static Map<String, Aula> readFolderAules() throws Exception {
        File AUfolder = new File("data/Aules");
        if (!AUfolder.isDirectory()) {
            throw new Exception("Error with data/Aules folder!");
        }

        Map<String, Aula> aules = new TreeMap<>();
        File[] Aules = AUfolder.listFiles();
        for (File au_file : Aules) {
            System.out.println("The following Aules file is being readed: " + "'" + AUfolder.getAbsolutePath());
            Map<String, Aula> aula_aux = llegeixAules(au_file.getAbsolutePath());
            if (!aularis.add(aula_aux)) {
                throw new Exception("Cannot read '" + au_file.getName() + "' file");
            }
            afegeixAules(aules, au_file.getAbsolutePath());
        }
        return aules;
    }
}
