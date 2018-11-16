package testsClasses.JUnit;

import domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class assignacioTest {

    private assignacio ass;

    @Before
    public void setUp() {
        Map<String, Aula> aules = new TreeMap<>();
        aules.put("Aula1", new Aula("Aula1", 120, Tipus_Aula.TEORIA));
        aules.put("Aula2", new Aula("Aula2", 120, Tipus_Aula.TEORIA));
        aules.put("Aula3", new Aula("Aula3", 120, Tipus_Aula.PROBLEMES));
        ass = new assignacio("10", 60, Tipus_Aula.TEORIA, "TC", 5, 1, 2, "M", aules);
        RestriccioCorequisit corequisit = new RestriccioCorequisit();
        corequisit.addAssignatura("PROP");
        corequisit.addAssignatura("A");
        ass.afegirCorrequisit(corequisit);
    }

    @Test
    public void getIdAssig() {
        assertEquals(ass.getIdAssig(), "TC");
    }

    @Test
    public void getIdGrup() {
        assertEquals(ass.getIdGrup(), "10" );
    }

    @Test
    public void esMatins() {
        assertTrue(ass.esMatins());
    }

    @Test
    public void getAllPossibleClasses() {
        for(Classe c:ass.getAllPossibleClasses()){
            assertTrue(c.getIdAula().equals("Aula1") || c.getIdAula().equals("Aula2"));
            assertEquals(c.getId_assig(), "TC");
            assertEquals(c.getId_grup(), "10");
            assertEquals(c.getDurada(), 2);
            assertTrue(c.getHoraInici() >= 8 && c.getHoraInici() <= 12 );
            assertTrue(c.getHoraFi() >= 10 && c.getHoraFi() <= 14 );
        }
    }

    @Test
    public void getNumeroClassesRestants() {
        assertTrue(ass.getNumeroClassesRestants() <= 1);
    }

    @Test
    public void forwardChecking() {
        for(Classe c: ass.forwardChecking(new Classe("LI", "10", DiaSetmana.DIMARTS, 10, 12, "Aula2"))){
            assertEquals(c.getDia(), DiaSetmana.DIMARTS);
            assertTrue((10 <= c.getHoraInici() && 12 > c.getHoraInici()) || (10 < c.getHoraFi() && 12 >= c.getHoraFi()));
        }
    }

    @Test
    public void nomesSeleccionades() {
        ass.nomesSeleccionades();
        assertTrue(ass.getAllPossibleClasses().isEmpty());
    }

    @Test
    public void afegirSeleccionada() {
        int in = ass.getNumeroClassesRestants();
        ass.afegirSeleccionada(new Classe("TC", "10", DiaSetmana.string_To_DiaSetmana("dilluns"), 10, 12, "Aula1"));
        assertEquals(ass.getNumeroClassesRestants(), in-1);
    }

    @Test
    public void eliminarSeleccionada() {
        int in = ass.getNumeroClassesRestants();
        ass.eliminarSeleccionada(new Classe("TC", "10", DiaSetmana.string_To_DiaSetmana("dilluns"), 10, 12, "Aula1"));
        assertTrue(ass.getNumeroClassesRestants() == in+1 || ass.getNumeroClassesRestants() == in);
    }

    @Test
    public void getSeleccionades() {
        Classe c = new Classe("TC", "10", DiaSetmana.string_To_DiaSetmana("dilluns"), 10, 12, "Aula1");
        ass.afegirSeleccionada(c);
        ArrayList<Classe> arrayList = new ArrayList<>();
        arrayList.add(c);
        assertEquals(ass.getSeleccionades(), arrayList);
    }

    @Test
    public void isEmpty() {
        assertFalse(ass.isEmpty());
        ass.nomesSeleccionades();
        assertTrue(ass.isEmpty());
    }

    @Test
    public void toStringTest() {
        assertEquals(ass.toString(), "TC:10:60:TEORIA:M");
    }
}