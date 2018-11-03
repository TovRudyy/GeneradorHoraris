package JUnit;

import domain.Aula;
import domain.Aula_Exception;
import domain.Tipus_Aula;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class AulaTest {

    private static ArrayList<Aula> aules;
    private static ArrayList<String> ids;
    private static ArrayList<Integer> capacitats;
    private static ArrayList<Tipus_Aula> tipus;


    @BeforeClass
    public static void beforeClass() throws FileNotFoundException, Aula_Exception {
        ids = new ArrayList<>();
        capacitats = new ArrayList<>();
        tipus = new ArrayList<>();
        Scanner scanner = new Scanner(new FileReader("C:\\Users\\Victor\\IdeaProjects\\GeneradorHoraris\\src\\JUnit\\AulaTest.txt"));
        scanner.nextLine();
        while(scanner.hasNext()) {
            ids.add(scanner.next());
            capacitats.add(scanner.nextInt());
            tipus.add(Tipus_Aula.string_to_Tipus_Aula(scanner.next()));
        }
    }

    @Before
    public void beforeTest() {
        aules = new ArrayList<>();
        for(int i=0; i<ids.size(); ++i) aules.add(new Aula(ids.get(i), capacitats.get(i), tipus.get(i)));

    }

    @Test
    public void getId() {
        for(int i=0; i<aules.size(); ++i){
            assertEquals(ids.get(i), aules.get(i).getId());
        }
    }

    @Test
    public void setId() {
        for (Aula aula : aules) {
            aula.setId("Hey");
            assertEquals("Hey", aula.getId());
        }
    }

    @Test
    public void getCapacitat() {
        for(int i=0; i<aules.size(); ++i){
            assertEquals(capacitats.get(i).intValue(), aules.get(i).getCapacitat());
        }
    }

    @Test
    public void setCapacitat() {
        for (Aula aula : aules) {
            aula.setCapacitat(564);
            assertEquals(564, aula.getCapacitat());
        }

    }

    @Test
    public void getTipus() {
        for(int i=0; i<aules.size(); ++i){
            assertEquals(tipus.get(i), aules.get(i).getTipus());
        }
    }

    @Test
    public void setTipus() {
        for (Aula aula : aules) {
            aula.setTipus(Tipus_Aula.PROBLEMES);
            assertEquals(Tipus_Aula.PROBLEMES, aula.getTipus());
        }
    }

    @Test
    public void testToString() {
        for(int i=0; i<aules.size(); ++i){
            assertEquals(ids.get(i) +":" + capacitats.get(i) + ":" + tipus.get(i), aules.get(i).toString());
        }
    }
}