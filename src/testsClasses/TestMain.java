package testsClasses;

import domain.*;

import java.util.Scanner;

public class TestMain {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        switch (keyboard.next()){
            case "1":
                ser();
                break;
            case "2":
                deser();
                break;
        }
    }

    private static void ser(){
        Aula aula = new Aula("Aula", 60, Tipus_Aula.TEORIA);
        grup grup = new grup("Grup", 30, "M", Tipus_Aula.TEORIA);
        Classe classe = new Classe("Assig", "10", DiaSetmana.DILLUNS, 8, 10, "Aula");
        try{
            Serialitzador.guarda("data/Array.ser", aula, grup, classe);
            Serialitzador.guarda("data/O.ser", aula);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private static void deser(){
        try{
            Object obj = Serialitzador.carrega("data/O.ser");
            Aula aula = (Aula) obj;
            System.out.println(aula.toString());
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
