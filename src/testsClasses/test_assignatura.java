package testsClasses;

import domain.assignatura;
import java.util.Scanner;

public class test_assignatura {
    public static void main (String [] args) {
        Scanner keyboard;

        String id, nom;
        int nivell;
        keyboard = new Scanner(System.in);
        System.out.println ("Introdueix el id de la assignatura, el nom i el nivell");
        id = keyboard.nextLine();
        nom = keyboard.nextLine();
        nivell = keyboard.nextInt();

        assignatura a = new assignatura(id, nom, nivell);
        System.out.println(a.getId() + " "+ a.getNom() + " " + a.getNivell());

       // a.setClassesLaboratori(2,0.45);
    }


}
