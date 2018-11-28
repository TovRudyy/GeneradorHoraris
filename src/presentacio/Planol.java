package presentacio;

import javafx.scene.layout.BorderPane;

public class Planol {
    BorderPane layout;
    private TopMenu toplayout;
    private VistaAularis leftlayout;
    private VistaPlans rightlayout;

    public Planol () {
        layout = new BorderPane();
        toplayout = new TopMenu();
        leftlayout = new VistaAularis();
        rightlayout = new VistaPlans();

        inicialitza();
    }

    private void inicialitza() {

    }

}
