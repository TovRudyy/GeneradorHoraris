package presentacio;

import javafx.scene.control.TreeView;
import javafx.scene.layout.*;

/**
 * Aquesta classe engloba totes les classes que formen el Layout principal de l'aplicacio
 */
public class LayoutPrincipal {
    private GridPane mainLayout;

    public LayoutPrincipal() {
        mainLayout = new GridPane();
    }

    /**
     *
     * @return l'objecte layout princinap
     */
    public Pane getLayout() {
        MenuPrincipal mp = new MenuPrincipal();
        mainLayout.add(mp.getLayout(),0,0,3,1);
        SeccioAules sa = new SeccioAules();
        mainLayout.add(sa.getLayout(),0,1);
        SeccioPlans sp = new SeccioPlans();
        mainLayout.add(sp.getLayout(), 1,1);
        SeccioBotonsPE sBotPE = new SeccioBotonsPE((TreeView<String>) sp.getArbrePE());
        mainLayout.add(sBotPE.getLayout(), 2, 1);
        return mainLayout;
    }


}
