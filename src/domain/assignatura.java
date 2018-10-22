package domain;

import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

/**
 * @author David Pujol,
 * Date: 07/10/18
 */


public class assignatura {
    /** Atributs **/

    private String id;  //identificador de la assignatura
    private String nom;     //nom complet de la assignatura
    private int nivell;  //potser caldria que fós una string????

    //numero i duració de classes
    private int classes_teoria = 0;
    private int classes_problemes = 0;
    private int classes_laboratori = 0;
    private int duracio_teoria = 0;
    private int duracio_problemes = 0;
    private int duracio_laboratori = 0;


    //mapa en el que guardem els diferents grups de la assignatura. Tots els de teoria i problemes es guarden junts.
    private TreeMap<String, grup> grups;


    /** Constructores **/
    public assignatura (String id, String nom, int nivell) {
        this.id = id;
        this.nom = nom;
        this.nivell = nivell;
    }


    /** Mètodes públics **/

    /**
     * Modifica el identificador de la assignatura pel del paràmetre rebut.
     * @return Retorna el id de la assignatura.
     */
    public String getId (){
        return id;
    }

    /**
     * Modifica el identificador de la assignatura pel del paràmetre rebut.
     * @return Retorna el nom de la assignatura.
     */
    public String getNom () {
        return nom;
    }

    /**
     * Modifica el identificador de la assignatura pel del paràmetre rebut.
     * @return Retorna el nivell de la assignatura.
     */
    public int getNivell () {
        return nivell;
    }


    /**
     * Es guarda tots els grups que pertanyen a aquesta assignatura.
     * @param grups
     */
    public void creaGrups (TreeMap<String, grup> grups) {
        this.grups = grups;
    }



    /**
     * El sistema modificarà el nombre i la durada de les classes de teoria.
     * @param nTeoria
     * @param dTeoria
     * @param nProblemes
     * @param dProblemes
     * @param nLaboratori
     * @param dLaboratori
     */
    public void setClasses (int nTeoria, int dTeoria, int nProblemes, int dProblemes, int nLaboratori, int dLaboratori) {
        classes_teoria = nTeoria;
        duracio_teoria = dTeoria;
        classes_problemes = nProblemes;
        duracio_problemes = dProblemes;
        classes_laboratori = nLaboratori;
        duracio_laboratori = dLaboratori;
    }



    public void showClasses () {
        System.out.println(classes_teoria + ":" + duracio_teoria + ":" + classes_problemes + ":" + duracio_problemes
                + ":" + classes_laboratori + ":" + duracio_laboratori + "\n");
    }



    public void showGrups () {

        for (Map.Entry<String, grup> a : grups.entrySet()) {
            grup b = a.getValue();
            Tipus_Aula t = b.getTipus();

            if (t.equals(Tipus_Aula.LAB)) System.out.println(b.getId() + ":" + b.getCapacitat() + ":" + t + ":" + b.getTipusLab());
            else System.out.println(b.getId() + ":" + b.getCapacitat() + ":" + t);
        }

    }



    /**
     * Fa un recorregut en tots els seus grups, i afegeix restriccions perquè
     * en cap cas, els grups de teoria es solapin amb els seus subgrups de
     * problemes o laboratori.
     *
     * Algorisme: Recorre els grups. Si trobes un grup de teoria guardete'l.
     * Sinó, crea la restricció entre el últim grup de teoria trobat i aquest.
     * Funciona perquè el map els manté ordenats (10,11,12,20,22,23)
     *
     */
    public void noSolapis_Teoria_i_Problemes () {

        grup grupTeoria; //anirà contenint el id del grup de teoria tractat

        for (Map.Entry<String, grup> g : grups.entrySet()) {
            String key = g.getKey();
            char last = key.charAt (key.length() -1);
            //ES DE TEORIA
            if (last == '0') grupTeoria = g.getValue();

            else {
                //funcio per afegir la restricció perquè no es solapin el grup de teoria i el subgrup
                //afegirRestriccio (grupTeoria, GrupConcret.getValue());
            }

        }

    }



    public ArrayList<GrupConcret> getAllGrupConcret () {
        ArrayList <GrupConcret> result = new ArrayList<>();

        for (Map.Entry<String, grup> g_aux : grups.entrySet()) {
            grup g = g_aux.getValue();
            GrupConcret a;
            if (g.getTipus() == Tipus_Aula.LAB)
                a = new GrupConcret(g.getId(), g.getCapacitat(), g.getTipus(),g.getTipusLab(), id, nivell, classes_laboratori, duracio_laboratori);

            else if (g.getTipus() == Tipus_Aula.TEORIA)
                a = new GrupConcret(g.getId(), g.getCapacitat(), g.getTipus(),id, nivell, classes_teoria, duracio_teoria);

            else a = new GrupConcret(g.getId(), g.getCapacitat(), g.getTipus(),id, nivell, classes_problemes, duracio_problemes);


            result.add(a);
            a.showAll();
        }

        return result;

    }



}
