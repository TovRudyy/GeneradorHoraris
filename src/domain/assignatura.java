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
    private RestriccioCorequisit corequisits = new RestriccioCorequisit();  //sempre tindrà aquesta restricció tot i que pot ser que estigui buida.



    /** Constructores **/
    public assignatura (String id, String nom, int nivell) {
        this.id = id;
        this.nom = nom;
        this.nivell = nivell;
        grups = new TreeMap<>();
    }


    /** Mètodes públics **/


    /**
     * @return Retorna el identificador de l'assignatura.
     */
    public String getId (){
        return id;
    }

    /**
     * @return Retorna el nom de la assignatura.
     */
    public String getNom () {
        return nom;
    }

    /**
     * @return Retorna el nivell de la assignatura.
     */
    public int getNivell () {
        return nivell;
    }

    public RestriccioCorequisit getCorequisits() {
        return corequisits;
    }

    /**
     * Es guarden tots els grups que pertanyen a aquesta assignatura.
     * @param grups Rep un map amb el conjunt de grups de l'assignatura, indexats pel seu identificador.
     */
    public void creaGrups (TreeMap<String, grup> grups) {
        this.grups = grups;
    }



    /**
     * El sistema modificarà el nombre i la durada de les classes de teoria, problemes i laboratori.
     * @param nTeoria Número de classes de teoria a la setmana.
     * @param dTeoria Duració de les classes de teoria.
     * @param nProblemes  Número de classes de problemes a la setmana.
     * @param dProblemes Duració de les classes de problemes.
     * @param nLaboratori Número de classes de laboratori a la setmana.
     * @param dLaboratori Duració de les classes de laboratori.
     */
    public void setClasses (int nTeoria, int dTeoria, int nLaboratori, int dLaboratori, int nProblemes, int dProblemes) {
        classes_teoria = nTeoria;
        duracio_teoria = dTeoria;
        classes_problemes = nProblemes;
        duracio_problemes = dProblemes;
        classes_laboratori = nLaboratori;
        duracio_laboratori = dLaboratori;
    }


    /**
     * Imprimeix per pantalla el nombre de classes i la duracio de teoria, problemes i laboratori.
     */
    public void showClasses () {
        System.out.println(classes_teoria + ":" + duracio_teoria + ":" + classes_problemes + ":" + duracio_problemes
                + ":" + classes_laboratori + ":" + duracio_laboratori + "\n");
    }

    /**
     * Imprimeix per pantalla la informació dels diferents grups que formen la assignatura.
     */
    public void showGrups () {

        for (Map.Entry<String, grup> a : grups.entrySet()) {
            grup b = a.getValue();
            Tipus_Aula t = b.getTipus();

            System.out.println(b.getId() + ":" + b.getCapacitat() + ":" + t);
        }

    }

    public Map<String, grup> getGrups() {
        return grups;
    }


    /**
     * Fa un recorregut en tots els seus grups, i afegeix restriccions perquè
     * en cap cas, els grups de teoria es solapin amb els seus subgrups de
     * problemes o laboratori.
     */
    public void noSolapis_Teoria_i_Problemes () {

        grup grupTeoria = null; //anirà contenint el id del grup de teoria tractat. Sabem que abans de un subgrup sempre hi anira primer el de teoria.

        for (Map.Entry<String, grup> g : grups.entrySet()) {
            String key = g.getKey();
            char last = key.charAt (key.length() -1);

            //ES DE TEORIA
            if (last == '0') grupTeoria = g.getValue();

            else {
                RestriccioSubgrup r = new RestriccioSubgrup(grupTeoria);
                g.getValue().afegirRestriccio(r);
            }

        }

    }


    /**
     * @return Retorna una arrayList amb tot el conjunt de assignacions generades, tenint en compte que
     * una assignacio serà la relació entre una assignatura i un dels seus grups.
     */
    public ArrayList<assignacio> getAssignacions () {
        ArrayList <assignacio> result = new ArrayList<>();

        RestriccioOcupacio r = new RestriccioOcupacio();

        for (Map.Entry<String, grup> g_aux : grups.entrySet()) {
            grup g = g_aux.getValue();
            assignacio a;
            if (Tipus_Aula.es_Laboratori(g.getTipus()))
                a = new assignacio(g.getId(), g.getCapacitat(), g.getTipus(), id, nivell, classes_laboratori, duracio_laboratori,g.getHorariAssig(),ControladorAules.getAules() );

            else if (g.getTipus() == Tipus_Aula.TEORIA)
                a = new assignacio(g.getId(), g.getCapacitat(), g.getTipus(),id, nivell, classes_teoria, duracio_teoria, g.getHorariAssig(),ControladorAules.getAules() );

            else a = new assignacio(g.getId(), g.getCapacitat(), g.getTipus(),id, nivell, classes_problemes, duracio_problemes, g.getHorariAssig(),ControladorAules.getAules() );


            //aqui copiem les restriccions que té un grup a la seva assignacio
            a.afegirCorrequisit (corequisits);
            a.afegirSubgrup (g_aux.getValue().getSubgrup());  //afegim les restriccions del grup
            result.add(a);
        }

        return result;
    }


    /**
     * Afegeix un nou correquisit en aquesta assignatura amb relacio amb la assignatura que té com a id
     * el paràmetre passat.
     * @param new_correquisit Una string amb el id de la assignatura amb la que és correquisit
     */
    public void addCorrequisit (String new_correquisit) {
        corequisits.addAssignatura(new_correquisit);
    }


    public void addManyCorrequisits (ArrayList<String> new_correquisits) {
        for (int i=0; i < new_correquisits.size(); ++i) {
            String nova_assig = new_correquisits.get(i);
            if (!(nova_assig.equals(id)) &&  (! corequisits.esCorrequisit(nova_assig)) ) {   //si no és ella mateixa i encara no la hem afegit
                corequisits.addAssignatura(nova_assig);
            }

        }

    }



    /**
     * @return Retorna una sola string amb el id, el nom i el nivell de la assignatura.
     */
    @Override
    public String toString() {
        return id + ":" + nom + ":" + nivell;
    }

    public String toStringComplet() {
        String ret = "Id: " + id + ", nom: " + nom + "\nnivell:" + nivell + "\nclasses_teoria:" +
                classes_teoria + "\nclasses_problemes:" + classes_problemes +
                "\nclasses laboratori:" + classes_laboratori + "\nCorrequisits:\n";

        if (!corequisits.isEmpty())
            ret = ret + corequisits.toString();

        ret = ret + "Grups:\n";
        for (grup g : grups.values()) {
            ret = ret + g.toString() + "\n";
        }
        return ret;
    }
}
