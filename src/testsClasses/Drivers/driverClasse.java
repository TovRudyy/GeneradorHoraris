package testsClasses.Drivers;

import domain.Classe;
import domain.DiaSetmana;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import static java.lang.System.out;


class driverClasse  {
    static void main2 () {
        Classe a = null;
        try {
            out.println("Driver Classe");
            out.println("Opcions:");
            out.println("\t 1) Creadora Classe()");
            out.println("\t       input: 1 <String>, <String>, <DiaSetmana>, <int>, <int>, <String>");

            out.println("\t 2) Retorna el Id de la assignatura()");
            out.println("\t       input: 2");

            out.println("\t 3) Retorna el Id del grup()");
            out.println("\t       input: 2");

            out.println("\t 4) Retorna el dia de la Setmana()");
            out.println("\t       input: 2");

            out.println("\t 5) Retorna la hora d'inici()");
            out.println("\t       input: 2");

            out.println("\t 6) Retorna la hora fi()");
            out.println("\t       input: 3");

            out.println("\t 7) Retorna el id de l'aula()");
            out.println("\t       input: 4");

            out.println("\t 5) Retorna la durada()");
            out.println("\t       input: 5");

            out.println("\t 8) Sortir");


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
                            out.println("Creada correctament");
                        } catch (Exception e) {
                        }
                        break;

                    case "2":
                        out.println(a.getId_assig());
                        break;

                    case "3":
                        out.println(a.getId_grup());
                        break;

                    case "4":
                        out.println(a.getDia());
                        break;

                    case "5":
                        out.println(a.getHoraInici());
                        break;

                    case "6":
                        out.println(a.getHoraFi());
                        break;

                    case "7":
                        out.println(a.getDurada());
                        break;

                    case "8":
                        sortir = true;
                        break;

                    default:
                        out.println("El numero de opcio triada es incorrecte");
                        break;
                }

                if (!sortir) linia_llegida = buffer.readLine();
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            out.println("No hem inicialitzat una aula");
        }

    }

    static void main() {
        printMenu();
        executar();
    }

    private static void printMenu() {
        out.println("Driver de la Classe Classe.");
        out.println("Selecciona la funcio que vols provar");

    }

    private static void printCodis() {

    }

    private static void executar() {

    }


}