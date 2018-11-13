package testsClasses.Tests;

import domain.Aula;
import domain.PlaEstudis;
import persistencia.Lector_Aules_JSON;
import persistencia.Lector_Pla_JSON;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class tests_main {
    public static void main(String [] args) throws Exception {
        Map<String, Aula> aules = new HashMap<>();
        PlaEstudis plaEstudis;
        try{
            plaEstudis = Lector_Pla_JSON.llegirPlaEstudis ("C:\\Users\\victo\\IdeaProjects\\GeneradorHoraris\\data\\PlaEstudis\\input_test.json");
            //plaEstudis = Lector_Pla_JSON.llegirPlaEstudis("/home/alumne/IdeaProjects/GeneradorHoraris/data/PlaEstudis/input_test.txt");
        }catch(FileNotFoundException fnfo){
            System.out.println("No existeix el fitxer");
            return;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }


        try{
            aules = Lector_Aules_JSON.llegirAules ("C:\\Users\\victo\\IdeaProjects\\GeneradorHoraris\\data\\Aules\\AulariA6.json");
            //aules = Lector_Aules_JSON.llegirAules ("/home/alumne/IdeaProjects/GeneradorHoraris/data/Aules/AulariA6.json");
        }catch(FileNotFoundException fnfo){
            System.out.println("No existeix el fitxer");
            return;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }


        //ara ja ho tenim tot carregat
        plaEstudis.generaHorari(aules);

    }
}
