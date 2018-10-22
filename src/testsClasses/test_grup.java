package testsClasses;

import domain.grup;
import java.util.Scanner;

public class test_grup {
    public static void main (String [] argv ) {
        Scanner keyboard;

        String id;
        int capacitat;
        keyboard = new Scanner(System.in);
        System.out.println("Introdueix el id del grup i la capacitat");
        id = keyboard.nextLine();
        capacitat = keyboard.nextInt();

        grup a = new grup(id, capacitat, "M");
        System.out.println(a.getId() +" "+  a.getCapacitat());
        System.out.println (a.getCapacitat());
    }
}
