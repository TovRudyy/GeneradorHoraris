package presentacio;

import javafx.scene.layout.*;

public class LayoutPrincipal {
    private GridPane mainLayout;

    public LayoutPrincipal() {
        mainLayout = new GridPane();
    }

    public Pane getLayout() {
        MenuPrincipal mp = new MenuPrincipal();
        mainLayout.add(mp.getLayout(),0,0,2,1);
        SeccioAules sa = new SeccioAules();
        mainLayout.add(sa.getLayout(),0,1);
        SeccioPlans sp = new SeccioPlans();
        mainLayout.add(sp.getLayout(), 1,1);
        return mainLayout;
    }


}
