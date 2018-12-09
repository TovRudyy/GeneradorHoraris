package testsClasses;

import domain.Aula;
import persistencia.Lector_Aules_JSON;
import persistencia.Serializer;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class TestMain {

    public static void main(String[] args) {
        System.out.println("Serialize: 1");
        System.out.println("Deserialize: 2");
        Scanner keyboard = new Scanner(System.in);
        String codi;
        while(keyboard.hasNext()){
            switch (keyboard.next()){
                case "1":
                    ser();
                    break;
                case "2":
                    deser();
                    break;
                default:
                    System.out.println("Error");
            }
            System.out.println("Serialize: 1");
            System.out.println("Deserialize: 2");
        }
    }

    private static void ser(){
        try{
            Map<String, Aula> aules = Lector_Aules_JSON.llegirCarpetaAules();
            for(Aula aula: aules.values()) System.out.println(aula.toString());
            Serializer<Map<String, Aula>> serializer = new Serializer<>();
            serializer.serialize(aules, "data/Serialized/AulesMap.ser");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void deser(){
        Serializer<Map<String, Aula>> serializer = new Serializer<>();
        try{
            Map<String, Aula> aules = serializer.deserialize("data/Serialized/AulesMap.ser");
            for(Aula aula: aules.values()) System.out.println(aula.toString());
        }catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
