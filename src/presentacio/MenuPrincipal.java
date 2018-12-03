package presentacio;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MenuPrincipal {
    VBox layout;

    public MenuPrincipal() {
        layout = new VBox();
        buildLayout();
    }

    public Pane getLayout() {
        return this.layout;
    }

    private void buildLayout() {
        //Creacio Menus:
        MenuBar menu = new MenuBar();
        //Menu Fitxer
        Menu menuFile = new Menu();
        menuFile.setText("Fitxer");
        //Menu Carregar
        Menu load = new Menu("Carregar");
        //Submenu carregar Aulari
        MenuItem loadAulari = new MenuItem("Carregar Aulari");
        load.getItems().addAll(loadAulari);
        //Submenu carregar PlaEstudi
        MenuItem loadPla = new MenuItem("Carregar Pla Estudis");
        load.getItems().addAll(loadPla);
        menuFile.getItems().addAll(load);
        //Menu Sortir
        MenuItem exit = new MenuItem("Sortir");
        exit.setOnAction(e -> VistaDialeg.terminate());
        menuFile.getItems().add(new SeparatorMenuItem());
        menuFile.getItems().addAll(exit);
        menu.getMenus().add(menuFile);
        // - Menu Vista
        Menu menuView = new Menu("Vista");
        menu.getMenus().add(menuView);

        //Addicio elements a topLayout
        layout.getChildren().add(menu);
    }
}
