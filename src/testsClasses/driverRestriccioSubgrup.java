package testsClasses;

import domain.RestriccioSubgrup;
import testsClasses.stubRestriccioSubgrup;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class driverRestriccioSubgrup  {
    public static void main (String argv[]) {
        RestriccioSubgrup a = null;
        stubRestriccioSubgrup q = new stubRestriccioSubgrup();

        try {
            System.out.println("Driver RestriccioSubgrup");
            System.out.println("Opcions:");
            System.out.println("\t 1) Creadora Classe()");
            System.out.println("\t       input: 1 (S'agafarà un exemple de grup que faci de grup de teoria)");

            System.out.println("\t 2) Comprova si la ultima classe afegida segueix complint la restriccio amb les altres");
            System.out.println("\t       input: 2");

            System.out.println("\t 3) Sortir");


            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            String linia_llegida;
            boolean sortir = false;
            linia_llegida = buffer.readLine();

            while (linia_llegida != null && !sortir) {
                String[] line = linia_llegida.split(" ");   //quan trobi un espai fer un split

                switch (line[0]) {  //line[0] ens indicarà el numero de la opcio
                    case "1":
                        try {
                            a = new RestriccioSubgrup(q.getGrupTeoria());
                            System.out.println("Creada correctament");
                        } catch (Exception e) {
                        }
                        break;

                    case "2":
                        boolean r = a.checkRestriccio(q.getResultatParcialFals());
                        System.out.println("PROVEM EL PRIMER TEST: El resultat hauria de ser false ja que dos classes es solapen");
                        System.out.println("Resultat: " + r);

                        r = a.checkRestriccio(q.getResultatParcialCert());
                        System.out.println("PROVEM EL SEGON TEST: El resultat hauria de ser true ja que no hi ha cap classe que es solapi");
                        System.out.println("Resultat: " + r);

                        break;

                    case "3":
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


}