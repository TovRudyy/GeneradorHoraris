package testsClasses;

import domain.PlaEstudis;
import domain.Aula;
import persistencia.Lector_Aules;
import persistencia.Lector_Pla;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleksandr, David
 * Date: 17/10/2018
 */

public class test_file_reader {

    public static void main(String[] args) {
        Map<String, Aula> aules = new HashMap<>();
        PlaEstudis plaEstudis;
        try{
            //plaEstudis = Lector_Pla.llegeixPlaEstudis ("C:\\Users\\victo\\IdeaProjects\\GeneradorHoraris\\data\\PlaEstudis\\input_test.txt");
            plaEstudis = Lector_Pla.llegeixPlaEstudis("/home/alumne/IdeaProjects/GeneradorHoraris/data/PlaEstudis/input_test.txt");
        }catch(FileNotFoundException fnfo){
            System.out.println("No existeix el fitxer");
            return;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }
        plaEstudis.showAssignatures();


        try{
            aules = Lector_Aules.llegeixAules ("/home/alumne/IdeaProjects/GeneradorHoraris/data/Aules/input_aules.txt");
        }catch(FileNotFoundException fnfo){
            System.out.println("No existeix el fitxer");
            return;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }

        for(Map.Entry<String, Aula> a : aules.entrySet()){
            System.out.print(a.getValue().toString() + "\n");
        }


    }
}
