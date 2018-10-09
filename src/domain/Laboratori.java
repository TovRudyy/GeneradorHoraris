package domain;


/**
 * @author Victor
 * Laboratori.
 * Els seu tipus d'Aula sempre és LAB
 */
public class Laboratori extends Aula{

    private Tipus_Lab tipus_lab;    //Tipus de Laboratori

    /**
     * Converteix un Laboratori a una Aula normal.
     * La manera correcta d'usar-la seria a = convert(a, tipus),
     * ja que d'aquesta manera mai tindrem dues Aules amb mateix Id.
     * @param laboratori Laboratori a convertir a Aula normal
     * @param tipus Nou tipus de l'Aula
     * @return Nova Aula
     * @throws Exception @tipus == LAB
     */
    public static Aula convert(Laboratori laboratori, Tipus_Aula tipus) throws Exception{
        return new Aula(laboratori.getId(), laboratori.getCapacitat(), tipus);
    }

    /**
     * Converteix una Aula normal a un Laboratori.
     * La manera correcta d'usar-la seria a = convert(a, tipus),
     * ja que d'aquesta manera mai tindrem dues Aules amb mateix Id.
     * @param aula Aula a convertir a Laboratori
     * @param tipus Tipus del Laboratori
     * @return  Nou Laboratori
     * @throws Exception @tipus == NULL
     */
    public static Laboratori convert(Aula aula, Tipus_Lab tipus) throws Exception{
        return new Laboratori(aula.getId(), aula.getCapacitat(), tipus);
    }

    /**
     * @param id Identificador del nou Laboratori
     * @param capacitat Capacitat del nou Laboratori
     * @param tipus Tipus del nou Laboratori
     * @throws Exception Mai
     */
    public Laboratori(String id, int capacitat, Tipus_Lab tipus) throws Exception {
        super(id, capacitat, Tipus_Aula.LAB);
        this.tipus_lab = tipus;
    }

    /**
     * @return Retorna el tipus del Laboratori
     */
    public Tipus_Lab getTipus_lab() {
        return tipus_lab;
    }

    /**
     * @param tipus_lab Nou tipus del Laboratori
     */
    public void setTipus_lab(Tipus_Lab tipus_lab){
        this.tipus_lab = tipus_lab;
    }

    /**
     * @return String amb les dades del Laboratori
     */
    @Override
    public String toString() {
        return super.toString() + "Tipus de Laboratori: " + tipus_lab + "\n";
    }
}
