package presentacio;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * La funció de la classe AulaFX és simplement sincronitzar les dades
 * de les aules de la capa de domini amb les dades mostrades per la GUI
 * @author Oleksandr Rudyy
 */
public class AulaFX {
    private final SimpleStringProperty id;
    private final SimpleIntegerProperty cap;
    private final SimpleStringProperty tipus;

    /**
     *
     * @param id identificador de l'aula
     * @param cap capacitat de l'aula
     * @param tipus tipus de l'aula
     */
    public AulaFX (String id, int cap, String tipus) {
        this.id = new SimpleStringProperty(id);
        this.cap = new SimpleIntegerProperty(cap);
        this.tipus = new SimpleStringProperty(tipus);
    }

    /**
     * @param id identificador de l'aula
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     *
     * @param cap capacitat de l'aula
     */
    public void setCap(int cap) {
        this.cap.set(cap);
    }

    /**
     *
     * @param tipus tipus de l'aula
     */
    public void setTipus(String tipus) {
        this.tipus.set(tipus);
    }

    /**
     *
     * @return l'identificador de l'aula
     */
    public String getId() {
        return id.get();
    }

    /**
     * @return el tipus de l'aula
     */
    public String getTipus() {
        return tipus.get();
    }


    /**
     * @return la capacitat de l'aula
     */
    public int getCap() {
        return cap.get();
    }


}
