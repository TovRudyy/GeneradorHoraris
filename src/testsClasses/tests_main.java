package testsClasses;

import domain.PlaEstudis;
import persistencia.Lector_Aules;

import java.util.Scanner;

public class tests_main {
    public static void main(String [] args) throws Exception {
        Scanner keyboard;
        String input, id;
        PlaEstudis pe;

        keyboard = new Scanner(System.in);
        System.out.println("Introdueix un acrònim de pla d'estudis");
        input = keyboard.nextLine();
        pe = new PlaEstudis(input);

        Lector_Aules.readFolderAules();
    }
}
