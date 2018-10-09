package domain;


// He fet una enumeracio i un atribut de classe en comptes de subclasses perque de moment tenen exactament el mateix comportament



/**
 * @author Victor
 * Aula.
 * El seu tipus d'Aula mai pot ser LAB
 */
public class Aula {

    private String id;          //Identificador de l'Aula
    private int capacitat;      //Capacitat de l'Aula
    private Tipus_Aula tipus;   //Tipus d'Aula
    private boolean[][] ocupacio;


    /**
     * @param id Identificador de la nova Aula
     * @param capacitat Capacitat de la nova Aula
     * @param tipus Tipus de la nova Aula
     * @throws Exception @tipus == LAB quan no és un Laboratori
     */
    public Aula(String id, int capacitat, Tipus_Aula tipus) throws Exception {
        this.id = id;
        this.capacitat = capacitat;
        this.tipus = tipus;
        if (tipus == Tipus_Aula.LAB && !(this instanceof Laboratori)) throw new Exception();
        this.ocupacio = new boolean[12][5];
    }

    /**
     * @return Retorna l'identificador de l'Aula
     */
    public String getId() {
        return id;
    }

    /**
     * @param id Nou identificador de l'Aula
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Retorna la capacitat de la classe
     */
    public int getCapacitat() {
        return capacitat;
    }

    /**
     * @param capacitat Nova capacitat de l'Aula
     */
    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }

    /**
     * @return Retorna el tipus de l'Aula
     */
    public Tipus_Aula getTipus() {
        return tipus;
    }

    /**
     * @param tipus Nou tipus de l'Aula
     */
    public void setTipus(Tipus_Aula tipus) throws Exception {
        if(tipus == Tipus_Aula.LAB || this.tipus == Tipus_Aula.LAB ) throw new Exception();
        this.tipus = tipus;
    }

    /**
     * @return Retorna l'Ocupació de l'Aula en forma de matriu de Booleans (true == ocupat)
     */
    public boolean[][] getOcupacio() {
        return ocupacio;
    }

    /**
     * @return Retorna l'Ocupació de l'Aula en forma d'horari (String) preparat per imprimir
     */
    public String ocupacioToString(){
        StringBuilder s = new StringBuilder("               Dilluns  Dimarts  Dimecres Dijous   Divendres\n");
        for(int i=0; i<12; ++i){
            s.append(i+8).append(":00 - ").append(i+9).append(":00: ");
            if(i==0) s.append("  ");
            if(i==1) s.append(" ");
            for(int j=0; j<5; ++j){
                s.append((ocupacio[i][j]) ? "ocupat   " : "lliure   ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Reserva l'Aula durant unes hores consecutives
     * @param dia Dia de la setmana de la reserva
     * @param hora Hora del dia de la reserva
     * @param durant Nombre d'hores de la reserva
     * @return @true si s'ha pogut fer la reserva, @false si alguna de les hores ja estava reservada
     */
    public boolean reserva(int dia, int hora, int durant) throws Exception{
        if(dia < 0 || dia > 4 || hora < 8 || hora > 19 || durant > 12 || (durant + hora)>19) throw new Exception();
        for(int i=0; i<durant; ++i){
            if(ocupacio[hora+i-8][dia]) return false;
        }for(int i=0; i<durant; ++i){
            ocupacio[hora+i-8][dia] = true;
        }
        return true;
    }

    /**
     * @return String amb les dades de l'Aula
     */
    @Override
    public String toString() {
        String s = "Id: " + id + "\n";
        s += "Capacitat: " + capacitat + "\n";
        s += "Tipus d'Aula: " + tipus + "\n";
        return s;
    }
}

