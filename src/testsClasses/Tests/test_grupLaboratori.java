package testsClasses.Tests;

import java.util.Scanner;

import domain.*;

public class test_grupLaboratori {
    public static void main (String [] argv) {
        Scanner keyboard;
        String id;
        int capacitat;
        String s; //accedim a la enumeració dels tipus
        Tipus_Aula tipus = null;

        System.out.println ("Escriu el id,la capacitat i el tipus de grup de laboratori");

        keyboard = new Scanner(System.in);
        id = keyboard.nextLine();
        capacitat = keyboard.nextInt();
        while(tipus == null) {
            s = keyboard.next();
            try {
                tipus = Tipus_Aula.string_to_Tipus_Aula(s);
            } catch (Aula_Exception ae) {
                System.out.println("Tipus no valid");
            }
        }

        grup g = new grup(id, capacitat, "M", tipus);
        System.out.println (g.getTipus());

    }
}
