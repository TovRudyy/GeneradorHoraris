package testsClasses;

import data.Capa_Dades;
import domain.PlaEstudis;
import domain.Aula;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class test_file_reader {


    public static void main(String[] args) {
        Map<String, Aula> Aules = new HashMap<>();
        PlaEstudis plaEstudis;
        try{
            plaEstudis = Capa_Dades.llegeixPlaEstudis(Aules, "C:\\Users\\victo\\IdeaProjects\\GeneradorHoraris\\src\\input_test.txt");
        }catch(FileNotFoundException fnfo){
            System.out.println("No existeix el fitxer");
            return;
        }catch(Exception e){
            System.out.println("Format incorrecte");
            return;
        }
        for(Map.Entry<String, Aula> a : Aules.entrySet()){
            System.out.print(a.getValue().toString());
            System.out.println();
        }
        System.out.println(Arrays.toString(plaEstudis.getAssignatures()));
    }
}
