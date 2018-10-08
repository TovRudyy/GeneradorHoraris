package domain;

import java.util.Map;
import java.util.TreeMap;


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
    private int classes_teoria;
    private double duracio_teoria;

    private int classes_problemes;
    private double duracio_problemes;

    private int classes_laboratori;
    private double duracio_laboratori;


    //mapa en el que guardem els diferents grups de la assignatura. Tots els de teoria i problemes es guarden junts.
    private TreeMap<String, grup> grups;


    /** Constructores **/
    public assignatura (String id, String nom, int nivell) {
        this.id = id;
        this.nom = nom;
        this.nivell = nivell;

        classes_teoria = 0;
        duracio_teoria = 0;

        classes_problemes = 0;
        duracio_problemes = 0;

        classes_laboratori = 0;
        duracio_laboratori = 0;
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
     * @param numeroClasses
     * @param duradaClasse
     */
    public void setClassesTeoria (int numeroClasses, double duradaClasse) {
        classes_teoria = numeroClasses;
        duracio_teoria = duradaClasse;
    }


    /**
     * El sistema modificarà el nombre i la durada de les classes de laboratori.
     * @param numeroClasses
     * @param duradaClasse
     */
    public void setClassesLaboratori (int numeroClasses, double duradaClasse) {
        classes_laboratori = numeroClasses;
        duracio_laboratori = duradaClasse;
    }


    /**
     * El sistema modificarà el nombre i la durada de les classes de problemes.
     * @param numeroClasses
     * @param duradaClasse
     */
    public void setClassesProblemes (int numeroClasses, double duradaClasse) {
        classes_problemes = numeroClasses;
        duracio_problemes = duradaClasse;
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

        for (Map.Entry<String, grup> GrupConcret : grups.entrySet()) {
            String key = GrupConcret.getKey();
            char last = key.charAt (key.length() -1);
            //ES DE TEORIA
            if (last == '0') grupTeoria = GrupConcret.getValue();

            else {
                //funcio per afegir la restricció perquè no es solapin el grup de teoria i el subgrup
                //afegirRestriccio (grupTeoria, GrupConcret.getValue());
            }

        }

    }


}
