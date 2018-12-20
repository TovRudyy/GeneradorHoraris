package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


/**
 * Contee informació sobre l'assignatura aixi com restriccions que l'afecten directament.
 * @author David Pujol
 * Date: 07/10/18
 */


public class assignatura implements Serializable {
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
    /**
     * Crea una nova instància de assignatura.
     * @param id Identificador de la assignatura
     * @param nom Nom de la assignatura.
     * @param nivell Nivell de la assignatura.
     */
    public assignatura (String id, String nom, int nivell) {
        this.id = id;
        this.nom = nom;
        this.nivell = nivell;
        grups = new TreeMap<>();
    }


    /** Metodes publics **/


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
     * El sistema modificara el nombre i la durada de les classes de teoria, problemes i laboratori.
     * @param nTeoria Numero de classes de teoria a la setmana.
     * @param dTeoria Duració de les classes de teoria.
     * @param nProblemes  Numero de classes de problemes a la setmana.
     * @param dProblemes Duracio de les classes de problemes.
     * @param nLaboratori Numero de classes de laboratori a la setmana.
     * @param dLaboratori Duracio de les classes de laboratori.
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
     * Imprimeix per pantalla la informacio dels diferents grups que formen la assignatura.
     */
    public void showGrups () {

        for (Map.Entry<String, grup> a : grups.entrySet()) {
            grup b = a.getValue();
            Tipus_Aula t = b.getTipus();

            System.out.println(b.getId() + ":" + b.getCapacitat() + ":" + t);
        }

    }

    /**
     * @return El map amb tots els grups que conte la assignatura.
     */
    public Map<String, grup> getGrups() {
        return grups;
    }


    /**
     * Fa un recorregut en tots els seus grups, i afegeix restriccions perque
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
     * una assignacio sera la relacio entre una assignatura i un dels seus grups.
     * @param aules conjunt d'aules amb les que fer les assignacions
     */
    public ArrayList<assignacio> getAssignacions (Map<String, Aula> aules) {
        ArrayList <assignacio> result = new ArrayList<assignacio>();

        for (Map.Entry<String, grup> g_aux : grups.entrySet()) {
            grup g = g_aux.getValue();
            assignacio a;
            if (Tipus_Aula.es_Laboratori(g.getTipus()))
                a = new assignacio(g.getId(), g.getCapacitat(), g.getTipus(), id, nivell, classes_laboratori, duracio_laboratori,g.getHorariAssig(), aules);

            else if (g.getTipus() == Tipus_Aula.TEORIA)
                a = new assignacio(g.getId(), g.getCapacitat(), g.getTipus(),id, nivell, classes_teoria, duracio_teoria, g.getHorariAssig(), aules);

            else a = new assignacio(g.getId(), g.getCapacitat(), g.getTipus(),id, nivell, classes_problemes, duracio_problemes, g.getHorariAssig(), aules);


            //aqui copiem les restriccions que té un grup a la seva assignacio
            a.afegirCorrequisit (corequisits);
            a.afegirSubgrup (g_aux.getValue().getSubgrup());  //afegim les restriccions del grup
            result.add(a);
        }


        return result;
    }


    /**
     * Afegeix un nou correquisit en aquesta assignatura amb relacio amb la assignatura que te com a id
     * el parametre passat.
     * @param new_correquisit Una string amb el id de la assignatura amb la que es correquisit
     */
    public void addCorrequisit (String new_correquisit) {
        corequisits.addAssignatura(new_correquisit);
    }


    /**
     * S'afegeixen a la assignatura una serie de nous correquisits.
     * @param new_correquisits Conjunt dels nous correquisits que es volen afegir.
     */
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


    /**
     * @return Una string amb tota la informacio que conte la classe Assignatura.
     */
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

    /**
     * @return El numero de classes de teoria.
     */
    public int getQtClassesTeoria() {
        return this.classes_teoria;
    }


    /**
     * @return La durada de les classes de teoria.
     */
    public int getDuradaClassesTeoria() {
        return this.duracio_teoria;
    }

    /**
     * @return El numero de classes de problemes.
     */
    public int getQtClassesProblemes() {
        return this.classes_problemes;
    }

    /**
     * @return La durada de les classes de problemes.
     */
    public int getDuradaClassesProblemes() {
        return this.duracio_problemes;
    }

    /**
     * @return El numero de classes de laboratori.
     */
    public int getQtClassesLaboratori() {
        return this.classes_laboratori;
    }

    /**
     * @return La durada de les classes de laboratori..
     */
    public int getDuradaClassesLaboratori() {
        return this.duracio_laboratori;
    }


    public ArrayList<String> getGrupsTeoria() {
        ArrayList<String> ret = new ArrayList<String>();
        for (grup g : grups.values()) {
            if (!g.esSubgrup()) {
                ret.add(g.getId());
            }
        }
        return ret;
    }

    /**
     * @param grup identificador del grup.
     * @return La capacitat de un grup donat.
     */
    public int getCapacitatGrup(String grup) {
        return grups.get(grup).getCapacitat();
    }

    /**
     * @param grup identificador del grup.
     * @return El horari del grup passat per parametre.
     */
    public String getHorariGrup(String grup) {
        return grups.get(grup).getHorariAssig();
    }

    /**
     * @param grup identificador del grup.
     * @return El tipus del grup..
     */
    public String getTipusAulaGrup(String grup) {
        return grups.get(grup).getTipusAula();
    }

