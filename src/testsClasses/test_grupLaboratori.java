package testsClasses;

import domain.grupLaboratori;
import java.util.Scanner;


public class test_grupLaboratori {
    public static void main (String [] argv) {
        Scanner keyboard;
        String id;
        int capacitat;
        String tipus; //accedim a la enumeració dels tipus

        System.out.println ("Escriu el id,la capacitat i el tipus de grup de laboratori");

        keyboard = new Scanner(System.in);
        id = keyboard.nextLine();
        capacitat = keyboard.nextInt();
        tipus = keyboard.next();

        grupLaboratori g = new grupLaboratori(id, capacitat, tipus);
        System.out.println (g.getTipus());

    }
}
