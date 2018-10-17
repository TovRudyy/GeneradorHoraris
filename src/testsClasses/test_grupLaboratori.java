package testsClasses;

import java.util.Scanner;
import domain.grupLaboratori;
import domain.Tipus_Lab;

public class test_grupLaboratori {
    public static void main (String [] argv) {
        Scanner keyboard;
        String id;
        int capacitat;
        String s; //accedim a la enumeració dels tipus
        Tipus_Lab tipus;

        System.out.println ("Escriu el id,la capacitat i el tipus de grup de laboratori");

        keyboard = new Scanner(System.in);
        id = keyboard.nextLine();
        capacitat = keyboard.nextInt();
        s = keyboard.next();

        if(s.equals("INFORMATICA") | s.equals("I")) tipus = Tipus_Lab.INFORMATICA;
        else if(s.equals("FISICA") | s.equals("F")) tipus =  Tipus_Lab.FISICA;
        else tipus = Tipus_Lab.ELECTRONICA;

        grupLaboratori g = new grupLaboratori (id, capacitat, tipus);
        System.out.println (g.getTipus());

    }
}