    /**
     * Retorna els subgrups de un grup.
     * @param grup Identificador del grup.
     * @return Conjunt de string referents a cadascun dels subgrups de un grup.
     */
    public ArrayList<String> getSubgrupsGrup(String grup) {
        ArrayList<String> ret = new ArrayList<String>();
        for (grup gr : grups.values()) {
            String id = gr.getId();
            if (id.charAt(0) == grup.charAt(0)) {
                if (gr.esSubgrup()) {
                    ret.add(id);
                }
            }
        }
        return ret;
    }

    /**
     * @return Tots els correquisits que te aquesta assignatura.
     */
    public ArrayList<String> getCorrequisits() {
        ArrayList<String> corr = corequisits.getAssignatures();
        corr.remove(this.id);
        return corr;
    }

    /**
     * Comprova si una assignatura conte un grup concret.
     * @param grup Identificador del grup
     * @return True si el conte o false altrament.
     */
    public boolean hasGrup(String grup) {
        return grups.containsKey(grup);
    }


    /**
     * Esborra un grup de la assignatura, si es pot.
     * @param grup Identificador del grup.
     */
    public void esborraGrup(String grup) {
        ArrayList<String> borrar = new ArrayList<>();
        borrar.add(grup);
        int a = Integer.parseInt(grup);
        if (a % 10 == 0)   //si es de teoria cal esborrar tambe els seus subgrups
        {
            for (Map.Entry<String, grup> g : grups.entrySet())
            {
                if ((Integer.parseInt(g.getKey())) / 10 == (a/ 10)) borrar.add (g.getKey());
            }
        }


        for (String aux : borrar)
            grups.remove(aux);

    }

    /**
     * Modifica el nom de la assignatura pel valor passat per parametre.
     * @param newValue Nom nou.
     */
    public void setNom(String newValue) {
        this.nom = newValue;
    }

    /**
     * Modifica el nivell de la assignatura pel valor passat per parametre.
     * @param nivell Nou nivell.
     */
    public void setNivell(int nivell) {
        this.nivell = nivell;
    }


    /**
     * Modifica el numero de classes de teoria.
     * @param qt Numero de classes de teoria.
     */
    public void setQtClassesTeoria(int qt) {
        this.classes_teoria = qt;
    }

    /**
     * Modifica la durada de les classes de teoria.
     * @param qt La nova durada de les classes de teoria.
     */
    public void setDuradaClassesTeoria(int qt) {
        this.duracio_teoria = qt;
    }

    /**
     * Modifica el numero de classes de problemes.
     * @param qt Numero de classes de problemes.
     */
    public void setQtClassesProblemes(int qt) {
        this.classes_problemes = qt;
    }

    /**
     * Modifica la durada de les classes de problemes.
     * @param qt La nova durada de les classes de problemes.
     */
    public void setDuradaClassesProblemes(int qt) {
        this.duracio_problemes = qt;
    }

    /**
     * Modifica el numero de classes de laboratori.
     * @param qt Numero de classes de laboratori.
     */
    public void setQtClassesLaboratori(int qt) {
        this.classes_laboratori = qt;
    }

    /**
     * Modifica la durada de les classes de laboratori.
     * @param qt La nova durada de les classes de laboratori.
     */
    public void setDuradaClassesLaboratori(int qt) {
        this.duracio_laboratori = qt;
    }

    /**
     * Modifica la capacitat de un grup per un nou valor.
     * @param grup Identificador del grup
     * @param qt Nova capacitat
     */
    public void setCapacitatGrup(String grup, int qt) {
        grups.get(grup).setCapacitat(qt);
    }

    /**
     * Modifica el horari d'un grup per un nou valor.
     * @param grup Identificador del grup
     * @param valor Nou valor de l'horari.
     */
    public void setHorariGrup(String grup, String valor) {
        grups.get(grup).setHorari(valor);
    }

    /**
     * Modifica el tipus d'un grup per un nou valor.
     * @param grup Identificador del grup
     * @param tipusAula Nou valor del tipus del grup.
     */
    public void setTipusGrup(String grup, Tipus_Aula tipusAula) {
        grups.get(grup).setTipus(tipusAula);
    }


    /**
     * Afegeix un grup a la assignatura.
     * @param g El nou grup.
     * @return True si l'hem pogut afegir o no.
     */
    public boolean afegirGrup (grup g)
    {
        String id = g.getId();
        if (hasGrup(id)) return false;
        else {
            grups.put(id, g);
            return true;
        }
    }

    /**
     * Eliminem un correquisit de la assignatura.
     * @param id_altre Identificador del correquisit.
     * @return True si l'hem pogut eliminar o false altrament.
     */
    public boolean eliminarCorrequisit (String id_altre)
    {
        return corequisits.eliminarAssignatura (id_altre);
    }



    // Aqui farem que tots els grups recalculin la seva capacitat
    public void recalcularCapacitatsGrups ()
    {
        //fiquem tots els grup de teoria a 0 per després recalcularlos
        for (Map.Entry<String, grup> g: grups.entrySet())
        {
            int a= Integer.parseInt(g.getKey());
            a = a%10;

            if (a== 0)
                g.getValue().setCapacitat(0);

        }

        //recalculem
        for (Map.Entry<String, grup> g: grups.entrySet())
        {
            int a= Integer.parseInt(g.getKey());
            int c = a%10;
            int b = (a/10)*10;

            if (c != 0) {
                int capacitat = getCapacitatGrup(Integer.toString(b));  //agafem la capacitat del grup de teoria
                setCapacitatGrup(Integer.toString(b), capacitat + g.getValue().getCapacitat());
            }
        }

    }

}
