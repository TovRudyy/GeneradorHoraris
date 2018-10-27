package testsClasses;

import domain.Aula;
import domain.PlaEstudis;
import persistencia.Lector_Aules;
import persistencia.Lector_Pla;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class tests_main {
    public static void main(String [] args) throws Exception {
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


        try{
            aules = Lector_Aules.llegeixAules ("/home/alumne/IdeaProjects/GeneradorHoraris/data/Aules/input_aules.txt");
        }catch(FileNotFoundException fnfo){
            System.out.println("No existeix el fitxer");
            return;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }


        //ara ja ho tenim tot carregat
        plaEstudis.generaHorari();

    }
}
