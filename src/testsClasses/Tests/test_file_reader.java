package testsClasses.Tests;

import domain.Aula_Exception;
import domain.PlaEstudis;
import domain.Aula;
import persistencia.Lector_Aules_JSON;
import persistencia.Lector_Pla_JSON;

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
            plaEstudis = Lector_Pla_JSON.llegirPlaEstudis ("C:\\Users\\victo\\IdeaProjects\\GeneradorHoraris\\data\\PlaEstudis\\input_test.json");
            //plaEstudis = Lector_Pla.llegeixPlaEstudis("/home/alumne/IdeaProjects/GeneradorHoraris/data/PlaEstudis/input_test.json");
        }catch(FileNotFoundException fnfo){
            System.out.println("No existeix el fitxer");
            return;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }
        plaEstudis.showAssignatures();


        try{
            //aules = Lector_Aules_JSON.llegirCarpetaAules ("/home/alumne/IdeaProjects/GeneradorHoraris/data/Aules/input_aules.txt");
            aules = Lector_Aules_JSON.llegirCarpetaAules();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        for(Map.Entry<String, Aula> a : aules.entrySet()){
            System.out.print(a.getValue().toString() + "\n");
        }


    }
}
