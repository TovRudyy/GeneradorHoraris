package testsClasses.JUnit;

import domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * @author Victor
 */

public class assignacioTest {

    private assignacio ass;

    /**
     * Crea un escenari amb 3 aules, correquisits, assignatures...
     */
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

    /**
     * Comprova el id de la assignatura.
     */
    @Test
    public void getIdAssig() {
        assertEquals("TC", ass.getIdAssig());
    }

    /**
     * Comprova el identificador del grup.
     */
    @Test
    public void getIdGrup() {
        assertEquals("10", ass.getIdGrup() );
    }

    /**
     * Comprova si el grup es o no de matins.
     */
    @Test
    public void esMatins() {
        assertTrue(ass.esMatins());
    }

    /**
     * Retorna les possibilitats creades per una assignacio.
     */
    @Test
    public void getAllPossibleClasses() {
        for(Classe c:ass.getAllPossibleClasses()){
            assertTrue(c.getIdAula().equals("Aula1") || c.getIdAula().equals("Aula2"));
            assertEquals("TC", c.getId_assig());
            assertEquals("10", c.getId_grup());
            assertEquals(2, c.getDurada());
            assertTrue(c.getHoraInici() >= 8 && c.getHoraInici() <= 12 );
            assertTrue(c.getHoraFi() >= 10 && c.getHoraFi() <= 14 );
        }
    }

    /**
     * Comprova el numero de classes que li queden per assignar.
     */
    @Test
    public void getNumeroClassesRestants() {
        assertTrue(ass.getNumeroClassesRestants() <= 1);
    }

    /**
     * Comprova el resultat de fer forward checking.
     */
    @Test
    public void forwardChecking() {
        for(Classe c: ass.forwardChecking(new Classe("LI", "10", DiaSetmana.DIMARTS, 10, 12, "Aula2"))){
            assertEquals(DiaSetmana.DIMARTS, c.getDia());
            assertTrue((10 <= c.getHoraInici() && 12 > c.getHoraInici()) || (10 < c.getHoraFi() && 12 >= c.getHoraFi()));
        }
    }

    /**
     * Comprova el resultat de eliminar totes les opcions menys les seleccionades.
     */
    @Test
    public void nomesSeleccionades() {
        ass.nomesSeleccionades();
        assertTrue(ass.getAllPossibleClasses().isEmpty());
    }

    /**
     * Comprova la operacio d'afegir una Classe seleccionada.
     */
    @Test
    public void afegirSeleccionada() {
        int in = ass.getNumeroClassesRestants();
        ass.afegirSeleccionada(new Classe("TC", "10", DiaSetmana.string_To_DiaSetmana("dilluns"), 10, 12, "Aula1"));
        assertEquals(in-1,ass.getNumeroClassesRestants());
    }

    /**
     * Comprova el resultat d'eliminar una Classe seleccionada.
     */
    @Test
    public void eliminarSeleccionada() {
        int in = ass.getNumeroClassesRestants();
        ass.eliminarSeleccionada(new Classe("TC", "10", DiaSetmana.string_To_DiaSetmana("dilluns"), 10, 12, "Aula1"));
        assertTrue(ass.getNumeroClassesRestants() == in+1 || ass.getNumeroClassesRestants() == in);
    }

    /**
     * Comprova les Classes que tenim seleccionades.
     */
    @Test
    public void getSeleccionades() {
        Classe c = new Classe("TC", "10", DiaSetmana.string_To_DiaSetmana("dilluns"), 10, 12, "Aula1");
        ass.afegirSeleccionada(c);
        ArrayList<Classe> arrayList = new ArrayList<>();
        arrayList.add(c);
        assertEquals(arrayList, ass.getSeleccionades());
    }

    /**
     * Comprova si el conjunt de Classes es empty o no, i si aixo es o no correcte.
     */
    @Test
    public void isEmpty() {
        assertFalse(ass.isEmpty());
        ass.nomesSeleccionades();
        assertTrue(ass.isEmpty());
    }

    /**
     * Comprova tota la informacio de la assignacio i si aixo es o no correcte.
     */
    @Test
    public void toStringTest() {
        assertEquals("TC:10:60:TEORIA:M", ass.toString());
    }
}