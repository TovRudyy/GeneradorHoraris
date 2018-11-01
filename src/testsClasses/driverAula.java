package testsClasses;

import domain.Aula;
import domain.Tipus_Aula;
import domain.Aula_Exception;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class driverAula  {
    public static void main (String argv[]) {
        Aula a = null;
        try {
            System.out.println("Driver Aula");
            System.out.println("Opcions:");
            System.out.println("\t 1) Creadora Aula()");
            System.out.println("\t       input: 1 <String>, <int>, <TipusAula>");

            System.out.println("\t 2) Retorna el Id()");
            System.out.println("\t       input: 2");

            System.out.println("\t 3) Modifica el Id()");
            System.out.println("\t       input: 3 <String>");

            System.out.println("\t 4) Retorna capacitat()");
            System.out.println("\t       input: 4");

            System.out.println("\t 5) Modifica capacitat()");
            System.out.println("\t       input: 5 <int>");

            System.out.println("\t 6) Retorna el tipus de aula()");
            System.out.println("\t       input: 5");

            System.out.println("\t 7) Modifica el tipus de aula()");
            System.out.println("\t       input: 5 <int>");

            System.out.println("\t 8) Sortir");


            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            String linia_llegida;
            boolean sortir = false;
            linia_llegida = buffer.readLine();

            while (linia_llegida != null && !sortir) {
                String[] line = linia_llegida.split(" ");   //quan trobi un espai fer un split

                switch (line[0]) {  //line[0] ens indicarà el numero de la opcio
                    case "1":
                        try {
                            a = new Aula(line[1], Integer.parseInt(line[2]), Tipus_Aula.valueOf(line[3]));
                            System.out.println("Creada correctament");
                        } catch (Exception e) {
                        }
                        break;

                    case "2":
                        System.out.println(a.getId());
                        break;
                    case "3":
                        a.setId(line[1]);
                        System.out.println("El nou id es: " + a.getId());
                        break;

                    case "4":
                        System.out.println(a.getCapacitat());
                        break;

                    case "5":
                        a.setCapacitat(Integer.parseInt(line[1]));
                        System.out.println("La nova capacitat es: " + a.getCapacitat());
                        break;

                    case "6":
                        System.out.println(a.getTipus());
                        break;

                    case "7":
                        a.setTipus(Tipus_Aula.string_to_Tipus_Aula(line[1]));
                        System.out.println("El nou tipus es: " + a.getTipus());
                        break;

                    case "8":
                        sortir = true;
                        break;

                    default:
                        System.out.println("El numero de opcio triada es incorrecte");
                        break;
                }

                if (!sortir) linia_llegida = buffer.readLine();
            }

        }
        catch (IOException | Aula_Exception e){
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            System.out.println("No hem inicialitzat una aula");
        }

        }


}
