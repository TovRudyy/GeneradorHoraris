package testsClasses.Tests;

import domain.Aula_Exception;
import domain.Tipus_Aula;
import domain.grup;
import java.util.Scanner;

public class test_grup {
    public static void main (String [] argv ) {
        Scanner keyboard;

        String id;
        int capacitat;
        keyboard = new Scanner(System.in);
        System.out.println("Introdueix el id del grup, la capacitat i el tipus");
        id = keyboard.nextLine();
        capacitat = keyboard.nextInt();

        Tipus_Aula tipus = null;
        while(tipus == null) {
            String s = keyboard.next();
            try {
                tipus = Tipus_Aula.string_to_Tipus_Aula(s);
            } catch (Aula_Exception ae) {
                System.out.println("Tipus no valid");
            }
        }

        grup a = new grup(id, capacitat, "M", tipus);
        System.out.println(a.getId() +" "+  a.getCapacitat());
        System.out.println (a.getCapacitat());
    }
}
