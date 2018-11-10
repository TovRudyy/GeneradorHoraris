package testsClasses.Drivers;

import domain.Aula_Exception;
import domain.Tipus_Aula;
import domain.grup;

import java.util.Scanner;

public class driverGrup {
    static void main() {
        grup g = null;

        System.out.println("Driver grup:");
        System.out.println("Opcions:");
        System.out.println("\t1) Constructora:");
        System.out.println("\t\tinput: 1 Id<int> Capacitat<int> Horari<M | T | MT> Tipus<Tipus_Aula>");

        System.out.println("\t2) Getter id:");
        System.out.println("\t\tinput: 2");

        System.out.println("\t3) Getter capacitat:");
        System.out.println("\t\tinput: 3");

        System.out.println("\t4) Getter horari:");
        System.out.println("\t\tinput: 4");

        System.out.println("\t5) Getter tipus:");
        System.out.println("\t\tinput: 5");

        System.out.println("\t6) Getter subgrup:");
        System.out.println("\t\tinput: 6");

        System.out.println("\t7) Consultora subgrup:");
        System.out.println("\t\tinput: 7 IdGrup<int>");

        System.out.println("\t8) Setter subgrup:");
        System.out.println("\t\tinput: 8 idPare<int> CapacitatPare<int> HorariPare<M | T | MT> TipusPare<Tipus_Aula>");

        System.out.println("\t9) Sortir:");
        System.out.println("\t\tinput: 9");

        System.out.println("Introdueix un codi:");

        Scanner keyboard = new Scanner(System.in);
        int codi = keyboard.nextInt();
        String id=null, horari=null;
        int capacitat = -1;
        Tipus_Aula tipus=null;
        while(codi!=9){
            try {
                switch (codi) {
                    case 1:
                        id = keyboard.next();
                        capacitat = keyboard.nextInt();
                        horari = keyboard.next();
                        tipus = Tipus_Aula.string_to_Tipus_Aula(keyboard.next());
                        g = new grup(id, capacitat, horari, tipus);

                        System.out.println("Grup esperat: " + id + ":" + capacitat + ":" + horari + ":" + tipus);
                        System.out.println("Grup creat: " + g.toString());
                        break;
                    case 2:
                        if(g == null) throw new NullPointerException();
                        System.out.println("Id esperat: " + id);
                        System.out.println("Id obtingut: " + g.getId());
                        break;
                    case 3:
                        if(g == null) throw new NullPointerException();
                        System.out.println("Capacitat esperada: " + capacitat);
                        System.out.println("Capacitat obtinguda: " + g.getCapacitat());
                        break;
                    case 4:
                        if(g == null) throw new NullPointerException();
                        System.out.println("Horari esperat: " + horari);
                        System.out.println("Horari obtingut: " + g.getHorariAssig());
                        break;
                    case 5:
                        if(g == null) throw new NullPointerException();
                        System.out.println("Tipus esperat: " + tipus);
                        System.out.println("Tipus obtingut: " + g.getTipus());
                        break;
                }
            }catch(Aula_Exception ae){
                System.out.println("Tipus no valid");
                Tipus_Aula.escriure_codis_valids();
            }catch(NullPointerException npe){
                System.out.println("Abans de provar altres funcions, crea un grup provant la creadora");
            }finally{
                System.out.println("Introdueix un codi:");
                codi = keyboard.nextInt();
            }
        }





    }
}
