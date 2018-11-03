package testsClasses;

import domain.Classe;
import domain.DiaSetmana;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class driverClasse  {
    public static void main (String argv[]) {
        Classe a = null;
        try {
            System.out.println("Driver Classe");
            System.out.println("Opcions:");
            System.out.println("\t 1) Creadora Classe()");
            System.out.println("\t       input: 1 <String>, <String>, <DiaSetmana>, <int>, <int>, <String>");

            System.out.println("\t 2) Retorna el Id de la assignatura()");
            System.out.println("\t       input: 2");

            System.out.println("\t 3) Retorna el Id del grup()");
            System.out.println("\t       input: 2");

            System.out.println("\t 4) Retorna el dia de la Setmana()");
            System.out.println("\t       input: 2");

            System.out.println("\t 5) Retorna la hora d'inici()");
            System.out.println("\t       input: 2");

            System.out.println("\t 6) Retorna la hora fi()");
            System.out.println("\t       input: 3");

            System.out.println("\t 7) Retorna el id de l'aula()");
            System.out.println("\t       input: 4");

            System.out.println("\t 5) Retorna la durada()");
            System.out.println("\t       input: 5");

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
                            a = new Classe(line[1], line[2], DiaSetmana.valueOf(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5]), line[6]);
                            System.out.println("Creada correctament");
                        } catch (Exception e) {
                        }
                        break;

                    case "2":
                        System.out.println(a.getId_assig());
                        break;

                    case "3":
                        System.out.println(a.getId_grup());
                        break;

                    case "4":
                        System.out.println(a.getDia());
                        break;

                    case "5":
                        System.out.println(a.getHoraInici());
                        break;

                    case "6":
                        System.out.println(a.getHoraFi());
                        break;

                    case "7":
                        System.out.println(a.getDurada());
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
        catch (IOException e){
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            System.out.println("No hem inicialitzat una aula");
        }

    }


}