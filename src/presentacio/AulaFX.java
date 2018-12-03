package presentacio;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AulaFX {
    private final SimpleStringProperty id;
    private final SimpleIntegerProperty cap;
    private final SimpleStringProperty tipus;

    public AulaFX (String id, int cap, String tipus) {
        this.id = new SimpleStringProperty(id);
        this.cap = new SimpleIntegerProperty(cap);
        this.tipus = new SimpleStringProperty(tipus);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setCap(int cap) {
        this.cap.set(cap);
    }

    public void setTipus(String tipus) {
        this.tipus.set(tipus);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getTipus() {
        return tipus.get();
    }

    public SimpleStringProperty tipusProperty() {
        return tipus;
    }

    public int getCap() {
        return cap.get();
    }

    public SimpleIntegerProperty capProperty() {
        return cap;
    }

}
