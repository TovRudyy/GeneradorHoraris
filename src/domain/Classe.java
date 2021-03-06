package domain;

import java.io.Serializable;

/**
 * Aquesta Classe serveix per relacionar Aula amb una data concreta i el grup d'una assignatura, per tant es la unitat de dades que processa l'algorisme a l'hora d'assignar un hoari a una assignacio.
 * @author Olek
 */

public class Classe implements Serializable {

    private String id_assig;
    private String id_grup;
    private DiaSetmana dia;
    private int hora_inici;
    private int hora_fi;
    private String id_aula;

    /**
     * Creadora de la Classe.
     * @param id_assig Identificador de la assignatura.
     * @param id_grup Identificador del grup.
     * @param dia Dia de la setmana
     * @param inici Hora d'inici de la Classe.
     * @param fi Hora del final de la Classe.
     * @param id_aula Identificador de l'aula
     */
    public Classe(String id_assig, String id_grup, DiaSetmana dia, int inici, int fi, String id_aula) {
        this.id_assig = id_assig;
        this.id_grup = id_grup;
        this.dia = dia;
        if (inici < fi) {
            this.hora_inici = inici;
            this.hora_fi = fi;
        }
        else {
            System.out.println(id_assig +" "+ id_grup);
            System.out.println("Error: hora_inici és major que hora_fi!");
        }
        this.id_aula = id_aula;
    }

    /**
     * @return Dia de la setmana de la Classe.
     */
    public DiaSetmana getDia() {
        return this.dia;
    }

    /**
     * @return Dia de la setmana de la Classe.
     */
    public int getHoraInici() {
        return this.hora_inici;
    }

    /**
     * @return Hora del final de la Classe..
     */
    public int getHoraFi() {
        return this.hora_fi;
    }

    /**
     * @return Identificador de la assignatura.
     */
    public String getId_assig () {
        return this.id_assig;
    }

    /**
     * @return Identificador del grup.
     */
    public String getId_grup () {
        return this.id_grup;
    }

    /**
     * @return Identificador de l'aula.
     */
    public String getIdAula() {
        return id_aula;
    }

    /**
     * @return Durada de la Classe.
     */
    public int getDurada() {
        if (hora_fi != -1) {
            return hora_fi - hora_inici;
        }
        else {
            System.out.println("Error: hora_fi no està inicialitzat!");
            return 0;
        }
    }


    /**
     * Imprimeix per pantalla tota la informacio de la Classe.
     */
    public void showClasse () {
        System.out.println(id_assig + " : " + id_grup + " : "+ id_aula + " : " + dia+ " : " + hora_inici + " : " + hora_fi);
    }


    /**
     * Modifica el valor del seu dia per un nou valor.
     * @param d
     */
    public void setDia (DiaSetmana d) {
        dia = d;
    }

    /**
     * Modifica el valor de la seva hora d'inici per un nou valor.
     * @param h
     */
    public void setHora_inici (int h) {
        hora_inici = h;
    }

    /**
     * Modifica el valor de la seva hora fi per un nou valor.
     * @param h
     */
    public void setHora_fi (int h) {
        hora_fi = h;
    }

    /**
     * Modifica el identificador de la seva aula.
     * @param a
     */
    public void setAula (String a) {
        id_aula = a;
    }



    @Override
    /**
     * @return Retorna una string amb tota la informacio de la Classe
     */
    public String toString() {
        return id_assig + " : " + id_grup + " : "+ id_aula + " : " + dia+ " : " + hora_inici + " : " + hora_fi;
    }
}
