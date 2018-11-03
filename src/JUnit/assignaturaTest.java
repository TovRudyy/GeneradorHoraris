package JUnit;

import JUnit.Stubs.StubGrup;
import domain.assignatura;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class assignaturaTest {

    private static ArrayList<domain.assignatura> ass;
    private static ArrayList<String> ids;
    private static ArrayList<String> noms;
    private static ArrayList<Integer> nivells;
    private static ArrayList<ArrayList<Integer>> classes;

    @BeforeClass
    public static void beforeClass() throws FileNotFoundException{
        ids = new ArrayList<>();
        noms = new ArrayList<>();
        nivells = new ArrayList<>();
        classes = new ArrayList<>(6);
        Scanner scanner = new Scanner(new FileReader("C:\\Users\\Victor\\IdeaProjects\\GeneradorHoraris\\src\\JUnit\\assignaturaTest.txt"));
        while(scanner.hasNext()){
            ids.add(scanner.next());
            noms.add(scanner.next());
            nivells.add(scanner.nextInt());
            for(ArrayList<Integer> c : classes) c.add(scanner.nextInt());
        }

    }

    @Before
    public void setUp() throws Exception {
        for(int i=0; i<ids.size(); ++i){
            ass.add(new assignatura(ids.get(i), noms.get(i), nivells.get(i)));
            ass.get(i).setClasses(classes.get(0).get(i),classes.get(1).get(i),classes.get(2).get(i),classes.get(3).get(i),classes.get(4).get(i),classes.get(5).get(i));
        }
    }

    @Test
    public void getId() {
        for(int i=0; i<ass.size(); ++i){
            assertEquals(ids.get(i), ass.get(i).getId());
        }
    }

    @Test
    public void getNom() {
        for(int i=0; i<ass.size(); ++i){
            assertEquals(noms.get(i), ass.get(i).getNom());
        }
    }

    @Test
    public void getNivell() {
        for(int i=0; i<ass.size(); ++i){
            assertEquals(nivells.get(i).intValue(), ass.get(i).getNivell());
        }
    }

    @Test
    public void creaGrups() {
        Map<String, StubGrup> grups = new TreeMap<>();
        grups.put("10", new StubGrup());
        grups.put("11", new StubGrup());
        grups.put("12", new StubGrup());
        grups.put("13", new StubGrup());
        for(assignatura a:ass){
            assertEquals(grups, a.getGrups());
        }
    }

    @Test
    public void noSolapis_Teoria_i_Problemes() {

        for(assignatura a:ass){
            a.noSolapis_Teoria_i_Problemes();
        }
    }

    @Test
    public void getAssignacions() {
    }

    @Test
    public void addCorrequisit() {
    }
}