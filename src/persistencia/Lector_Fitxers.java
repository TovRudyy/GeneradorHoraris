package persistencia;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

class Lector_Fitxers {

    private Lector_Fitxers() {
    }

    static ArrayList<Object> llegirCarpetaPlansJSON() {
        File PEfolder = new File("data/PlaEstudis");
        if (!PEfolder.isDirectory()) return new ArrayList<>();
        ArrayList<Object> plansEstudis = new ArrayList<>();
        JSONParser parser = new JSONParser();
        for (File pe_file : Objects.requireNonNull(PEfolder.listFiles())) {
            String path = pe_file.getAbsolutePath();
            if(!path.substring(path.lastIndexOf('.') + 1).equals("json")) continue;
            try{
                plansEstudis.add(parser.parse(new FileReader(path)));
            }catch(IOException | ParseException e){
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return plansEstudis;
    }

    static ArrayList<Object> llegirCarpetaPlansSerialized() {
        File PEfolder = new File("data/PlaEstudis");
        if (!PEfolder.isDirectory()) return new ArrayList<>();
        ArrayList<Object> plansEstudis = new ArrayList<>();
        for (File pe_file : Objects.requireNonNull(PEfolder.listFiles())) {
            String path = pe_file.getAbsolutePath();
            if(!path.substring(path.lastIndexOf('.') + 1).equals("ser")) continue;
            try{
                plansEstudis.add(Serialitzador.deserialize(pe_file.getAbsolutePath()));
            }catch(IOException | ClassNotFoundException e){
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return plansEstudis;
    }

    static Object llegirJSON(String path) throws IOException, ParseException {
        return new JSONParser().parse(new FileReader(path));
    }

    static ArrayList<Object> llegirCarpetaAulesJSON() {
        File AUfolder = new File("data/Aules");
        if (!AUfolder.isDirectory()) return new ArrayList<>();
        ArrayList<Object> aularis = new ArrayList<>();
        JSONParser parser = new JSONParser();
        for(File au_file : Objects.requireNonNull(AUfolder.listFiles())) {
            String path = au_file.getAbsolutePath();
            if(!path.substring(path.lastIndexOf('.') + 1).equals("json")) continue;
            try{
                aularis.add(parser.parse(new FileReader(path)));
            }catch(IOException | ParseException e){
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return aularis;
    }

    static ArrayList<Object> llegirCarpetaAulesSerialized() {
        File AUfolder = new File("data/Aules");
        if (!AUfolder.isDirectory()) return new ArrayList<>();
        ArrayList<Object> aularis = new ArrayList<>();
        for (File au_file : Objects.requireNonNull(AUfolder.listFiles())) {
            String path = au_file.getAbsolutePath();
            if(!path.substring(path.lastIndexOf('.') + 1).equals("ser")) continue;
            try{
                aularis.add(Serialitzador.deserialize(au_file.getAbsolutePath()));
            }catch(IOException | ClassNotFoundException e){
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return aularis;
    }

}
