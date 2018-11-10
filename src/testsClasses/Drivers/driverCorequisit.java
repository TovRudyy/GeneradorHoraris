package testsClasses.Drivers;

import domain.Corequisit;
import testsClasses.stubCorequisit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

public class driverCorequisit  {


    static void main () {
        Corequisit a = null;
        stubCorequisit q = new stubCorequisit();

        try {
            System.out.println("Driver Corequisit");
            System.out.println("Opcions:");
            System.out.println("\t 1) Creadora Classe()");
            System.out.println("\t       input: 1 (S'agafarà un exemple de grup que faci de grup de teoria)");

            System.out.println("\t 2) Afegeix una assignatura corequisit");
            System.out.println("\t       input: 2 <String>");

            System.out.println("\t 3) Retorna les assignatures amb les que es corequisit");
            System.out.println("\t       input: 3");

            System.out.println("\t 4) Comprova si la ultima classe afegida segueix complint la restriccio amb les altres");
            System.out.println("\t       input: 4");

            System.out.println("\t 5) Sortir");


            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            String linia_llegida;
            boolean sortir = false;
            linia_llegida = buffer.readLine();

            while (linia_llegida != null && !sortir) {
                String[] line = linia_llegida.split(" ");   //quan trobi un espai fer un split

                switch (line[0]) {  //line[0] ens indicarà el numero de la opcio
                    case "1":
                        try {
                            a = new Corequisit();
                            System.out.println("Creada correctament");
                        } catch (Exception e) {
                        }
                        break;

                    case "2":
                        a.addAssignatura(line[1]);
                        showAssignatures(a.getAssignatures());
                        break;

                    case "3":
                        showAssignatures (a.getAssignatures());
                        break;

                    case "4":
                        break;

                    case "5":
                        sortir = true;
                        break;

                    default:
                        System.out.println("El numero de opcio triada es incorrecte");
                        break;
                }

                if (!sortir) linia_llegida = buffer.readLine();
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            System.out.println("No hem inicialitzat la restriccio");
        }

    }

    private static void showAssignatures (ArrayList <String> assig) {
        for (int i= 0; i < assig.size(); ++i)
            System.out.println("Assignatura " + i + " : " + assig.get(i));
    }



}