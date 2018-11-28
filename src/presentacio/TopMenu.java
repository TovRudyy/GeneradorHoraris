package presentacio;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TopMenu {
    VBox layout;
    MenuBar menu;

    public TopMenu () {
        layout = new VBox();
        menu = new MenuBar();

        inicialitza();
    }

    private void inicialitza() {
        initMenus();

    }

    private void initMenus() {
        // - Menu Fitxer
        Menu menuFile = new Menu();
        menuFile.setText("Fitxer");

        // -- Menu Carregar
        Menu load = new Menu("Carregar");
        // --- Submenu carregar fitxer Aulari
        MenuItem loadAulari = new MenuItem("Carregar Aulari");
        load.getItems().addAll(loadAulari);
        // --- Submenu carregar fitxer Pla Estudi
        MenuItem loadPla = new MenuItem("Carregar Pla Estudis");
        load.getItems().addAll(loadPla);

        menuFile.getItems().addAll(load);


        // -- Menu Sortir
        MenuItem exit = new MenuItem("Sortir");
        exit.setOnAction(e -> VistaDialeg.terminate());
        menuFile.getItems().add(new SeparatorMenuItem());
        menuFile.getItems().addAll(exit);

        menu.getMenus().add(menuFile);

        // - Menu Vista
        Menu menuView = new Menu("Vista");
        // -- CheckMenu Aulari
        CheckMenuItem aulari = new CheckMenuItem("Aulari");
        aulari.setOnAction(e -> vistaAulari(aulari));
        menuView.getItems().add(aulari);
        // -- CheckMenu Plans Estudi
        CheckMenuItem plans = new CheckMenuItem("Plans Estudi");
        menuView.getItems().add(plans);

        menu.getMenus().add(menuView);
    }

}
